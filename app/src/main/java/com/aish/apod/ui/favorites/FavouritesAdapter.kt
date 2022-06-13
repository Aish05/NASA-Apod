package com.aish.apod.ui.favorites


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aish.apod.R
import com.aish.apod.common.OnClickListener
import com.aish.apod.databinding.RowFavoritesBinding
import com.aish.apod.model.PictureData

/**
 * Represents adapter class for Favourites
 */
class FavouritesAdapter(val context: Context?,
                        val clickListener: OnClickListener) : RecyclerView.Adapter<FavouritesAdapter.FavViewHolder>() {

    var pictureList: List<PictureData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val viewBinding: RowFavoritesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_favorites, parent, false
        )
        return FavViewHolder(viewBinding)
    }


    override fun getItemCount(): Int {
        return pictureList.size
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.onBind(position)
    }

    fun setPictureData(countries: List<PictureData>) {
        this.pictureList = countries
        notifyDataSetChanged()
    }

    inner class FavViewHolder(private val viewBinding: RowFavoritesBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun onBind(position: Int) {
            val row = pictureList[position]
            viewBinding.pictureData = row
            viewBinding.onClickInterface = clickListener
        }
    }
}


