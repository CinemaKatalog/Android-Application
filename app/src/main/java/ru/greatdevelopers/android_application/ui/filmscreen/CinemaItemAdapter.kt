package ru.greatdevelopers.android_application.ui.filmscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ru.greatdevelopers.android_application.R

class CinemaItemAdapter(val onClick: (position: Int)-> Unit): RecyclerView.Adapter<CinemaItemAdapter.ViewHolder>() {

    private val values: List<CinemaListItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cinema_card_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CinemaItemAdapter.ViewHolder, position: Int) {
        holder.nameTextView?.text = values[position].name
        holder.priceTextView?.text = values[position].price.toString()
        holder.ratingTextView?.text = values[position].rating.toString()
    }

    override fun getItemCount(): Int {
       return values.size
    }

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer{
        var nameTextView: TextView? = null
        var priceTextView: TextView? = null
        var ratingTextView: TextView? = null
        init{
            nameTextView = containerView.findViewById(R.id.tv_cinema_name)
            priceTextView = containerView.findViewById(R.id.tv_cinema_price)
            ratingTextView = containerView.findViewById(R.id.tv_cinema_film_rating)
            containerView.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }
}