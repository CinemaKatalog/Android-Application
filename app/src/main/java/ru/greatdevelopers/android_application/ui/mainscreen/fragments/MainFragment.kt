package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_main.*
import ru.greatdevelopers.android_application.*
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.GroupAdapter
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.FilmGroup
import ru.greatdevelopers.android_application.ui.EditActivity
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity

class MainFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: GroupAdapter
    private var filmsGroupList: ArrayList<FilmGroup> = ArrayList()

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

        //val fab: View = view.findViewById(R.id.fab_edit)
        fab_edit.setOnClickListener {
            var intentEdit = Intent(this.context, EditActivity::class.java)
            startActivity(intentEdit)
        }
    }

    /*private fun createFakeElements(count : Int) {
        var filmList: ArrayList<Film> = ArrayList()
        for (j in 0 .. 10){
            filmList.add(Film("Название$j","Жанр$j"))
        }
        for (i in 0 .. count){
            filmsGroupList.add(FilmGroup("Название$i", filmList))
        }
    }*/

}