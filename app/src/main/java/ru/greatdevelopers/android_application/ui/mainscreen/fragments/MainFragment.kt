package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.*
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.GroupAdapter
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.FilmGroup
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.editscreen.EditActivity
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.FilmListItem
import ru.greatdevelopers.android_application.viewmodel.MainViewModel

class MainFragment : Fragment(R.layout.fragment_main){
    private val user: User by inject<User> ()

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var recyclerViewAdapter: GroupAdapter
    private var filmsGroupList: ArrayList<FilmGroup> = ArrayList()
    //private var user: User? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //var userId = requireArguments().getInt("user_id")

        if (user.userType == "admin"){
            fab_edit.visibility = View.VISIBLE
            fab_edit.setOnClickListener {
                var intentEdit = Intent(this.context, EditActivity::class.java)
                startActivity(intentEdit)
            }
        }

        recyclerViewAdapter = GroupAdapter(){
            var intent = Intent(
                activity,
                FilmActivity::class.java
            )
            intent.putExtra("film_id", it)
            //intent.putExtra("user_id", user.id)
            activity?.startActivity(intent)
        }
        recycle_view_tops.adapter = recyclerViewAdapter
        recycle_view_tops.layoutManager = LinearLayoutManager(activity)

        /*mainViewModel.user.observe(viewLifecycleOwner, Observer {foundUser->
            user = foundUser
        })*/
        mainViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            //createFakeElements()
            //recyclerViewAdapter.setItemList(filmsGroupList)
        })
        mainViewModel.filmsGenre.observe(viewLifecycleOwner, Observer {
            filmsGroupList.add(FilmGroup("Комедии", it))
        })
        mainViewModel.filmsYear.observe(viewLifecycleOwner, Observer {
            filmsGroupList.add(FilmGroup("2020 года", it))
        })
        mainViewModel.filmsRating.observe(viewLifecycleOwner, Observer {
            filmsGroupList.add(FilmGroup("Рейтинговые", it))
        })



        mainViewModel.initialRequest(){
            /*user: User? ->
            if (user?.userType == "admin"){
                fab_edit.visibility = View.VISIBLE
                fab_edit.setOnClickListener {
                    var intentEdit = Intent(this.context, EditActivity::class.java)
                    startActivity(intentEdit)
                }
            }*/
            filmsGroupList.clear()
            mainViewModel.initGroupsRequest(){
                recyclerViewAdapter.setItemList(filmsGroupList)
            }
        }
    }
}