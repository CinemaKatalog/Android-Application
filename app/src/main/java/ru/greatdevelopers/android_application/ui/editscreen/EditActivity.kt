package ru.greatdevelopers.android_application.ui.editscreen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaItemAdapter
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem
import ru.greatdevelopers.android_application.viewmodel.EditViewModel
import java.util.*


class EditActivity : AppCompatActivity() {
    private val editViewModel by viewModel<EditViewModel> { parametersOf(intent.extras?.getInt("film_id")) }

    private var film: Film? = null

    companion object {
        const val YOUR_IMAGE_CODE: Int = 1
        const val READ_EXTERNAL_STORAGE_PERMISSION_CODE: Int = 1

        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private var isEditMode = false

    lateinit var viewFields: Map<String, TextView>

    var genres = arrayOf(
        "Комедия",
        "Детектив",
        "Триллер",
        "Документальный",
        "Мелодрамма",
        "Военный"
    )

    var country = arrayOf(
        "Россия",
        "США",
        "Франция",
        "Казахстан",
        "Италия",
        "Финляндия"
    )

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: CinemaItemAdapter

    private lateinit var adapterGenres: ArrayAdapter<String>
    private lateinit var adapterCountries: ArrayAdapter<String>

    private var selectedImageUri: Uri? = null

    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {

        viewFields = mapOf(
            "name" to et_film_name,
            "description" to et_film_description,
            "producer" to et_film_producer
        )
        edit_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        edit_toolbar.setNavigationOnClickListener() {
            onBackPressed() // возврат на предыдущий activity
        }

        //isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        initSpinners()
        initDatePickers()

        recyclerView = findViewById(R.id.recycle_view_options)
        recyclerViewAdapter = CinemaItemAdapter() {
            showBottomSheetDialog(it.film_id, it.site_url)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        editViewModel.cinemaList.observe(this, Observer {
            if (it != null) recyclerViewAdapter.setItemList(it)
        })
        editViewModel.film.observe(this, Observer { foundFilm ->
            if (foundFilm != null) {
                for ((k, v) in viewFields) {
                    when (k) {
                        "name" -> v.text = foundFilm.name
                        "description" -> v.text = foundFilm.description
                        "producer" -> v.text = foundFilm.producer
                    }
                }
                selectedImageUri = Uri.parse(foundFilm.poster)

                try{
                    iv_edit_poster.setImageURI(selectedImageUri)

                }catch (e: SecurityException){
                    Utils.showToast(
                        this,"Photo load exception", Toast.LENGTH_SHORT
                    )
                    e.printStackTrace()
                }

                date_edit_picker.init(
                    foundFilm.year,
                    1,
                    1,
                    DatePicker.OnDateChangedListener { _, _, _, _ -> })
                spinner_edit_country.setSelection(getCountryPosition(foundFilm))
                spinner_edit_genre.setSelection(getGenrePosition(foundFilm))
                isEditMode = true
                showCurrentMode(isEditMode)

            }
            film = foundFilm
        })
        editViewModel.initialRequest()

        btn_add_poster.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this.baseContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Проверка наличия разрешений
                // Если нет разрешения на использование соответсвующих разркешений выполняем какие-то действия
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_PERMISSION_CODE
                )
            }else{
                val intentPoster = Intent()
                //intentPoster.type = "image/*"
                //intentPoster.action = Intent.ACTION_GET_CONTENT
                /*startActivityForResult(
                    Intent.createChooser(intentPoster, "select a picture"),
                    YOUR_IMAGE_CODE
                )*/
                intentPoster.action = Intent.ACTION_OPEN_DOCUMENT
                intentPoster.addCategory(Intent.CATEGORY_OPENABLE)
                intentPoster.type = "image/*"
                startActivityForResult(Intent.createChooser(intentPoster, "select a picture"), YOUR_IMAGE_CODE)
            }
        }
        btn_edit_save.setOnClickListener {

            if (viewFields["name"]?.text.toString().isNotEmpty() &&
                viewFields["description"]?.text.toString().isNotEmpty() &&
                viewFields["producer"]?.text.toString().isNotEmpty() &&
                selectedImageUri != null
            ) {
                if (isEditMode) {
                    editViewModel.updateFilm(
                        Film(
                            film!!.id,
                            viewFields["name"]?.text.toString(),
                            genres[spinner_edit_genre.selectedItemPosition],
                            country[spinner_edit_country.selectedItemPosition],
                            viewFields["producer"]?.text.toString(),
                            viewFields["description"]?.text.toString(),
                            selectedImageUri.toString(),
                            date_edit_picker.year,
                            4.5f
                        )
                    ) {
                        Utils.showToast(
                            this,
                            getString(R.string.text_changes_saved), Toast.LENGTH_SHORT
                        )
                    }
                } else {
                    editViewModel.insertFilm(
                        Film(
                            name = viewFields["name"]?.text.toString(),
                            genre = genres[spinner_edit_genre.selectedItemPosition],
                            country = country[spinner_edit_country.selectedItemPosition],
                            producer = viewFields["producer"]?.text.toString(),
                            description = viewFields["description"]?.text.toString(),
                            poster = selectedImageUri.toString(),
                            year = date_edit_picker.year,
                            rating = 4.5f
                        )
                    ) {
                        Utils.showToast(
                            this,
                            getString(R.string.text_changes_saved), Toast.LENGTH_SHORT
                        )
                        isEditMode = true
                        showCurrentMode(isEditMode)

                    }
                }
                editViewModel.initialRequest()
            } else {
                Utils.showToast(
                    this,
                    getString(R.string.text_film_fields_complete), Toast.LENGTH_SHORT
                )
            }
        }
        btn_add_cinema.setOnClickListener {
            showBottomSheetDialog(film!!.id!!)
        }
        showCurrentMode(isEditMode)

    }

    private fun initDatePickers() {
        date_edit_picker.maxDate = Calendar.getInstance().timeInMillis
        //date_edit_picker_to.minDate = Date(1897, 1, 1).toString().toLong()

        (date_edit_picker as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
        (date_edit_picker as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("month", "id", "android")
        ).visibility =
            View.GONE
    }

    private fun initSpinners() {

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGenres = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres)
        adapterCountries = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country)

        // Определяем разметку для использования при выборе элемента
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinner_edit_genre.adapter = adapterGenres
        spinner_edit_country.adapter = adapterCountries
    }

    private fun getCountryPosition(film: Film): Int {
        var country: Int = 0

        for (index in this.country.indices) {
            country = if (this.country[index] == film.country) {
                index
            } else {
                0
            }
        }

        return country
    }

    private fun getGenrePosition(film: Film): Int {
        var genre: Int = 0
        genres.forEachIndexed { index, element ->
            genre = if (element == film.genre) {
                index
            } else {
                0
            }
        }
        return genre
    }

    private fun showBottomSheetDialog(filmId: Int, cinemaUrl: String? = null) {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_cinema_add, null)
        val dialog = CinemaBottomSheetFragment(editViewModel)
        var arg = Bundle()
        arg.putInt("film_id", filmId)
        if (cinemaUrl != null) {
            arg.putString("cinema_url", cinemaUrl)
        }
        dialog.arguments = arg
        //supportFragmentManager.beginTransaction().replace()
        dialog.show(supportFragmentManager, dialog.tag)
        //supportFragmentManager?.let { dialog.show(it, dialog.tag) }
    }

    private fun showCurrentMode(isEdit: Boolean) {

        if (isEdit) {
            btn_add_cinema.visibility = View.VISIBLE
        } else {
            btn_add_cinema.visibility = View.GONE
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === YOUR_IMAGE_CODE) {
            if (resultCode === RESULT_OK) {
                selectedImageUri = data?.data
                iv_edit_poster.setImageURI(selectedImageUri)

                val takeFlags: Int = (data?.flags!!
                        and (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))

                try {
                    selectedImageUri?.let {
                        this.contentResolver
                            .takePersistableUriPermission(it, takeFlags)
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    Activity().baseContext,
                    "Разрешение получено",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Разрешение не получено", Toast.LENGTH_SHORT).show()
            }
        }
    }
}