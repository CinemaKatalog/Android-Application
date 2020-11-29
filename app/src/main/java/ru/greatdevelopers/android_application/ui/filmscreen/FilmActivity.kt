package ru.greatdevelopers.android_application.ui.filmscreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_film.*
import ru.greatdevelopers.android_application.ui.EditActivity
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.Utils.Utils
import ru.greatdevelopers.android_application.data.model.FilmCinema


class FilmActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: CinemaItemAdapter
    private var cinemaList: ArrayList<CinemaListItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)
        initView()
    }

    private fun initView(){

        btn_film_back.setOnClickListener {
            onBackPressed()
        }
        btn_edit_film.setOnClickListener{
            var intentEdit = Intent(this, EditActivity::class.java)
            startActivity(intentEdit)
        }
        btn_delete_film.setOnClickListener {
            val sureAlert = AlertDialog.Builder(this)
            sureAlert.setTitle(getString(R.string.label_delete))
                .setMessage(getString(R.string.text_question_sure_delete))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.label_yes_sure)){ dialog, which ->
                    onBackPressed()
                }
                .setNegativeButton(getString(R.string.label_cancel)){ dialog, which ->
                    Utils.showToast(applicationContext,
                        getString(R.string.text_delete_canceled), Toast.LENGTH_SHORT)
                }

            sureAlert.create().show()
            //sureAlert.show().getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.color_secondary))
            sureAlert.show().getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.color_secondary))
            //sureAlert.getPositiveButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.color_secondary)
            //sureAlert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.color_secondary);

            fab_add_favourite.setOnClickListener{

            }
        }

        recyclerView = findViewById(R.id.recycle_view_film)
        //createFakeElements(3)
        //recyclerViewAdapter = CinemaItemAdapter(cinemaList) {}
        recyclerViewAdapter = CinemaItemAdapter() {}
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


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
            else-> super.onOptionsItemSelected(item)
        }
    }
    /*private fun createFakeElements(count: Int) {
        for (i in 0..count) {
            cinemaList.add(FilmCinema("Название$i", "url$i", 129.9, i + 0.5))
        }
    }*/
}