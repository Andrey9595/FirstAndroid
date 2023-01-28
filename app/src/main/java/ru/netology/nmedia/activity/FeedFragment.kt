package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFeedBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
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
//                viewModel.shareById(post.id)
            }

            override fun onPlay(post: Post) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }

            override fun onOwnPost(post: Post) {

                findNavController().navigate(R.id.action_feedFragment_to_postFragment,
                    Bundle().apply {
                        textArg = post.id.toString()
                    })
                viewModel.loadPosts()
            }
        })


        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { state ->
            val newPost = state.posts.size > adapter.currentList.size
            adapter.submitList(state.posts) {
                if (newPost) binding.list.smoothScrollToPosition(0)
            }
        }
        viewModel.dataState.observe(viewLifecycleOwner){ state ->
            binding.progress.isVisible = state.loading
            if (state.error){
                Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG).setAction(R.string.retry_loading){
                    viewModel.refresh()
                }
                    .show()
            }
            binding.errorGroup.isVisible = state.error
        }
        viewModel.newerCount.observe(viewLifecycleOwner) { state ->

            if (state != 0){
                val btnText = "Новая запись ($state)"
                binding.newerPostsBtn.text = btnText
                binding.newerPostsBtn.visibility = View.VISIBLE
            }

            println("state: $state")
        }

        binding.newerPostsBtn.setOnClickListener {
//            viewModel.loadPosts()
            viewModel.updateShownStatus()
            it.visibility = View.GONE
        }

        binding.retryButton.setOnClickListener {
        }

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_feedFragment_to_newPostFragment) }

    }
}

