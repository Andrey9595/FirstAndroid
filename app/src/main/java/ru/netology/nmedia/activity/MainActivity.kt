package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                btnLikes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
                textLikes.text = numPostfix(post.likes)
                textShares.text = numPostfix(post.shares)
            }
        }
        binding.btnLikes.setOnClickListener {
            viewModel.like()
        }
        binding.btnShares.setOnClickListener {
            viewModel.share()
        }

    }

    fun numPostfix(num: Int): String = when (num) {
        in 0..999 -> num.toString()
        in 1_000..9_999 -> if (num.toString()[1] == '0') "${num.toString()[0]}K" else "${num.toString()[0]},${num.toString()[1]}K"
        in 10_000..999_999 -> "${num.toString().dropLast(3)}K"
        else -> if (num.toString()[1] == '0') "${num.toString()[0]}M" else "${num.toString()[0]},${num.toString()[1]}M"
    }
}
