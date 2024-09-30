package com.itis.template.presentation.mvvm

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.itis.template.R
import com.itis.template.databinding.ActivityWeatherBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainMvvmFlowFragment : Fragment(R.layout.activity_main) {

    private var binding: ActivityWeatherBinding? = null

    private val viewModel: MainViewModelMvi by viewModels {
        MainViewModelMvi.Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        observeCommand()

        binding?.btnLoad?.setOnClickListener {

            viewModel.reducer(MainViewModelMvi.ScreenEvent.OnLoadButtonClick("kazan"))
        }
    }

    private fun observeState() {
        viewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state->
                when (state) {
                    is MainViewModelMvi.ScreenState.Error -> TODO()
                    MainViewModelMvi.ScreenState.Initial -> TODO()
                    is MainViewModelMvi.ScreenState.Loading ->
                        binding?.progress?.isVisible = state.isLoading
                    is MainViewModelMvi.ScreenState.SuccessData -> TODO()
                    is MainViewModelMvi.ScreenState.UserList -> TODO()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun observeCommand() {
//        viewModel.command.flowWithLifecycle(viewLifecycleOwner.lifecycle)
//            .onEach { command->
//                when (command) {
//                    is MainViewModelMvi.ScreenCommand.ShowToast -> {
//                        Snackbar.make(binding?.root!!, command.text, Snackbar.LENGTH_LONG).show()
//                    }
//                }
//            }
//            .launchIn(lifecycleScope)
    }
}