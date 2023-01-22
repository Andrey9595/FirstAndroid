package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.card_post.*
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.databinding.FragmentPost2Binding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.PostViewModel


class PostFragment : Fragment() {

//    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    //    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val binding = FragmentPost2Binding.inflate(
//            inflater,
//            container,
//            false
//        )
//        val onInteractionListener = object : OnInteractionListener {
//            override fun onEdit(post: Post) {
//                findNavController().navigate(
//                    R.id.action_postFragment_to_newPostFragment,
//                    Bundle().apply {
//                        textArg = post.content
//                    })
//                viewModel.edit(post)
//            }
//
//            override fun onRemove(post: Post) {
//                viewModel.removeById(post.id)
//
//            }
//
//            override fun onLike(post: Post) {
//                viewModel.likeById(post.id)
//
//            }
//
//            override fun onShare(post: Post) {
//                viewModel.shareById(post.id)
//            }
//
//            override fun onPlay(post: Post) {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
//                startActivity(intent)
//            }
//
//            override fun onOwnPost(post: Post) {
//                findNavController().navigate(R.id.action_feedFragment_to_postFragment,
//                    Bundle().apply {
//                        textArg = post.id.toString()
//                    })
//                viewModel.getPost(post.id)
//            }
//        }
//
//        val post = viewModel.getPost(arguments?.textArg!!.toLong())
//
//        binding.fragmentPost.apply {
//            author.text = post.author
//            published.text = post.published
//            content.text = post.content
//            btnLikes.isChecked = post.likedByMe
//            btnLikes.text = Utils.numPostfix(post.likes)
//            btnShares.text = Utils.numPostfix(post.shares)
//            playVideoView.isVisible = post.video != null
//            playVideoView.setOnClickListener {
//                onInteractionListener.onPlay(post)
//            }
//            btnLikes.setOnClickListener {
//                onInteractionListener.onLike(post)
//            }
//            btnShares.setOnClickListener {
//                onInteractionListener.onShare(post)
//            }
//
//            menu.setOnClickListener {
//                PopupMenu(it.context, it).apply {
//                    inflate(R.menu.options_post)
//                    setOnMenuItemClickListener { item ->
//                        when (item.itemId) {
//                            R.id.remove -> {
////                                onInteractionListener.onRemove(post)
//                                viewModel.removeById(post.id)
//                                findNavController().navigateUp()
//                                true
//                            }
//                            R.id.edit -> {
////                                onInteractionListener.onEdit(post)
//                                viewModel.edit(post)
//                                findNavController().navigate(R.id.action_postFragment_to_newPostFragment,
//                                    Bundle().apply {
//                                        textArg = post.content
//                                    })
//                                true
//                            }
//
//                            else -> false
//                        }
//                    }
//                }.show()
//            }
//
//            viewModel.data.observe(viewLifecycleOwner) { state ->
//                onInteractionListener.submitList(state.posts)
//                binding.progress.isVisible = state.loading
//                binding.errorGroup.isVisible = state.error
//                binding.emptyText.isVisible = state.empty
//            }
//        }
//
//        return binding.root
//    }
//    companion object {
//        var Bundle.textArg: String? by StringArg
//    }
//
//    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        val binding = FragmentPost2Binding.inflate(
//            inflater,
//            container,
//            false
//        )
//        val postId = arguments?.textArg?.toLong()
//        if (postId != null) {
//            viewModel.getPost(postId).let { post ->
//                binding.apply {
//                    author.text = post.author
//                    published.text = post.published
//                    content.text = post.content
//                    btn_likes.isChecked = post.likedByMe
//                    btn_likes.text = Utils.numPostfix(post.likes)
//                    btn_shares.text = Utils.numPostfix(post.shares)
//                    playVideoView.isVisible = post.video != null
//
//                    menu.setOnClickListener {
//                        PopupMenu(it.context, it).apply {
//                            inflate(R.menu.options_post)
//                            setOnMenuItemClickListener { item ->
//                                when (item.itemId) {
//                                    R.id.remove -> {
//                                        viewModel.removeById(post.id)
//                                        findNavController().navigateUp()
//                                        true
//                                    }
//                                    R.id.edit -> {
//                                        viewModel.edit(post)
//                                        findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
//                                            Bundle().apply {
//                                                textArg = post.content
//                                            })
//                                        true
//                                    }
//                                    else -> false
//                                }
//                            }
//                        }.show()
//                    }
//                }
//            }
//        }
//        return binding.root
//    }
}