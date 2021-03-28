package ru.greatdevelopers.android_application.ui.filmscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_film.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Favourite
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.User
import ru.greatdevelopers.android_application.ui.editscreen.EditActivity
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.FilmViewModel


class FilmActivity: AppCompatActivity() {
    private val user: User by inject<User> ()

    private val filmViewModel by viewModel<FilmViewModel> { parametersOf(intent.extras?.getInt("film_id")) }

    private lateinit var recyclerViewAdapter: CinemaItemAdapter
    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()

    private var film: Film? = null
    private var isFavourite: Boolean = false

    private lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        initView()
    }

    private fun initView(){
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
        recycle_view_film.layoutManager = LinearLayoutManager(this)

        filmViewModel.film.observe(this, Observer { foundFilm ->
            for ((k, v) in viewFields){
                when(k){
                    "description" -> v.text = foundFilm.description
                    "producer" -> v.text = foundFilm.producer
                    "rating" -> v.text = foundFilm.rating.toString()
                    "year" -> v.text = foundFilm.year.toString()
                }
            }

            try{
                iv_collapsing_film_poster.setImageURI(Uri.parse(foundFilm.poster))

            }catch (e: SecurityException){
                Utils.showToast(
                    this,"Photo load exception", Toast.LENGTH_SHORT
                )
                e.printStackTrace()
            }
            film_collapsing_toolbar.title = foundFilm.name
            film = foundFilm

        })
        filmViewModel.favourite.observe(this, Observer { foundFavour->
            isFavourite = foundFavour != null
            showCurrentMode(isFavourite)

            fab_add_favourite.setOnClickListener {
                if (isFavourite){
                    filmViewModel.deleteFavourite(foundFavour)
                }else{
                    filmViewModel.insertFavourite(Favourite(user.id, film!!.id)){
                        filmViewModel.favouriteRequest(user.id)
                    }
                }
                isFavourite = !isFavourite
                showCurrentMode(isFavourite)
            }
        })
        filmViewModel.favouriteRequest(user.id)
        filmViewModel.cinema.observe(this, Observer {
            recyclerViewAdapter.setItemList(it)
        })
        filmViewModel.country.observe(this, Observer {
            viewFields["country"]?.text = it.name
        })
        filmViewModel.genre.observe(this, Observer {
            viewFields["genre"]?.text = it.name
        })
        filmViewModel.initialRequest(user.id){
            user: User? ->
            if (user?.userType == "admin"){
                btn_edit_film.visibility = View.VISIBLE
                btn_edit_film.setOnClickListener{
                    var intentEdit = Intent(this, EditActivity::class.java)
                    intentEdit.putExtra("film_id", film?.id)
                    startActivity(intentEdit)
                }
                btn_delete_film.visibility = View.VISIBLE
                btn_delete_film.setOnClickListener {
                    val sureAlert = MaterialAlertDialogBuilder(this)
                    sureAlert.setTitle(getString(R.string.label_delete))
                        .setMessage(getString(R.string.text_question_sure_delete))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.label_yes_sure)){ dialog, which ->
                            filmViewModel.deleteFilm(film!!)
                            onBackPressed()
                        }
                        .setNegativeButton(getString(R.string.label_cancel)){ dialog, which ->
                            Utils.showToast(applicationContext,
                                getString(R.string.text_delete_canceled), Toast.LENGTH_SHORT)
                        }

                    sureAlert.create().show()
                }
            }
        }
        btn_film_back.setOnClickListener {
            onBackPressed()
        }


    }

    private fun showCurrentMode(isFavourite: Boolean) {

        if (isFavourite) {
            fab_add_favourite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_add_favourite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_film_toolbar, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_film_edit -> {
                var intentEdit = Intent(this, EditActivity::class.java)
                startActivity(intentEdit)
                true
            }
            R.id.menu_film_delete -> {
                Toast.makeText(this, "delete action clicked", Toast.LENGTH_LONG).show();
                true;
            }
            android.R.id.home ->{
                Toast.makeText(this,"Home action",Toast.LENGTH_LONG).show()
                true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }
    
}