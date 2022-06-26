package hs.project.mvvmexample

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import hs.project.mvvmexample.network.Character

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var textView2: TextView

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.connected)
        textView2 = findViewById(R.id.not_connected)

        NetWorkCheckUtil.observe(this, { isConnected ->
            if (isConnected) {
                textView.visibility = View.VISIBLE
                textView2.visibility = View.GONE
            } else {
                textView.visibility = View.GONE
                textView2.visibility = View.VISIBLE
            }
        })

        viewModel.charactersLiveData.observe(this) { state ->
            processCharactersResponse(state)
        }


//        val client = ApiClient.apiService.fetchCharacters(page = "1")
//        client.enqueue(object : Callback<CharacterResponse?> {
//            override fun onResponse(
//                call: Call<CharacterResponse?>,
//                response: Response<CharacterResponse?>
//            ) {
//                if (response.isSuccessful) {
//                    Log.d("character", "" + response.body())
//                    val result = response.body()?.result
//                    result?.let {
//                        val adapter = MainAdapter(result)
//                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//                        recyclerView?.layoutManager =
//                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                        recyclerView?.adapter = adapter
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<CharacterResponse?>, t: Throwable) {
//                Log.e("character", "" + t)
//            }
//        })

    }

    private fun processCharactersResponse(state: ScreenState<List<Character>?>) {

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        when (state) {
            is ScreenState.Loading -> {
                // 로딩바 보여짐
                progressBar.isVisible = true
            }
            is ScreenState.Success -> {
                progressBar.isVisible = false
                if (state.data != null) {
                    val adapter = MainAdapter(state.data)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerView?.layoutManager =
                        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter = adapter
                }
            }
            is ScreenState.Error -> {
                progressBar.isVisible = false
                Snackbar.make(progressBar.rootView, state.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}