package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.bottom_sheet_search_options.*
import ru.greatdevelopers.android_application.R
import java.util.*


class BottomSheetFragment: BottomSheetDialogFragment() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_search_options, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerGenre = view.findViewById(R.id.spinner_genre) as Spinner
        val spinnerCountry = view.findViewById(R.id.spinner_country) as Spinner

        val sliderRating = view.findViewById(R.id.rs_rating) as RangeSlider
        sliderRating.values = listOf(0F, 5F)

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        val adapterGenres: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, genres)
        val adapterCountries: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, country)

        // Определяем разметку для использования при выборе элемента
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinnerGenre.adapter = adapterGenres
        spinnerCountry.adapter = adapterCountries


        date_picker_from.maxDate = Calendar.getInstance().timeInMillis
        date_picker_to.maxDate = Calendar.getInstance().timeInMillis

        (date_picker_from as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("day", "id", "android")).visibility =
            View.GONE
        (date_picker_from as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("month", "id", "android")).visibility =
            View.GONE

        (date_picker_to as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("day", "id", "android")).visibility =
            View.GONE
        (date_picker_to as ViewGroup).findViewById<ViewGroup>(Resources.getSystem().getIdentifier("month", "id", "android")).visibility =
            View.GONE
    }
}