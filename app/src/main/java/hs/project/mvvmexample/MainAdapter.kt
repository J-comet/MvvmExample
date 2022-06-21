package hs.project.mvvmexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import hs.project.mvvmexample.network.Character

class MainAdapter(val list: List<Character>): RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent,false))
    }

    override fun onBindViewHolder(holder: MainAdapter.MainHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainHolder(private val itemView:View): RecyclerView.ViewHolder(itemView) {
        fun bindData(item: Character){
            val name = itemView.findViewById<TextView>(R.id.tv_name)
            val image = itemView.findViewById<ImageView>(R.id.iv_image)

            name.text = item.name
            image.load(item.image){
                transformations(CircleCropTransformation())
            }
        }
    }
}