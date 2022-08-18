package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel


class EditPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )
        arguments?.textArg?.let {
            binding.contentEditText.setText(it)
        }
        binding.ok.setOnClickListener {
            val text = binding.contentEditText.text.toString()
            if (text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE).show()
            } else {
                viewModel.changeContent(text.trim())
                viewModel.save()
                AndroidUtils.hideKeyboard(requireView())
                findNavController().navigateUp()
            }
        }
        binding.cancel.setOnClickListener {
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }
}
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding: ActivityEditPostBinding = ActivityEditPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val post = intent.getParcelableExtra<Post>("post")
//        binding.contentEditText.setText(post?.content)
//
//        binding.contentEditText.requestFocus()
//
//        binding.cancel.setOnClickListener {
//            val intent = Intent()
//            setResult(Activity.RESULT_CANCELED, intent)
//            finish()
//        }
//
//        binding.ok.setOnClickListener {
//            val intent = Intent()
//            if (binding.contentEditText.text.isBlank()) {
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else {
//                val content = binding.contentEditText.text.toString()
//                intent.putExtra(Intent.EXTRA_TEXT, content)
//                setResult(Activity.RESULT_OK, intent)
//            }
//            finish()
//        }
//    }
