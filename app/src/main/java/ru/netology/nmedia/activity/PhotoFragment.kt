package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.netology.nmedia.R
import  ru.netology.nmedia.adapter.BASE_URL
import ru.netology.nmedia.databinding.FragmentPhotoBinding

class PhotoFragment: Fragment() {
    private val args by navArgs<PhotoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPhotoBinding.inflate(
            inflater,
            container,
            false
        )
        val url = args.imageArg.toUri()

        Glide.with(binding.imageFull)
            .load("$BASE_URL/media/$url")
            .error(R.drawable.ic_loading)
            .timeout(10_000)
            .into(binding.imageFull)

        return binding.root
    }

}