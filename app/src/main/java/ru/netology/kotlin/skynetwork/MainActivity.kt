package ru.netology.kotlin.skynetwork

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.netology.kotlin.skynetwork.adapter.PostAdapter
import ru.netology.kotlin.skynetwork.data.Post

class MainActivity : AppCompatActivity() {

    private val POSTS_URL =
        "https://raw.githubusercontent.com/katebazleva/netology_backend/master/posts.json"
    private val ADS_POSTS_URL =
        "https://raw.githubusercontent.com/katebazleva/netology_backend/master/adsPosts.json"

    private lateinit var postsAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(IO).launch {
            delay(3000)
            val postsList = getPostsFromInternet(POSTS_URL)
            val adsPostsList  = getPostsFromInternet(ADS_POSTS_URL)

            withContext(Main) {
                initRecyclerView()
                addData(postsList, adsPostsList)
            }
        }
    }

    private fun addData(postsList: List<Post>, adsPostsList: List<Post>) {
        postsAdapter.setData(postsList, adsPostsList)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ItemDecoration(45))
            postsAdapter = PostAdapter()
            adapter = postsAdapter
        }
        recycler_view.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    private suspend fun getPostsFromInternet(url: String): List<Post> {
        val client = HttpClient {
            install(JsonFeature) {
                acceptContentTypes = listOf(
                    ContentType.Text.Plain,
                    ContentType.Application.Json
                )
                serializer = GsonSerializer()
            }
        }
        val response = client.get<List<Post>>(url)
        println(response)

        client.close()
        return response
    }
}
