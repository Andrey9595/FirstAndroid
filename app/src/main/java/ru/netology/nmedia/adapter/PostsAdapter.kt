package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardAdBinding
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Ad
import ru.netology.nmedia.dto.FeedItem
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.view.load

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onPlay(post: Post) {}
    fun onOwnPost(post: Post)
    fun onImage(post: Post) {}
}

const val BASE_URL = "http://10.0.2.2:9999"

class PostsAdapter(private val onInteractionListener: OnInteractionListener) :
    PagingDataAdapter<FeedItem, RecyclerView.ViewHolder>(PostDiffCallback()) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Ad -> R.layout.card_ad
            is Post -> R.layout.card_post
            null -> error("unknown item type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):

            RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.card_post -> {
                val binding =
                    CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostViewHolder(binding, onInteractionListener)
            }
            R.layout.card_post -> {
                val binding =
                    CardAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdViewHolder(binding)
            }
            else -> error("unknown item type: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Ad -> (holder as? AdViewHolder)?.bind(item)
            is Post -> (holder as? PostViewHolder)?.bind(item)
            null -> error("unknown item type")
        }
    }
}

class AdViewHolder(
    private val binding: CardAdBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ad: Ad) {
        binding.image.load("$BASE_URL/media/${ad.image}")
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        getAvatars(post, binding)
        if (post.attachment != null) {
            binding.attachImage.visibility = View.VISIBLE
            getAttachment(post, binding)
        } else binding.attachImage.visibility = View.GONE
        if (post.attachment != null) {
            binding.btnLikes.visibility = View.INVISIBLE
            binding.btnShares.visibility = View.INVISIBLE
        } else {
            binding.btnLikes.visibility = View.VISIBLE
            binding.btnShares.visibility = View.VISIBLE
        }
        if (post.attachment != null) {
            binding.savedOnServer.setImageResource(R.drawable.ic_baseline_public_24)
        } else binding.savedOnServer.setImageResource(R.drawable.ic_baseline_public_off_24)

        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            menu.visibility = if (post.ownedByMe) View.VISIBLE else View.INVISIBLE
            btnLikes.isChecked = post.likedByMe
            btnLikes.text = Utils.numPostfix(post.likes)
            btnShares.text = Utils.numPostfix(post.shares)
            playVideoView.isVisible = post.video != null
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            attachImage.setOnClickListener {
                onInteractionListener.onImage(post)
            }

            playVideoView.setOnClickListener {
                onInteractionListener.onPlay(post)
            }
            btnLikes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            btnShares.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            container.setOnClickListener {
                onInteractionListener.onOwnPost(post)
            }
        }
    }

    fun getAvatars(post: Post, binding: CardPostBinding) {
        Glide.with(binding.avatar)
            .load("$BASE_URL/avatars/${post.authorAvatar}")
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_error)
            .circleCrop()
            .timeout(10_000)
            .into(binding.avatar)
    }

    fun getAttachment(post: Post, binding: CardPostBinding) {
        Glide.with(binding.attachImage)
            .load("$BASE_URL/media/${post.attachment?.url}")
            .error(R.drawable.ic_loading)
            .timeout(10_000)
            .into(binding.attachImage)
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}



