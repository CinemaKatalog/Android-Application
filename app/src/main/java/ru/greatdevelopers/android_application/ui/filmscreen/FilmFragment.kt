package ru.greatdevelopers.android_application.ui.filmscreen

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_film.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.FilmViewModel


class FilmFragment : Fragment(R.layout.activity_film) {

    private val args: FilmFragmentArgs by navArgs()

    private val filmViewModel by viewModel<FilmViewModel> {
        parametersOf(args.filmId)
    }

    private lateinit var recyclerViewAdapter: CinemaItemAdapter
    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()

    private var film: Film? = null
    private var user: User? = null
    private var isFavourite: Boolean = false

    private lateinit var viewFields: Map<String, TextView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        //setting toolbar
        //setSupportActionBar(findViewById(R.id.film_toolbar))
        //home navigation
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        //var userId = intent.extras?.getInt("user_id")

        viewFields = mapOf(
            "description" to tv_film_description_body,
            "producer" to tv_film_producer,
            "country" to tv_film_country,
            "genre" to tv_film_genre,
            "rating" to tv_film_rating,
            "year" to tv_film_year
        )


        recyclerViewAdapter = CinemaItemAdapter() {}
        recycle_view_film.adapter = recyclerViewAdapter
        recycle_view_film.layoutManager = LinearLayoutManager(requireContext())

        filmViewModel.film.observe(viewLifecycleOwner, Observer { foundFilm ->
            for ((k, v) in viewFields) {
                when (k) {
                    "description" -> v.text = foundFilm.description
                    "producer" -> v.text = foundFilm.producer
                    "rating" -> v.text = foundFilm.rating.toString()
                    "year" -> v.text = foundFilm.year.toString()
                }
            }

            try {
                iv_collapsing_film_poster.setImageURI(Uri.parse(foundFilm.poster))

            } catch (e: SecurityException) {
                Utils.showToast(
                    requireContext(), "Photo load exception", Toast.LENGTH_SHORT
                )
                e.printStackTrace()
            }
            film_collapsing_toolbar.title = foundFilm.name
            film = foundFilm

        })
        filmViewModel.favourite.observe(viewLifecycleOwner, Observer { foundFavour ->
            isFavourite = foundFavour != null
            showCurrentMode(isFavourite)

            fab_add_favourite.setOnClickListener {
                if (isFavourite) {
                    filmViewModel.deleteFavourite(foundFavour)
                } else {
                    filmViewModel.insertFavourite(Favourite(user!!.id, film!!.id)) {
                        filmViewModel.favouriteRequest()
                    }
                }
                isFavourite = !isFavourite
                showCurrentMode(isFavourite)
            }
        })
        filmViewModel.favouriteRequest()
        filmViewModel.cinema.observe(viewLifecycleOwner, Observer {
            recyclerViewAdapter.setItemList(it)
        })
        filmViewModel.country.observe(viewLifecycleOwner, Observer {
            viewFields["country"]?.text = it.name
        })
        filmViewModel.genre.observe(viewLifecycleOwner, Observer {
            viewFields["genre"]?.text = it.name
        })
        filmViewModel.initialRequest()
        filmViewModel.user.observe(viewLifecycleOwner) { user: User ->
            this.user = user
            if (user.userType == "admin") {
                btn_edit_film.visibility = View.VISIBLE
                btn_edit_film.setOnClickListener {
                    findNavController().navigate(
                        FilmFragmentDirections.actionFilmFragmentToEditFragment()
                            .setFilmId(film!!.id)
                    )
                }
                btn_delete_film.visibility = View.VISIBLE
                btn_delete_film.setOnClickListener {
                    val sureAlert = MaterialAlertDialogBuilder(requireContext())
                    sureAlert.setTitle(getString(R.string.label_delete))
                        .setMessage(getString(R.string.text_question_sure_delete))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.label_yes_sure)) { dialog, which ->
                            filmViewModel.deleteFilm(film!!)
                            findNavController().popBackStack()
                        }
                        .setNegativeButton(getString(R.string.label_cancel)) { dialog, which ->
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_delete_canceled), Toast.LENGTH_SHORT
                            )
                        }

                    sureAlert.create().show()
                }
            }
        }
        btn_film_back.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun showCurrentMode(isFavourite: Boolean) {

        if (isFavourite) {
            fab_add_favourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_add_favourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_film_toolbar, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_film_edit -> {
                findNavController().navigate(
                    FilmFragmentDirections.actionFilmFragmentToEditFragment().setFilmId(film!!.id)
                )
                true
            }
            R.id.menu_film_delete -> {
                Toast.makeText(requireContext(), "delete action clicked", Toast.LENGTH_LONG).show();
                true;
            }
            android.R.id.home -> {
                Toast.makeText(requireContext(), "Home action", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}