package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dto.Post
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
//            if (post.likedByMe) {
//                btnLikes?.setImageResource(R.drawable.ic_liked_24)
//            }
//            textLikes?.text = post.likes.toString()
//            root.setOnClickListener {
//             //   Log.d("stuff", "stuff")
//            }
            avatar.setOnClickListener {
                //      Log.d("stuff", "avatar")
            }
            btnShares.setOnClickListener {
                textShares.text = numPostfix(++post.shares)
            }

            btnLikes.setOnClickListener {
                //  Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                btnLikes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                textLikes.text = post.likes.toString()
            }
        }
    }

    fun numPostfix(num: Int): String = when (num) {
        in 0..999 -> num.toString()
        in 1_000..9_999 -> if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
        in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
        else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
    }
}
