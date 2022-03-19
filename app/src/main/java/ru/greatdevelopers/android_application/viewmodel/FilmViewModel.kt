package ru.greatdevelopers.android_application.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.greatdevelopers.android_application.data.model.*
import ru.greatdevelopers.android_application.data.repo.CinemaRepository
import ru.greatdevelopers.android_application.data.repo.FilmRepository
import ru.greatdevelopers.android_application.data.repo.ProfileRepository
import ru.greatdevelopers.android_application.data.repo.UserRepository
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem

class FilmViewModel(
    private val userRepo: UserRepository,
    private val filmRepository: FilmRepository,
    private val cinemaRepository: CinemaRepository,
    private val profileRepository: ProfileRepository,
    private val filmId: Long
) : ViewModel() {

    private val loadUser = MutableLiveData<User>()
    val user: LiveData<User>
        get() = loadUser

    private val loadFilmInfo = MutableLiveData<Film>()
    val film: LiveData<Film>
        get() = loadFilmInfo


    private val loadGenreInfo = MutableLiveData<Genre>()
    val genre: LiveData<Genre>
        get() = loadGenreInfo

    private val loadCountryInfo = MutableLiveData<Country>()
    val country: LiveData<Country>
        get() = loadCountryInfo

        private val loadPosterInfo = MutableLiveData<String>()
    val poster: LiveData<String>
        get() = loadPosterInfo

    private val loadCinemaInfo = MutableLiveData<List<CinemaListItem>>()
    val cinema: LiveData<List<CinemaListItem>>
        get() = loadCinemaInfo

    private fun loadUser() {
        viewModelScope.launch {
            val id = userRepo.getCurrentUserIdFromShPref()
            if (id == -1L) {
                loadUser.postValue(null)
            } else {
                loadUser.postValue(userRepo.getUserById(id))
            }
        }
    }

    fun initialRequest() {
        viewModelScope.launch {
            loadUser()
            val tmpFilm = filmId.let { filmRepository.getFilmById(it) }
            loadFilmInfo.postValue(tmpFilm)
            val favourite = try{
                filmRepository.getFavouriteById(filmId, loadUser.value!!.id)
            }catch (ex: HttpException){
                null
            }
            loadFavourite.postValue(favourite)
            loadCinemaInfo.postValue(cinemaRepository.getFilmCinemaByFilm(filmId))
            loadGenreInfo.postValue(tmpFilm?.let { filmRepository.getGenreById(it.genre) })
            loadCountryInfo.postValue(tmpFilm?.let { filmRepository.getCountryById(it.country) })
            loadPosterInfo.postValue(tmpFilm?.let { filmRepository.getPoster(it.poster) })
            //onFoundUser
        }
    }

    fun favouriteRequest() {
        viewModelScope.launch {
            val userId = loadUser.value!!.id
            val favourite = try{
                filmRepository.getFavouriteById(filmId, userId)
            }catch (ex: HttpException){
                null
            }
            loadFavourite.postValue(favourite)
        }
    }

    fun deleteFilm(film: Film){
        viewModelScope.launch {
            filmRepository.delFilm(film)
        }
    }

    fun deleteFavourite(favourite: Favourite, onDelete: () -> Unit){
        viewModelScope.launch {
            filmRepository.delFavourite(favourite)
            onDelete()
        }
    }

    private val loadFavourite = MutableLiveData<Favourite>()
    val favourite: LiveData<Favourite>
        get() = loadFavourite

    fun insertFavourite(favourite: Favourite, onInsert: () -> Unit){
        viewModelScope.launch {
            filmRepository.insertFavourite(favourite)
            onInsert()
        }
    }
}