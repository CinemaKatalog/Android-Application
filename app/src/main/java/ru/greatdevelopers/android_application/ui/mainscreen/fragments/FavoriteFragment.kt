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
    //private val user: User by inject<User> ()
    private val favouriteViewModel by viewModel<FavouriteViewModel>()


    private lateinit var recyclerViewAdapter: BaseItemAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initVM()
        /*favouriteViewModel.user.observe(viewLifecycleOwner){foundUser ->
            if (foundUser == null) {

                group_signed.visibility = View.GONE
                group_unsigned.visibility = View.VISIBLE

                return@observe
            }else{
                group_unsigned.visibility =View.GONE
                group_signed.visibility = View.VISIBLE
            }
        }*/

        //val userId = requireArguments().getInt("user_id")
        //recyclerView = view.findViewById(R.id.recycle_view_favorite)

        /*recyclerViewAdapter = BaseItemAdapter() {
            val intent = Intent(
                activity,
                FilmActivity::class.java
            )
            intent.putExtra("film_id", it)
            //intent.putExtra("user_id", user.id)
            activity?.startActivity(intent)
        }
        recycle_view_favorite.adapter = recyclerViewAdapter
        recycle_view_favorite.layoutManager = GridLayoutManager(activity, 3)*/

        /*favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)
        })*/
        //favouriteViewModel.initialRequest()
    }

    override fun onResume() {
        super.onResume()
        favouriteViewModel.loadUser()
        favouriteViewModel.initialRequest()
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
            /*findNavController().navigate(R.id.sign_nav_graph)*/
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
            }
        }

        favouriteViewModel.favoriteFilms.observe(viewLifecycleOwner, Observer { films ->
            recyclerViewAdapter.setItemList(films)
        })
    }
}