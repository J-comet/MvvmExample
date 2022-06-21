package hs.project.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import hs.project.mvvmexample.network.ApiClient
import hs.project.mvvmexample.network.Character
import hs.project.mvvmexample.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    private fun processCharactersResponse(state: ScreenState<List<Character>?>){

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        when(state){
            is ScreenState.Loading -> {
                // 로딩바 보여짐
                progressBar.isVisible = true
            }
            is ScreenState.Success -> {
                progressBar.isVisible = false
                if (state.data != null) {
                    val adapter = MainAdapter(state.data)
                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                    recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter = adapter
                }
            }
            is ScreenState.Error -> {
                progressBar.isVisible = false
                Snackbar.make(progressBar.rootView, state.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}