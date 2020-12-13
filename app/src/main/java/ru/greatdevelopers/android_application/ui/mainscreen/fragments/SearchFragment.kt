package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.viewmodel.SearchViewModel
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.ui.filmscreen.FilmActivity
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter


class SearchFragment : Fragment(){
    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: BaseItemAdapter
    private lateinit var linearLayoutOfSearchView: LinearLayout
    private lateinit var searchView: SearchView
    private lateinit var optionsBtn: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = view.findViewById(R.id.search_view)
        searchView.isSubmitButtonEnabled = false
        linearLayoutOfSearchView = searchView.getChildAt(0) as LinearLayout
        optionsBtn = inflater.inflate(R.layout.custom_button_search_view, container, false) as ImageButton
        optionsBtn.setImageResource(R.drawable.ic_baseline_tune_24)
        optionsBtn.setOnClickListener {
            showBottomSheetDialog()
        }
        linearLayoutOfSearchView.addView(optionsBtn)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycle_view_search)


        var userId = requireArguments().getInt("user_id")

        recyclerViewAdapter = BaseItemAdapter(){
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

        searchViewModel.allFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)
        })
        searchViewModel.initialRequest()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Log.d(TAG, "onQueryTextSubmit: $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.d(TAG, "onQueryTextChange: $newText")
                return false
            }
        })
    }

    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_search_options, null)
        val dialog = BottomSheetFragment()
        activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
    }
}