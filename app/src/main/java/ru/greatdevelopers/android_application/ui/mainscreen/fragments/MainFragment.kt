package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.*
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.GroupAdapter
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.FilmGroup
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.EditActivity
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.viewmodel.MainViewModel

class MainFragment : Fragment(){
    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: GroupAdapter
    private var filmsGroupList: ArrayList<FilmGroup> = ArrayList()
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycle_view_tops)
        //createFakeElements(35)
        //recyclerViewAdapter = GroupAdapter(filmsGroupList){
        recyclerViewAdapter = GroupAdapter(){
            var intent = Intent(
                activity,
                FilmActivity::class.java
            )
            activity?.startActivity(intent)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        mainViewModel.user.observe(viewLifecycleOwner, Observer {foundUser->
            user = foundUser
        })
        mainViewModel.films.observe(viewLifecycleOwner, Observer { films ->
            createFakeElements(films)
            recyclerViewAdapter.setItemList(filmsGroupList)
        })

        mainViewModel.initialRequest(requireArguments().getInt("user_id")){
            user: User? ->
            if (user?.userType == "admin"){
                fab_edit.visibility = View.VISIBLE
                fab_edit.setOnClickListener {
                    var intentEdit = Intent(this.context, EditActivity::class.java)
                    startActivity(intentEdit)
                }
            }
        }
    }

    private fun createFakeElements(filmList: List<Film>) {

        for (i in 0 .. 5){
            filmsGroupList.add(FilmGroup("Название$i", filmList))
        }
    }

}