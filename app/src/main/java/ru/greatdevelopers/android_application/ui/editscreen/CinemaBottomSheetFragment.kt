package ru.greatdevelopers.android_application.ui.editscreen

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.alert_cinema_add.view.*
import kotlinx.android.synthetic.main.bottom_sheet_cinema_add.*
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Cinema
import ru.greatdevelopers.android_application.data.model.FilmCinema
import ru.greatdevelopers.android_application.utils.Utils
import ru.greatdevelopers.android_application.viewmodel.EditViewModel

class CinemaBottomSheetFragment(private val editViewModel: EditViewModel) :
    BottomSheetDialogFragment() {

    companion object {
        const val IS_CINEMA_EDIT_MODE = "IS_CINEMA_EDIT_MODE"
        const val IS_FILM_CINEMA_EDIT_MODE = "IS_FILM_CINEMA_EDIT_MODE"
    }

    private var isEditMode: Boolean = false
    private var isAddMode: Boolean = false
    private var cinema: Cinema? = null

    var cinemas = ArrayList<Cinema>()

    private lateinit var adapterCinemas: ArrayAdapter<Cinema>
    lateinit var viewFields: Map<String, TextView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper =
            ContextThemeWrapper(activity, R.style.AppTheme) // your app theme here
        val view = inflater.cloneInContext(contextThemeWrapper)
            .inflate(R.layout.bottom_sheet_cinema_add, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewFields = mapOf(
            "name" to et_cinema_name,
            "cinema_url" to et_cinema_url,
            "cinema_email" to et_cinema_email,
            "price" to et_price,
            "film_url" to et_film_url
        )
        initSpinner()

        //isEditMode = savedInstanceState?.getBoolean(IS_CINEMA_EDIT_MODE, false) ?: false
        if (requireArguments().getString("cinema_url") != null) {
            isEditMode = true
        }
        showCurrentMode(isEditMode)

        editViewModel.cinema.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapterCinemas.clear()
                adapterCinemas.addAll(it)
                adapterCinemas.notifyDataSetChanged()
                cinemas = it as ArrayList<Cinema>

                if(isEditMode){
                    spinner_name.setSelection(getPosition(requireArguments().getString("cinema_url")!!))
                }
            }
        })
        editViewModel.filmCinema.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                for ((k, v) in viewFields) {
                    when (k) {
                        "price" -> v.text = it.price.toString()
                        "film_url" -> v.text = it.pageUrl
                    }
                }
                rs_cinema_add_rating.values = arrayListOf(it.rating)
            }
            editViewModel.cinemaInitRequest()
        })
        editViewModel.initialRequestCinemas(requireArguments().getString("cinema_url"))

        btn_cinema_add_save.setOnClickListener {
            if (!isEditMode) {
                if (cinema != null &&
                    viewFields["price"]?.text.toString().isNotEmpty() &&
                    viewFields["film_url"]?.text.toString().isNotEmpty()
                ) {
                    if(viewFields["film_url"]?.text.toString().matches(Regex(Utils.URL_PATTERN))){
                        editViewModel.insertFilmCinema(
                            FilmCinema(
                                viewFields["film_url"]?.text.toString(),
                                requireArguments().getLong("film_id"),
                                cinema!!.url,
                                viewFields["price"]?.text.toString().toFloat(),
                                rs_cinema_add_rating.values[0]
                            )
                        )

                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                        )
                    }else{
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.wrong_url), Toast.LENGTH_SHORT
                        )
                    }
                } else {
                    Utils.showToast(
                        requireContext(),
                        getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                    )
                }
            } else {
                if (cinema != null &&
                    viewFields["price"]?.text.toString().isNotEmpty() &&
                    viewFields["film_url"]?.text.toString().isNotEmpty()
                ) {
                    if(viewFields["film_url"]?.text.toString().matches(Regex(Utils.URL_PATTERN))){
                        editViewModel.updateFilmCinema(
                            FilmCinema(
                                viewFields["film_url"]?.text.toString(),
                                requireArguments().getLong("film_id"),
                                cinema!!.url,
                                viewFields["price"]?.text.toString().toFloat(),
                                rs_cinema_add_rating.values[0]
                            )
                        )
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                        )
                    }else{
                        Utils.showToast(
                            requireContext(),
                            getString(R.string.wrong_url), Toast.LENGTH_SHORT
                        )
                    }

                } else {
                    Utils.showToast(
                        requireContext(),
                        getString(R.string.text_not_complete), Toast.LENGTH_SHORT
                    )
                }
            }
            isEditMode = true
        }

        btn_edit_add_cinema.setOnClickListener {

            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_cinema_add, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            addAlert.setTitle(getString(R.string.label_add))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_cinema_alert_name.text.toString().isNotEmpty() &&
                        promptsView.et_cinema_alert_url.text.toString().isNotEmpty() &&
                        promptsView.et_cinema_alert_email.text.toString().isNotEmpty()
                    ) {
                        if(promptsView.et_cinema_alert_url?.text.toString().matches(Regex(Utils.URL_PATTERN))){
                            if(promptsView.et_cinema_alert_email?.text.toString().matches(Regex(Utils.EMAIL_PATTERN))){

                                editViewModel.insertCinema(
                                    Cinema(
                                        promptsView.et_cinema_alert_url.text.toString(),
                                        promptsView.et_cinema_alert_name.text.toString(),
                                        promptsView.et_cinema_alert_email.text.toString()
                                    )
                                ) {
                                    Utils.showToast(
                                        requireContext(),
                                        getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                                    )
                                    editViewModel.initialRequestCinemas(requireArguments().getString("cinema_url"))
                                    editViewModel.initialRequest()
                                }
                            }else{
                                Utils.showToast(
                                    requireContext(),
                                    getString(R.string.wrong_email), Toast.LENGTH_SHORT
                                )
                            }
                        }else{
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.wrong_url), Toast.LENGTH_SHORT
                            )
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

        btn_edit_change_cinema.setOnClickListener {

            val promptsView: View =
                LayoutInflater.from(requireContext()).inflate(R.layout.alert_cinema_add, null)
            val addAlert = MaterialAlertDialogBuilder(requireContext())
            addAlert.setView(promptsView)

            (promptsView.et_cinema_alert_name as TextView).text = cinema?.name
            (promptsView.et_cinema_alert_url as TextView).text = cinema?.url
            (promptsView.et_cinema_alert_email as TextView).text = cinema?.email
            promptsView.et_cinema_alert_url.isEnabled = false
            promptsView.et_cinema_alert_url.isFocusable = false
            promptsView.et_cinema_alert_url.isFocusableInTouchMode = false

            addAlert.setTitle(getString(R.string.label_change))
                .setMessage(getString(R.string.text_question_add_alert))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_save)) { dialog, which ->
                    if (promptsView.et_cinema_alert_name.text.toString().isNotEmpty() &&
                        promptsView.et_cinema_alert_email.text.toString().isNotEmpty()
                    ) {
                        if(promptsView.et_cinema_alert_email?.text.toString().matches(Regex(Utils.EMAIL_PATTERN))){
                            editViewModel.updateCinema(
                                Cinema(
                                    promptsView.et_cinema_alert_url.text.toString(),
                                    promptsView.et_cinema_alert_name.text.toString(),
                                    promptsView.et_cinema_alert_email.text.toString()
                                )
                            ){
                                Utils.showToast(
                                    requireContext(),
                                    getString(R.string.text_cinema_add_complete), Toast.LENGTH_SHORT
                                )
                                editViewModel.initialRequestCinemas(requireArguments().getString("cinema_url"))
                                editViewModel.initialRequest()
                            }
                        }else{
                            Utils.showToast(
                                requireContext(),
                                getString(R.string.wrong_email), Toast.LENGTH_SHORT
                            )
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

        tv_cancel_cinema_add.setOnClickListener {
            editViewModel.deleteFilmCinema(requireArguments().getLong("film_id"), cinema!!.url){
                editViewModel.initialRequest()
                dismiss()
            }
        }

    }

    private fun initSpinner() {
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterCinemas = ArrayAdapter<Cinema>(requireContext(), android.R.layout.simple_spinner_item, cinemas)
        // Определяем разметку для использования при выборе элемента
        adapterCinemas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Применяем адаптер к элементу spinner
        spinner_name.adapter = adapterCinemas

        spinner_name.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            val info = viewFields.filter {
                setOf(
                    "name",
                    "cinema_url",
                    "cinema_email"
                ).contains(it.key)
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                for ((k, v) in info) {
                    when (k) {
                        "name" -> v.text = (parent.getItemAtPosition(position) as Cinema).name
                        "cinema_url" -> v.text = (parent.getItemAtPosition(position) as Cinema).url
                        "cinema_email" -> v.text =
                            (parent.getItemAtPosition(position) as Cinema).email
                    }
                }
                cinema = parent.getItemAtPosition(position) as Cinema
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                for ((k, v) in info) {
                    v as EditText
                    v.isFocusable = false
                    v.isFocusableInTouchMode = false
                    v.isEnabled = false
                    //v.background.alpha = if (isEdit) 255 else 0
                }
            }
        }

    }

    private fun showCurrentMode(isEdit: Boolean) {
        if (requireArguments().getString("cinema_url") != null) {
            //spinner_name.setSelection(getPosition(requireArguments().getString("cinema_url")!!))
        }
        spinner_name.isEnabled = !isEdit
        spinner_name.isFocusable = !isEdit
        spinner_name.isFocusableInTouchMode = !isEdit
    }

    private fun getPosition(cinemaUrl: String): Int {
        var position = 0
        for (i in cinemas) {
            if (cinemas[position].url == cinemaUrl) break else position++
        }
        return position
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_CINEMA_EDIT_MODE, isEditMode)
    }
}