package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.nmedia.databinding.FragmentAuthBinding
import ru.netology.nmedia.viewmodel.RegistrationViewModel

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAuthBinding.inflate(
            inflater,
            container,
            false
        )

        binding.enterBtnView.setOnClickListener {
            if (binding.loginView.text.isNotEmpty() &&
                binding.passwordView.text.isNotEmpty()
            ) {
                val login = binding.loginView.text.trim().toString()
                val password = binding.passwordView.text.trim().toString()
                viewModel.registerUser(login, password)

            } else Snackbar.make(binding.root, "Заполните все поля", Snackbar.LENGTH_LONG).show()
        }
        viewModel.tokenReceived.observe(viewLifecycleOwner) {
            if (it == 0) {
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.root, "Неверный пароль или логин", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        return binding.root
    }
}