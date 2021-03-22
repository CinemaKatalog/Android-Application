package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter
import ru.greatdevelopers.android_application.ui.mainscreen.fragments.ProfileFragmentDirections.actionProfileFragmentToUnregisteredFragment
import ru.greatdevelopers.android_application.viewmodel.FavouriteViewModel

class FavoriteFragment() : Fragment(R.layout.fragment_favorite){
    private val user: User by inject<User> ()
    private val favouriteViewModel by viewModel<FavouriteViewModel>()


    private lateinit var recyclerViewAdapter: BaseItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(user.id == -1){
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToUnregisteredFragment()
            view.findNavController().navigate(action)
        }
        //val userId = requireArguments().getInt("user_id")

        //recyclerView = view.findViewById(R.id.recycle_view_favorite)

        recyclerViewAdapter = BaseItemAdapter() {
            val intent = Intent(
                activity,
                FilmActivity::class.java
            )
            intent.putExtra("film_id", it)
            //intent.putExtra("user_id", user.id)
            activity?.startActivity(intent)
        }
        recycle_view_favorite.adapter = recyclerViewAdapter
        recycle_view_favorite.layoutManager = GridLayoutManager(activity, 3)

        favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)

        })
        favouriteViewModel.initialRequest()
    }
}