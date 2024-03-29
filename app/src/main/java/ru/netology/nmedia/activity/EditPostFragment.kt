package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.utils.AndroidUtils
import ru.netology.nmedia.utils.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

@AndroidEntryPoint
class EditPostFragment : Fragment() {
    companion object {
        var Bundle.textArg: String? by StringArg
    }

    @ExperimentalCoroutinesApi
    private val viewModel: PostViewModel by activityViewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(
            inflater,
            container,
            false
        )
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_edit_post, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
                when (menuItem.itemId) {
                    R.id.save -> {
                        viewModel.changeContent(binding.content.text.toString())
                        viewModel.save()
                        AndroidUtils.hideKeyboard(requireView())
                        true
                    }
                    else -> false
                }
        }, viewLifecycleOwner)

        val photoLauncher = registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) {
            when (it.resultCode) {
                ImagePicker.RESULT_ERROR -> {
                    Snackbar.make(
                        binding.root,
                        ImagePicker.getError(it.data),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                else -> {
                    val uri = it.data?.data ?: return@registerForActivityResult
                    viewModel.changePhoto(uri, uri.toFile())
                }
            }
        }

        binding.takeShotBtn.setOnClickListener {
            ImagePicker.Builder(this)
                .crop()
                .cameraOnly()
                .maxResultSize(2048, 2048)
                .createIntent {
                    photoLauncher.launch(it)
                }
        }

        binding.pickPicBtn.setOnClickListener {
            ImagePicker.Builder(this)
                .crop()
                .galleryOnly()
                .maxResultSize(2048, 2048)
                .createIntent {
                    photoLauncher.launch(it)
                }
        }

        viewModel.photo.observe(viewLifecycleOwner) {
            if (it?.uri == null) {
                binding.picLayout.visibility = View.GONE
                return@observe
            }
            binding.picLayout.visibility = View.VISIBLE
            binding.pic.setImageURI(it.uri)
        }

        binding.deletePic.setOnClickListener {
            viewModel.changePhoto(null, null)
        }
        arguments?.textArg
            ?.let(binding.content::setText)
        viewModel.postCreated.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        return binding.root
    }
}

