package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.card_post.*
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        val adapter = PostsAdapter(object : OnInteractionListener{
            override fun onEdit(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_editPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    })
                viewModel.edit(post)
        }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)

            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
           }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun onOwnPost(post: Post) {
                findNavController().navigate(R.id.action_feedFragment_to_ownPostFragment,
                    Bundle().apply {
                        textArg = post.id.toString()
                    })
                viewModel.getPost(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_feedFragment_to_editPostFragment) }

        return binding.root
    }
}



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val viewModel: PostViewModel by viewModels()
//        val adapter = PostsAdapter(object : OnInteractionListener {
//            override fun onEdit(post: Post) {
//                viewModel.edit(post)
//            }
//
//            override fun onLike(post: Post) {
//                viewModel.likeById(post.id)
//            }
//
//            override fun onRemove(post: Post) {
//                viewModel.removeById(post.id)
//            }
//
//            override fun onPlay(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                startActivity(intent)
//            }
//
//            override fun onShare(post: Post) {
//                viewModel.shareById(post.id)
//            }
//        })
//
//        binding.list.adapter = adapter
//        viewModel.data.observe(this) { posts ->
//            adapter.submitList(posts)
//        }
//        val fixedPostLauncher = registerForActivityResult(FixPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }
//        viewModel.edited.observe(this, { post ->
//            if (post.id == 0L) {
//                return@observe
//            }
//            fixedPostLauncher.launch(post) // появляется активити для редактирования старого поста
//        })
//
//        val newPostLauncher = registerForActivityResult(NewPostContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }
//
//        binding.fab.setOnClickListener {
//            newPostLauncher.launch() // появляется новая активити для создания поста
//        }
//    }
//}
