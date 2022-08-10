package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.dto.Post


class EditPostActivity : AppCompatActivity() {
//    companion object {
//        private const val EDIT_POST_REQUEST_CODE = 2
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_post)
//        val binding = ActivityEditPostBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        val binding: ActivityEditPostBinding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post = intent.getParcelableExtra<Post>("post")
        binding.contentEditText.setText(post?.content)

        binding.contentEditText.requestFocus()
//        intent?.let {
//            if (it.extras?.getInt("REQUEST_CODE") == EDIT_POST_REQUEST_CODE) {
//                val text = it.getStringExtra(Intent.EXTRA_TEXT)
//                if (text.isNullOrBlank()) {
//                    Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
//                        .setAction(android.R.string.ok) {
//                            finish()
//                        }
//                        .show()
//                    return@let
//                }
//                binding.contentEditText.setText(text, TextView.BufferType.EDITABLE)
//            }
            binding.cancel.setOnClickListener {
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }

            binding.ok.setOnClickListener {
                val intent = Intent()
                if (binding.contentEditText.text.isBlank()) {
                    setResult(Activity.RESULT_CANCELED, intent)
                } else {
                    val content = binding.contentEditText.text.toString()
                    intent.putExtra(Intent.EXTRA_TEXT, content)
                    setResult(Activity.RESULT_OK, intent)
                }
                finish()
            }
        }
    }
//}