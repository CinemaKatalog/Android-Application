package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.viewmodel.FavouriteViewModel

class FavoriteFragment() : Fragment(){
    private val favouriteViewModel by viewModel<FavouriteViewModel>{parametersOf(0)}

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: BaseItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycle_view_favorite)
        //createFakeElements(35)
        //recyclerViewAdapter = BaseItemAdapter(favoriteFilmsList) {
        recyclerViewAdapter = BaseItemAdapter() {
            var intent = Intent(
                activity,
                FilmActivity::class.java
            )
            activity?.startActivity(intent)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, 3)

        favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)
        })
        favouriteViewModel.initialRequest()
    }

    /*private fun createFakeElements(count: Int) {
        for (i in 0..count) {
            favoriteFilmsList.add(Film("Название$i", "Жанр$i"))
        }
    }*/

}