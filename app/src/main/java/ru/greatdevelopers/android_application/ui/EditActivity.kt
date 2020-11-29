package ru.greatdevelopers.android_application.ui

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.*
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaListItem
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.ui.filmscreen.CinemaItemAdapter
import java.util.*

class EditActivity : AppCompatActivity() {
    var genres = arrayOf(
        "Любой",
        "Комедия",
        "Детектив",
        "Триллер",
        "Документальный",
        "Мелодрамма",
        "Военный"
    )

    var country = arrayOf(
        "Не выбрано",
        "Россия",
        "США",
        "Франция",
        "Казахстан",
        "Италия",
        "Финляндия"
    )
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: CinemaItemAdapter
    private lateinit var spinnerGenre: Spinner
    private lateinit var spinnerCountry: Spinner
    private lateinit var adapterGenres: ArrayAdapter<String>
    private lateinit var adapterCountries: ArrayAdapter<String>
    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initView()
    }
    private fun initView(){

        edit_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        edit_toolbar.setNavigationOnClickListener() {
            onBackPressed() // возврат на предыдущий activity
        }

        spinnerGenre = findViewById<Spinner>(R.id.spinner_edit_genre)
        spinnerCountry = findViewById<Spinner>(R.id.spinner_edit_country)

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGenres = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres)
        adapterCountries = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, country)

        // Определяем разметку для использования при выборе элемента
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinnerGenre.adapter = adapterGenres
        spinnerCountry.adapter = adapterCountries


        date_edit_picker_to.maxDate = Calendar.getInstance().timeInMillis
        //date_edit_picker_to.minDate = Date(1897, 1, 1).toString().toLong()

        (date_edit_picker_to as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("day", "id", "android")).visibility =
            View.GONE
        (date_edit_picker_to as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("month", "id", "android")).visibility =
            View.GONE

        recyclerView = findViewById(R.id.recycle_view_options)
        createFakeElements(2)
        //recyclerViewAdapter = CinemaItemAdapter(cinemaList) {}
        recyclerViewAdapter = CinemaItemAdapter() {}
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun createFakeElements(count: Int) {
        for (i in 0..count) {
            cinemaList.add(CinemaListItem("Название$i",129.9f, i + 0.5f))
        }
    }
}