package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter
import ru.greatdevelopers.android_application.viewmodel.SearchViewModel


class SearchFragment : Fragment(){
    //private val user: User by inject<User> ()

    private val searchViewModel by viewModel<SearchViewModel>()

    private lateinit var recyclerViewAdapter: BaseItemAdapter
    private lateinit var linearLayoutOfSearchView: LinearLayout
    private lateinit var optionsBtn: ImageButton
    private lateinit var searchView: SearchView


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
        //recyclerView = view.findViewById(R.id.recycle_view_search)
        /*search_view.isSubmitButtonEnabled = false
        linearLayoutOfSearchView = search_view.getChildAt(0) as LinearLayout
        optionsBtn.setImageResource(R.drawable.ic_baseline_tune_24)
        optionsBtn.setOnClickListener {
            showBottomSheetDialog()
        }
        linearLayoutOfSearchView.addView(optionsBtn)*/

        //var userId = requireArguments().getInt("user_id")

        recyclerViewAdapter = BaseItemAdapter() {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToFilmFragment(
                    it
                )
            )
        }

        recycle_view_search.adapter = recyclerViewAdapter
        recycle_view_search.layoutManager = GridLayoutManager(activity, 3)

        searchViewModel.allFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)
        })
        searchViewModel.initialRequest()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.searchRequest(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchViewModel.searchRequest(newText)
                return false
            }
        })
    }

    private fun showBottomSheetDialog() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_search_options, null)
        val dialog = BottomSheetFragment(searchViewModel)
        activity?.supportFragmentManager?.let { dialog.show(it, dialog.tag) }
    }
}