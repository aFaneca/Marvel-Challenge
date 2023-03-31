package com.afaneca.marvelchallenge.ui.details

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.MainActivity
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.databinding.FragmentDetailsBinding
import com.afaneca.marvelchallenge.ui.model.CharacterContentUiModel
import com.afaneca.marvelchallenge.ui.normalizeUrlToHttps
import com.afaneca.marvelchallenge.ui.utils.ImageLoader
import com.afaneca.marvelchallenge.ui.utils.VerticalMarginItemDecoration
import com.afaneca.marvelchallenge.ui.utils.setVisibilityWithAnimation
import com.google.android.material.divider.MaterialDividerItemDecoration
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
        binding.includeMainContainer.tvEventsSeeAllCta.setOnClickListener {
            openUrlInBrowser(
                Constants.EVENTS_SEE_ALL_CTA_URL
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openUrlInBrowser(url: String) {
        val openIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(openIntent)
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
        viewModel.storiesState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                // If list is still loading or an error occurred, we simply don't show this section
                binding.includeMainContainer.tvStoriesLabel.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )
                binding.includeMainContainer.rvStories.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )

                // Recycler View
                state.list?.let {
                    setupContentRecyclerView(
                        ListViewType.StorySeries,
                        binding.includeMainContainer.rvStories,
                        it, state.list.size > 1
                    )
                }

                // Error Handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error)
                } else hideErrorContainer()
            }.launchIn(lifecycleScope)
    }

    private fun observeSeriesState() {
        viewModel.seriesState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                // If list is still loading or an error occurred, we simply don't show this section
                binding.includeMainContainer.tvSeriesLabel.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )
                binding.includeMainContainer.rvSeries.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )

                // Recycler View
                state.list?.let {
                    setupContentRecyclerView(
                        ListViewType.StorySeries,
                        binding.includeMainContainer.rvSeries,
                        it, state.list.size > 1
                    )
                }

                // Error Handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error)
                } else hideErrorContainer()
            }.launchIn(lifecycleScope)
    }

    private fun observeEventsState() {
        viewModel.eventsState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                // If list is still loading or an error occurred, we simply don't show this section
                binding.includeMainContainer.tvEventsLabel.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )
                binding.includeMainContainer.rvEvents.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )
                binding.includeMainContainer.tvEventsSeeAllCta.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )

                // Recycler View
                state.list?.let {
                    setupContentRecyclerView(
                        ListViewType.Event,
                        binding.includeMainContainer.rvEvents,
                        it, state.list.size > 1
                    )
                }

                // Error Handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error)
                } else hideErrorContainer()
            }.launchIn(lifecycleScope)
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
                binding.includeMainContainer.tvComicsLabel.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )
                binding.includeMainContainer.rvComics.setVisibilityWithAnimation(
                    binding.root as ViewGroup, !state.list.isNullOrEmpty()
                )

                // Recycler View
                state.list?.let {
                    setupContentRecyclerView(
                        ListViewType.Comic,
                        binding.includeMainContainer.rvComics,
                        it, false
                    )
                }

                // Error Handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error)
                }
            }.launchIn(lifecycleScope)
    }

    private fun setupContentRecyclerView(
        viewType: ListViewType,
        recyclerView: RecyclerView,
        dataset: List<CharacterContentUiModel>,
        addDivider: Boolean = false
    ) {
        if (recyclerView.adapter == null) {
            // setup
            recyclerView.apply {
                setHasFixedSize(true)
                if (addDivider) {
                    addItemDecoration(MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
                        isLastItemDecorated = false
                    })
                    addItemDecoration(VerticalMarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_default)))
                }
                adapter = CharacterContentAdapter(viewType).also {
                    // Restore recycler state only when adapter is not empty
                    it.stateRestorationPolicy =
                        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
            }
        }

        // submit data
        (recyclerView.adapter as? CharacterContentAdapter)?.submitList(
            dataset
        )
    }
}