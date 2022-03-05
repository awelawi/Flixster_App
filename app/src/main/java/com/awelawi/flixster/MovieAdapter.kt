package com.awelawi.flixster

import android.app.ActivityOptions.makeSceneTransitionAnimation
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.lang.reflect.Array.get

private const val TAG = "Movie Adapter"
const val MOVIE_EXTRA = "MOVIE EXTRA"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    //    Expensive operation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    /**8**Creating an onClick Lisrener ***/
    // Define the listener interface
    interface OnItemClickListener {
        fun onItemClick(itemView: View?, position: Int)
    }

    // Define listener member variable
    private lateinit var listener: OnItemClickListener

    // Define the method that allows the parent activity or fragment to define the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val ivPoster = itemView.findViewById<ImageView>(R.id.posterImage)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)

        init{
            itemView.setOnClickListener(this)

        }
        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val radius = 30 // corner radius, higher value = more rounded
            val margin = 10 // crop margin, set to 0 for corners with no crop
//            Glide.with(this)
//                .load("http://via.placeholder.com/300.png")
//                .centerCrop() // scale image to fill the entire ImageView
//                .transform(RoundedCornersTransformation(radius, margin))
//                .into(ivImg)
            Glide.with(context).load(movie.posterImageUrl).into(ivPoster)
//            Glide.with(context).load(movie.posterImageUrl).centerCrop().transform(RoundedCorners(radius)).into(ivPoster)

        }


        override fun onClick(v: View?) {
            // 1. Get notified of the particular movie that's tapped on
            val movie = movies[adapterPosition]
            Toast.makeText(context, movie.title, Toast.LENGTH_SHORT).show()


            //2. Use the intent system too navigate to the new activity
            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("movie title, ", movie.title )
            intent.putExtra(MOVIE_EXTRA, movie)
//            makeSceneTransitionAnimation(this@MovieAdapter, tvTitle, "more_info" )
//            makeSceneTransitionAnimation(this@DetailActivity, tvTitle as View?, "moreinfo")
            context.startActivity(intent)

        }
//        fun onOptionsItemSelected(item: View): Boolean {
//            when (item.getItemId()) {
//                android.R.id.home -> {
//                    supportFinishAfterTransition()
//                    return true
//                }
//            }
//            return super.onOptionsItemSelected(item)
//        }
    }
}
