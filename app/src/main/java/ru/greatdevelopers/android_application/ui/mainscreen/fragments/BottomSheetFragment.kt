package ru.greatdevelopers.android_application.ui.mainscreen.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.bottom_sheet_search_options.*
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Country
import ru.greatdevelopers.android_application.data.model.Genre
import ru.greatdevelopers.android_application.viewmodel.SearchViewModel
import java.util.*
import kotlin.collections.ArrayList


class BottomSheetFragment(private val searchViewModel: SearchViewModel) :
    BottomSheetDialogFragment() {

    private var genres = ArrayList<Genre>()
    private var country = ArrayList<Country>()

    private lateinit var adapterGenres: ArrayAdapter<Genre>
    private lateinit var adapterCountries: ArrayAdapter<Country>
    private var selectedCountry: Long? = null
    private var selectedGenre: Long? = null

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
        initSpinner()
        initDatePicker()
        //val sliderRating = view.findViewById(R.id.rs_rating) as RangeSlider
        rs_rating.values = listOf(0F, 10F)

        searchViewModel.country.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapterCountries.clear()
                adapterCountries.addAll(it)
                adapterCountries.notifyDataSetChanged()
                country = it as ArrayList<Country>

            }
        })
        searchViewModel.genre.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapterGenres.clear()
                adapterGenres.addAll(it)
                adapterGenres.notifyDataSetChanged()
                genres = it as ArrayList<Genre>

            }
        })
        searchViewModel.initialRequestOptions()

        tv_cancel.setOnClickListener {
            searchViewModel.initialRequest()
            dismiss()
        }

        btn_select.setOnClickListener {
            searchViewModel.searchByParamsRequest(
                selectedGenre,
                selectedCountry,
                date_picker_from.year,
                date_picker_to.year,
                //sliderRating.values[0],
                rs_rating.values[0],
                //sliderRating.values[1]
                rs_rating.values[1]
            )
        }
    }

    private fun initSpinner() {

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGenres =
            ArrayAdapter<Genre>(requireContext(), android.R.layout.simple_spinner_item, genres)
        adapterCountries =
            ArrayAdapter<Country>(requireContext(), android.R.layout.simple_spinner_item, country)

        // Определяем разметку для использования при выборе элемента
        adapterGenres.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinner_genre.adapter = adapterGenres
        spinner_country.adapter = adapterCountries

        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCountry =
                    if (parent?.getItemAtPosition(position).toString() == "Не выбрано") {
                        null
                    } else {
                        //parent?.getItemAtPosition(position).toString()
                        (parent?.getItemAtPosition(position) as Country).id
                    }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedCountry = null
            }
        }

        spinner_genre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedGenre =
                    if (parent?.getItemAtPosition(position).toString() == "Не выбрано") {
                        null
                    } else {
                        (parent?.getItemAtPosition(position) as Genre).id
                    }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectedGenre = null
            }
        }
    }

    private fun initDatePicker() {
        date_picker_from.maxDate = Calendar.getInstance().timeInMillis
        date_picker_to.maxDate = Calendar.getInstance().timeInMillis

        (date_picker_from as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
        (date_picker_from as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("month", "id", "android")
        ).visibility =
            View.GONE

        (date_picker_to as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("day", "id", "android")
        ).visibility =
            View.GONE
        (date_picker_to as ViewGroup).findViewById<ViewGroup>(
            Resources.getSystem().getIdentifier("month", "id", "android")
        ).visibility =
            View.GONE
    }
}