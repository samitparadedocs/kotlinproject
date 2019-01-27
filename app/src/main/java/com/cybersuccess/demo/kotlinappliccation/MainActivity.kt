package com.cybersuccess.demo.kotlinappliccation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moviesAdapter = MoviesAdapter()
        movies_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        movies_list.adapter = moviesAdapter

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val apiMovies = retrofit.create(ApiMovies::class.java)
        apiMovies.getMovies()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesAdapter.setMovies(it.data)
                }, {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                })
        startNextActivity()
    }

    private fun startNextActivity() {
         var intent:Intent = Intent(this, DocumentsList::class.java);
        startActivity(intent);

    }

    inner class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

        private val movies: MutableList<Movie> = mutableListOf()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            return MoviesViewHolder(layoutInflater.inflate(R.layout.item_view, parent, false))
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        fun setMovies(data: List<Movie>) {
            movies.addAll(data)
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.bindModel(movies[position])
        }


        inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txtMovietitle: TextView = itemView.findViewById(R.id.movieTitle)
            var txtMovieGener: TextView = itemView.findViewById(R.id.movieGenre)
            var txtMovieYear: TextView = itemView.movieYear
            var moviewImage: ImageView = itemView.movieAvatar

            fun bindModel(movie: Movie) {
                txtMovietitle.text = movie.title
                txtMovieGener.text = movie.genre
                txtMovieYear.text = movie.year
                Picasso.get().load(movie.poster).into(moviewImage)
            }
        }
    }
}
