package ru.netology.kotlin.skynetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.ContentType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import ru.netology.kotlin.skynetwork.adapter.PostAdapter
import ru.netology.kotlin.skynetwork.data.Post

class MainActivity : AppCompatActivity() {

    companion object {
        private const val POSTS_URL =
            "https://raw.githubusercontent.com/katebazleva/netology_backend/master/posts.json"
        private const val ADS_POSTS_URL =
            "https://raw.githubusercontent.com/katebazleva/netology_backend/master/adsPosts.json"
    }

    private val postsAdapter: PostAdapter = PostAdapter()
    private val client by lazy(LazyThreadSafetyMode.NONE) { getHttpClient() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            val postsList = client.getPostsFromInternet(POSTS_URL)
            val adsPostsList = client.getPostsFromInternet(ADS_POSTS_URL)

            initRecyclerView()
            addData(postsList, adsPostsList)
        }
    }

    private fun addData(postsList: List<Post>, adsPostsList: List<Post>) {
        postsAdapter.setData(postsList, adsPostsList)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ItemDecoration(45))
            adapter = postsAdapter
        }
        recycler_view.isVisible = true
        progress_bar.isVisible = false
    }

    private suspend fun HttpClient.getPostsFromInternet(url: String): List<Post> {
        val response = this.get<List<Post>>(url)
        println(response)

        return response
    }

    private fun getHttpClient() = HttpClient {
        install(JsonFeature) {
            accept(
                ContentType.Text.Plain,
                ContentType.Application.Json
            )
            serializer = GsonSerializer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}
