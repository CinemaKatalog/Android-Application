package ru.greatdevelopers.android_application.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.R

class HeaderAdapter(private val values: List<Film>):RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.header_container_main, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView?.text = values[position].name
        //holder?.genreTextView?.text = values[position].genre
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameTextView: TextView? = null
        var genreTextView: TextView? = null
        init {
            nameTextView = itemView.findViewById(R.id.header_film_name)
            genreTextView = itemView.findViewById(R.id.header_film_genre)
        }
    }
}