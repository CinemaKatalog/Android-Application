package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.FilmGroup
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.GroupAdapter
import ru.greatdevelopers.android_application.viewmodel.MainViewModel

class  MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var recyclerViewAdapter: GroupAdapter
    private var filmsGroupList: ArrayList<FilmGroup> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter = GroupAdapter() {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToFilmFragment(it))
        }
        recycle_view_tops.adapter = recyclerViewAdapter
        recycle_view_tops.layoutManager = LinearLayoutManager(activity)

        mainViewModel.films.observe(viewLifecycleOwner) { films ->
            //createFakeElements()
            //recyclerViewAdapter.setItemList(filmsGroupList)
        }
        mainViewModel.filmsGenre.observe(viewLifecycleOwner) {
            filmsGroupList.add(FilmGroup("Комедии", it))
        }
        mainViewModel.filmsYear.observe(viewLifecycleOwner) {
            filmsGroupList.add(FilmGroup("2020 года", it))
        }
        mainViewModel.filmsRating.observe(viewLifecycleOwner) {
            filmsGroupList.add(FilmGroup("Рейтинговые", it))
        }
        mainViewModel.isAdmin.observe(viewLifecycleOwner) {
            if (it) {
                fab_edit.visibility = View.VISIBLE
                fab_edit.setOnClickListener {
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToEditFragment(-1))
                }
            }
        }

        mainViewModel.initialRequest() {
            filmsGroupList.clear()
            mainViewModel.initGroupsRequest() {

                recyclerViewAdapter.setItemList(filmsGroupList)
            }
        }
    }
}