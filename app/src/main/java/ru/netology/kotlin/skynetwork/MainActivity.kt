package ru.netology.kotlin.skynetwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.kotlin.skynetwork.adapter.PostAdapter
import ru.netology.kotlin.skynetwork.data.SimplePost
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var postsAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createdTime = Calendar.getInstance()
        createdTime.set(2020, Calendar.JULY, 20, 9, 35, 0)

        val postsList = listOf(
            SimplePost(
                1,
                "kate bazleva",
                "Something very interesting",
                createdTime.time,
                likesCount = 1,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true
            ),
            SimplePost(
                2,
                "bzzzz",
                "Bzz-zzzz",
                createdTime.time,
                likesCount = 10
            ),
            SimplePost(
                3,
                "mikki",
                "Third post!",
                createdTime.time,
                commentsCount = 3,
                shareCount = 2,
                commentedByMe = true
            ),
            SimplePost(
                4,
                "mouse",
                "QWERTYUIOP[ASDFGHJKL;zxcvbnm,dhfkjehrfvcljbdnvcli ubaeowhlvbkjfzds;oifeh;oigbvavubeb;vb zjk",
                createdTime.time,
                likesCount = 5,
                shareCount = 2,
                likedByMe = true
            ),
            SimplePost(
                5,
                "mur",
                "meow :3",
                createdTime.time,
                likesCount = 100,
                commentsCount = 3,
                shareCount = 2,
                likedByMe = true,
                sharedByMe = true
            )
        )

        initRecyclerView()
        addData(postsList)

    }

    private fun addData(postsList: List<SimplePost>) {
        postsAdapter.submitPosts(postsList)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(ItemDecoration(45))
            postsAdapter = PostAdapter()
            adapter = postsAdapter
        }
    }
}
