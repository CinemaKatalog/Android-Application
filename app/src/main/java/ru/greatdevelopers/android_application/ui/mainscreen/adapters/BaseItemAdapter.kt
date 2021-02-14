package ru.greatdevelopers.android_application.ui.mainscreen.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import ru.greatdevelopers.android_application.data.model.Film
import ru.greatdevelopers.android_application.R

class BaseItemAdapter(val onClick: (filmId: Int)-> Unit): RecyclerView.Adapter<BaseItemAdapter.ViewHolder>(){


    private var values: List<FilmListItem> = ArrayList()

    fun setItemList(newList: List<FilmListItem>){
        notifyDataSetChanged()
        values = newList
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView?.text = values[position].film_name
        holder.genreTextView?.text = values[position].genre_name
        holder.ratingTextView?.text = values[position].rating.toString()

        /*Glide.with(context)
            .load(new File(uri.getPath()))
            .into(imageView);*/

        holder.image?.let {
            Glide.with(holder.itemView.context).load(Uri.parse(values[position].poster)).into(
                it
            )
        }
        //holder.image?.setImageURI(Uri.parse(values[position].poster))
    }


    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        var nameTextView: TextView? = null
        var genreTextView: TextView? = null
        var ratingTextView: TextView? = null
        var image : ImageView? = null
        init {
            nameTextView = containerView.findViewById(R.id.card_film_name)
            genreTextView = containerView.findViewById(R.id.card_film_genre)
            ratingTextView = containerView.findViewById(R.id.card_film_rating)
            image = containerView.findViewById(R.id.film_image)

            containerView.setOnClickListener{
                onClick(values[adapterPosition].film_id)
            }
        }
    }
}