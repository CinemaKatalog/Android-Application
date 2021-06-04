package ru.greatdevelopers.android_application.ui.mainscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.greatdevelopers.android_application.data.FilmGroup
import ru.greatdevelopers.android_application.R
import ru.greatdevelopers.android_application.data.model.Film

class GroupAdapter(private val onClick: (filmId: Long)-> Unit): RecyclerView.Adapter<GroupAdapter.ViewHolder>(){

    private var values: List<FilmGroup> = ArrayList()

    fun setItemList(newList: List<FilmGroup>){
        notifyDataSetChanged()
        values = newList
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_container, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView?.text = values[position].groupName
        //holder?.recyclerViewAdapter = BaseItemAdapter(values[position].filmList, onClick)
        //holder.recyclerViewAdapter = BaseItemAdapter(onClick)
        holder.recyclerViewAdapter?.setItemList(values[position].filmList)
        holder.recyclerView?.adapter = holder.recyclerViewAdapter
        //holder?.recyclerView?.layoutManager = LinearLayoutManager(activity)
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var titleTextView: TextView? = null
        var recyclerView: RecyclerView? = null
        var recyclerViewAdapter: BaseItemAdapter? = null
        init {
            titleTextView = itemView.findViewById(R.id.title_text_view)
            recyclerView = itemView.findViewById(R.id.item_container)
            recyclerViewAdapter = BaseItemAdapter(onClick)
        }
    }
}