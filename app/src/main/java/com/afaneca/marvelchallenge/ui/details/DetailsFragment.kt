package com.afaneca.marvelchallenge.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.MainActivity
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.databinding.FragmentDetailsBinding
import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel
import com.afaneca.marvelchallenge.ui.normalizeUrlToHttps
import com.afaneca.marvelchallenge.ui.utils.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeMainState()
        observeComicsState()
        observeEventsState()
        observeStoriesState()
        observeSeriesState()

        viewModel.init(args.id, args.name, args.description, args.imgUrl)
        binding.ivClose.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openShareIntent(name: String, description: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.details_share_message, name, description)
            )
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

    private fun observeMainState() {
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                val name =
                    if (state.name.isNullOrBlank()) getString(R.string.character_name_unknown) else state.name
                val description =
                    if (state.description.isNullOrBlank()) getString(R.string.character_description_unknown) else state.description

                // NAME
                binding.tvName.text = name

                // DESCRIPTION
                binding.includeMainContainer.tvDescription.text = description


                // IMAGE
                if (!state.imgUrl.isNullOrBlank()) {
                    ImageLoader.loadImageIntoView(
                        requireContext(),
                        state.imgUrl.normalizeUrlToHttps(),
                        binding.ivCharacter
                    )

                    // SHARE
                    binding.ivShare.setOnClickListener {
                        openShareIntent(name, description)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun observeStoriesState() {
        //TODO
    }

    private fun observeSeriesState() {
        //TODO
    }

    private fun observeEventsState() {
        //TODO
    }

    private fun handleError(error: String) {
        (requireActivity() as? MainActivity)?.showTopError(error)
    }

    private fun hideErrorContainer() {
        (requireActivity() as? MainActivity)?.hideTopError()
    }

    private fun observeComicsState() {
        viewModel.comicsState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                // If list is still loading or an error occurred, we simply don't show this section
                binding.includeMainContainer.tvComicsLabel.isVisible = !state.list.isNullOrEmpty()
                binding.includeMainContainer.rvComics.isVisible = !state.list.isNullOrEmpty()

                // Recycler View
                state.list?.let { setupComicsRecyclerView(it) }

                // Error Handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error)
                }
            }.launchIn(lifecycleScope)
    }

    private fun setupComicsRecyclerView(dataset: List<CharacterContentUiModel>) {
        if (binding.includeMainContainer.rvComics.adapter == null) {
            // setup
            binding.includeMainContainer.rvComics.apply {
                adapter = CharacterContentAdapter(ListViewType.Comic).also {
                    // Restore recycler state only when adapter is not empty
                    it.stateRestorationPolicy =
                        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            }
        }

        // submit data
        (binding.includeMainContainer.rvComics.adapter as? CharacterContentAdapter)?.submitList(
            dataset
        )
    }
}