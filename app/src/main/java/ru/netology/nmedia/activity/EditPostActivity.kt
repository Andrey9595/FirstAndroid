package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding


class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityEditPostBinding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val post = intent.getParcelableExtra<Post>("post")

    //    binding.contentEditText.setText(post?.content)

        binding.contentEditText.requestFocus()
        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.contentEditText.text.isBlank()){
                setResult(Activity.RESULT_CANCELED, intent)
            }else{
                val content = binding.contentEditText.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}