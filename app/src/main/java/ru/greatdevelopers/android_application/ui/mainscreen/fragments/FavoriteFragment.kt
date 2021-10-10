package ru.greatdevelopers.android_application.ui.mainscreen.fragments

//import ru.greatdevelopers.android_application.ui.mainscreen.fragments.ProfileFragmentDirections.actionProfileFragmentToUnregisteredFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragment
import ru.greatdevelopers.android_application.ui.mainscreen.MenuFragmentDirections
import ru.greatdevelopers.android_application.ui.mainscreen.adapters.BaseItemAdapter
import ru.greatdevelopers.android_application.viewmodel.FavouriteViewModel

class FavoriteFragment() : Fragment(R.layout.fragment_favorite){
    private val favouriteViewModel by viewModel<FavouriteViewModel>()


    private lateinit var recyclerViewAdapter: BaseItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initVM()


    }

    override fun onResume() {
        super.onResume()
        favouriteViewModel.loadUser()
        //favouriteViewModel.initialRequest()
    }

    private fun initView(){
        recyclerViewAdapter = BaseItemAdapter() {
            findNavController().navigate(
                FavoriteFragmentDirections.actionFavoriteFragmentToFilmFragment(
                    it
                )
            )
        }
        recycle_view_favorite.adapter = recyclerViewAdapter
        recycle_view_favorite.layoutManager = GridLayoutManager(activity, 3)

        btn_not_authorized_favor.setOnClickListener {
            (requireParentFragment().requireParentFragment() as MenuFragment).findNavController()
                .navigate(MenuFragmentDirections.actionMenuFragmentToSignInFragment())
        }
    }

    private fun initVM(){
        favouriteViewModel.user.observe(viewLifecycleOwner){foundUser ->
            if (foundUser == null) {
                recycle_view_favorite.visibility = View.GONE
                group_unsignet_favor.visibility = View.VISIBLE
                return@observe
            }else{
                group_unsignet_favor.visibility =View.GONE
                recycle_view_favorite.visibility = View.VISIBLE
                favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
                    recyclerViewAdapter.setItemList(films)
                })
            }
        }
    }
}