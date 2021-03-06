package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.viewmodel.FavouriteViewModel

class FavoriteFragment() : Fragment(R.layout.fragment_favorite){
    private val favouriteViewModel by viewModel<FavouriteViewModel>{parametersOf(requireArguments().getInt("user_id"))}

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: BaseItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userId = requireArguments().getInt("user_id")

        recyclerView = view.findViewById(R.id.recycle_view_favorite)

        recyclerViewAdapter = BaseItemAdapter() {
            var intent = Intent(
                activity,
                FilmActivity::class.java
            )
            intent.putExtra("film_id", it)
            intent.putExtra("user_id", userId)
            activity?.startActivity(intent)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, 3)

        favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)

        })
        favouriteViewModel.initialRequest()
    }


}