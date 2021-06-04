package ru.greatdevelopers.android_application.ui.editscreen

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.alert_country_genre.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaItemAdapter
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.EditViewModel
import java.util.*

class EditFragment : Fragment(R.layout.activity_edit) {

    companion object {
        const val YOUR_IMAGE_CODE: Int = 1
        const val READ_EXTERNAL_STORAGE_PERMISSION_CODE: Int = 1

        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    private val args: EditFragmentArgs by navArgs()

    private val editViewModel by viewModel<EditViewModel> {
        parametersOf(args.filmId)
    }
    private var film: Film? = null

    private var isEditMode = false
    private var rating = 0f

    lateinit var viewFields: Map<String, TextView>

    var genreList = ArrayList<Genre>()
    var countryList = ArrayList<Country>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: CinemaItemAdapter

    private lateinit var adapterGenres: ArrayAdapter<Genre>
    private lateinit var adapterCountries: ArrayAdapter<Country>

    private var selectedImageUri: Uri? = null

    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {

        //editViewModel.insertCountry(Country(name = "Не выбрано")){}
        //editViewModel.insertGenre(Genre(name = "Любой")){}
        viewFields = mapOf(
            "name" to et_film_name,
            "description" to et_film_description,
            "producer" to et_film_producer
        )
        edit_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        edit_toolbar.setNavigationOnClickListener() {
            //onBackPressed() // возврат на предыдущий activity
        }

        //isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        initSpinners()
        initDatePickers()

        recyclerView = recycle_view_options
        recyclerViewAdapter = CinemaItemAdapter() {
            showBottomSheetDialog(it.film_id, it.page_url, it.site_url)
        }
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        editViewModel.cinemaList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                var sum = 0f
                recyclerViewAdapter.setItemList(it)
                for (i in it) {
                    sum += i.rating
                }
                if (sum > 0) {
                    rating = sum / it.size
                }
            }
        })
        editViewModel.countryList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapterCountries.clear()
                adapterCountries.addAll(it)
                adapterCountries.notifyDataSetChanged()
                countryList = it as ArrayList<Country>

                if (isEditMode) {
                    spinner_edit_country.setSelection(getCountryPosition(film))
                }
            }
        })
        editViewModel.genreList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapterGenres.clear()
                adapterGenres.addAll(it)
                adapterGenres.notifyDataSetChanged()
                genreList = it as ArrayList<Genre>

                if (isEditMode) {
                    spinner_edit_genre.setSelection(getGenrePosition(film))
                }
            }
        })
        editViewModel.film.observe(viewLifecycleOwner, Observer { foundFilm ->
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
                        requireContext(), "Photo load exception", Toast.LENGTH_SHORT
                    )
                    e.printStackTrace()
                }

                date_edit_picker.init(
                    foundFilm.year,
                    1,
                    1,
                    DatePicker.OnDateChangedListener { _, _, _, _ -> })

                //spinner_edit_country.setSelection(getCountryPosition(foundFilm))
                //spinner_edit_genre.setSelection(getGenrePosition(foundFilm))


                film = foundFilm
                isEditMode = true
                showCurrentMode(isEditMode)
            }
            editViewModel.spinnerInitRequest()
        })
        editViewModel.initialRequest()

        btn_add_poster.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Проверка наличия разрешений
                // Если нет разрешения на использование соответсвующих разркешений выполняем какие-то действия
                ActivityCompat.requestPermissions(
                    requireActivity(),
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
                selectedImageUri != null &&
                spinner_edit_country.selectedItemPosition != 0 &&
                spinner_edit_genre.selectedItemPosition != 0
            ) {
                if (isEditMode) {
                    editViewModel.updateFilm(
                        Film(
                            film!!.id,
                            viewFields["name"]?.text.toString(),
                            genreList[spinner_edit_genre.selectedItemPosition].id,
                            countryList[spinner_edit_country.selectedItemPosition].id,
                            viewFields["producer"]?.text.toString(),
                            viewFields["description"]?.text.toString(),
                            selectedImageUri.toString(),
                            date_edit_picker.year,
                            rating
                        )
                    ) {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_changes_saved), Toast.LENGTH_SHORT
                        )
                        //editViewModel.initialRequest()
                    }
                } else {
                    film = Film(
                        name = viewFields["name"]?.text.toString(),
                        genre = genreList[spinner_edit_genre.selectedItemPosition].id,
                        country = countryList[spinner_edit_country.selectedItemPosition].id,
                        producer = viewFields["producer"]?.text.toString(),
                        description = viewFields["description"]?.text.toString(),
                        poster = selectedImageUri.toString(),
                        year = date_edit_picker.year,
                        rating = rating
                    )
                    editViewModel.insertFilm(film!!) {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_changes_saved), Toast.LENGTH_SHORT
                        )
                        //editViewModel.setFilmId(film!!.id)
                        isEditMode = true
                        //showCurrentMode(isEditMode)
                        //editViewModel.initialRequest()
                        findNavController().popBackStack()
                    }
                }
            } else {
                Utils.showToast(
                    requireContext(),
                    getString(R.string.text_film_fields_complete), Toast.LENGTH_SHORT
                )
            }
        }
        btn_add_cinema.setOnClickListener {
            showBottomSheetDialog(film!!.id)
        }

        btn_add_country.setOnClickListener {
            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_country_genre, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            addAlert.setTitle(getString(R.string.label_add))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_alert_name.text.toString().isNotEmpty()) {
                        editViewModel.insertCountry(
                            Country(name = promptsView.et_alert_name.text.toString())
                        ) {
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                            )
                            editViewModel.initialRequest()
                        }
                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                        )
                    }
                }
                .setNegativeButton(getString(R.string.label_cancel)) { dialog, which ->

                }
            addAlert.create().show()
        }
        btn_change_country.setOnClickListener {
            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_country_genre, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            (promptsView.et_alert_name as TextView).text =
                spinner_edit_country.selectedItem.toString()

            addAlert.setTitle(getString(R.string.label_change))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_alert_name.text.toString().isNotEmpty()) {
                        editViewModel.updateCountry(
                            Country(
                                id = (spinner_edit_country.selectedItem as Country).id,
                                name = promptsView.et_alert_name.text.toString()
                            )
                        ) {
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                            )
                            editViewModel.initialRequest()
                        }
                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                        )
                    }
                }
                .setNegativeButton(getString(R.string.label_cancel)) { dialog, which ->

                }
            addAlert.create().show() }
        btn_add_genre.setOnClickListener {
            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_country_genre, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            addAlert.setTitle(getString(R.string.label_add))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_alert_name.text.toString().isNotEmpty()) {
                        editViewModel.insertGenre(
                            Genre(name = promptsView.et_alert_name.text.toString())
                        ) {
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                            )
                            editViewModel.initialRequest()
                        }
                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                        )
                    }
                }
                .setNegativeButton(getString(R.string.label_cancel)) { dialog, which ->

                }
            addAlert.create().show() }
        btn_change_genre.setOnClickListener {
            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_country_genre, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            (promptsView.et_alert_name as TextView).text =
                spinner_edit_genre.selectedItem.toString()
            addAlert.setTitle(getString(R.string.label_change))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_alert_name.text.toString().isNotEmpty()) {
                        editViewModel.updateGenre(
                            Genre(
                                id = (spinner_edit_genre.selectedItem as Genre).id,
                                name = promptsView.et_alert_name.text.toString()
                            )
                        ) {
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                            )
                            editViewModel.initialRequest()
                        }
                    } else {
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                        )
                    }
                }
                .setNegativeButton(getString(R.string.label_cancel)) { dialog, which ->

                }
            addAlert.create().show()
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
        adapterGenres =
            ArrayAdapter<Genre>(requireContext(), android.R.layout.simple_spinner_item, genreList)
        adapterCountries = ArrayAdapter<Country>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            countryList
        )

        // Определяем разметку для использования при выборе элемента
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinner_edit_genre.adapter = adapterGenres
        spinner_edit_country.adapter = adapterCountries
    }

    private fun getCountryPosition(film: Film?): Int {
        var position: Int = 0

        for(i in this.countryList){
            if (this.countryList[position].id == film?.country) break else position++
        }

        return position
    }

    private fun getGenrePosition(film: Film?): Int {
        var position: Int = 0

        for(i in 1 .. adapterGenres.count){
            if (this.genreList[position].id == film?.genre) break else position++
        }

        return position
    }

    private fun showBottomSheetDialog(filmId: Long, pageUrl: String? = null, cinemaUrl: String? = null) {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_cinema_add, null)
        val dialog = CinemaBottomSheetFragment(editViewModel)
        var arg = Bundle()
        arg.putLong("film_id", filmId)
        if (cinemaUrl != null) {
            arg.putString("cinema_url", cinemaUrl)
            arg.putString("page_url", pageUrl)
        }
        dialog.arguments = arg
        //supportFragmentManager.beginTransaction().replace()
        dialog.show(parentFragmentManager, dialog.tag)
        //supportFragmentManager?.let { dialog.show(it, dialog.tag) }
    }

    private fun showCurrentMode(isEdit: Boolean) {

        if (isEdit) {
            btn_add_cinema.visibility = View.VISIBLE
            //spinner_edit_country.setSelection(getCountryPosition(film))
            //spinner_edit_genre.setSelection(getGenrePosition(film))
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
        if (requestCode == YOUR_IMAGE_CODE) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data?.data
                iv_edit_poster.setImageURI(selectedImageUri)

                val takeFlags: Int = (data?.flags!!
                        and (Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_GRANT_WRITE_URI_PERMISSION))

                try {
                    selectedImageUri?.let {
                        requireContext().contentResolver
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

                Utils.showToast(
                    requireContext(),
                    "Разрешение получено", Toast.LENGTH_SHORT
                )
            } else {
                Toast.makeText(requireContext(), "Разрешение не получено", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}