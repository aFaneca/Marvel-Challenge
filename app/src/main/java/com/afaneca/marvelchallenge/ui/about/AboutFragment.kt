package com.afaneca.marvelchallenge.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.afaneca.marvelchallenge.databinding.FragmentAboutBinding
import com.afaneca.marvelchallenge.ui.model.TopSellingItemUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AboutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeState() {
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                state.items?.let { setupTopSellingRecyclerView(it) }
            }.launchIn(lifecycleScope)
    }

    private fun setupTopSellingRecyclerView(dataset: List<TopSellingItemUiModel>) {
        if (binding.rvTopSelling.adapter == null) {
            // setup
            binding.rvTopSelling.apply {
                setHasFixedSize(true)
                adapter = TopSellingAdapter {
                    openUrlInBrowser(it.ctaUrl)
                }
            }
        }

        // submit data
        (binding.rvTopSelling.adapter as? TopSellingAdapter)?.submitList(dataset)
    }

    private fun openUrlInBrowser(url: String) {
        val openIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(openIntent)
    }
}