package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentOwnPostBinding
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.PostViewModel


class OwnPostFragment: Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }
    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentOwnPostBinding.inflate(
            inflater,
            container,
            false
        )
        val postId = arguments?.textArg?.toLong()
        if (postId != null) {
            viewModel.getPost(postId).let { post ->
                binding.apply{
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    btnLikes.isChecked = post.likedByMe
                    btnLikes.text = Utils.numPostfix(post.likes)
                    btnShares.text = Utils.numPostfix(post.shares)
                    playVideoView.isVisible = post.video != null
                  //  avatarImageView.setImageResource(R.drawable.ic_like_24)

                    menu.setOnClickListener {
                        PopupMenu(it.context, it).apply {
                            inflate(R.menu.options_post)
                            setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.remove -> {
                                        viewModel.removeById(post.id)
                                        findNavController().navigateUp()
                                        true
                                    }
//                                    R.id.edit -> {
//                                        viewModel.edit(post)
//                                        findNavController().navigate(R.id.action_ownPostFragment_to_editPostFragment,
//                                            Bundle().apply {
//                                                textArg = post.content
//                                            })
//                                        true
//                                    }

                                    else -> false
                                }
                            }
                        }.show()
                    }
                }
            }
        }

        return binding.root
    }
}