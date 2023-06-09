package com.afaneca.marvelchallenge.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afaneca.marvelchallenge.MainActivity
import com.afaneca.marvelchallenge.R
import com.afaneca.marvelchallenge.common.Constants
import com.afaneca.marvelchallenge.databinding.FragmentCharactersBinding
import com.afaneca.marvelchallenge.ui.hideSoftKeyboard
import com.afaneca.marvelchallenge.ui.model.CharacterUiModel
import com.afaneca.marvelchallenge.ui.safeNavigate
import com.afaneca.marvelchallenge.ui.utils.GlideApp
import com.afaneca.marvelchallenge.ui.utils.ImageLoader
import com.afaneca.marvelchallenge.ui.utils.InfiniteScrollListener
import com.afaneca.marvelchallenge.ui.utils.setVisibilityWithAnimation
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharactersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchView()
        observeState()
    }

    private fun setupSearchView() {
        binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchInputSubmitted(query)
                binding.svSearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.tvResetSearch.setOnClickListener {
            binding.svSearch.setQuery("", true)
            binding.svSearch.clearFocus()
            viewModel.onSearchInputSubmitted(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideKeyboard() {
        requireActivity().hideSoftKeyboard()
    }

    private fun observeState() {
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                hideKeyboard()
                // Loading
                binding.pbLoading.root.isVisible = state.isLoading
                binding.pbPaginationLoading.isVisible = state.isLoadingFromPagination

                // Empty View
                binding.emptyView.root.isVisible = !state.isLoading && state.error.isNullOrBlank()
                        && state.characterList.isNullOrEmpty()

                // Recycler View
                binding.rvList.isVisible =
                    !state.characterList.isNullOrEmpty() && !state.isLoading

                state.characterList?.let { setupRecyclerView(it) }

                // Reset search call-to-action
                with(binding.tvResetSearch) {
                    // reset search cta should only be visible if search is active
                    val desiredResetSearchVisibility =
                        if (state.searchQuery.isNullOrEmpty()) View.GONE else View.VISIBLE

                    // if desired visibility attr is different from current, change view state (with transition animation)
                    if (desiredResetSearchVisibility != visibility)
                        setVisibilityWithAnimation(
                            parentView = binding.root as ViewGroup,
                            shouldBecomeVisible = desiredResetSearchVisibility == View.VISIBLE
                        )
                }

                // Error handling
                if (!state.error.isNullOrBlank()) {
                    handleError(state.error, !state.characterList.isNullOrEmpty())
                } else {
                    hideErrorContainers()
                }
            }.launchIn(lifecycleScope)
    }

    /**
     * If [isShowingList] = true, will show a top bar error, otherwise it'll show a full container one
     */
    private fun handleError(error: String, isShowingList: Boolean) {
        if (isShowingList) {
            (requireActivity() as? MainActivity)?.showTopError(error)
        } else {
            binding.errorView.tvError.text = error
            binding.errorView.root.isVisible = true
        }
    }

    private fun hideErrorContainers() {
        (requireActivity() as? MainActivity)?.hideTopError()
        binding.errorView.root.isVisible = false
    }

    private fun setupRecyclerView(list: List<CharacterUiModel>) {
        if (binding.rvList.adapter == null) {
            // setup recycler view
            binding.rvList.apply {
                adapter = CharacterListAdapter(
                    requestManager = ImageLoader.getRequestBuilder(context),
                ) { character ->
                    goToDetails(character)
                }.also {
                    // Restore recycler state only when adapter is not empty
                    it.stateRestorationPolicy =
                        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                // handle infinite scrolling
                addOnScrollListener(InfiniteScrollListener {
                    // on load more
                    viewModel.requestNextPage()
                })

                // Setup RecyclerViewPreloader to preload images ahead of time
                val cardWidth = binding.root.width;
                val cardHeight =
                    resources.getDimensionPixelSize(R.dimen.default_character_list_card_height);
                val viewPreloadSizeProvider =
                    FixedPreloadSizeProvider<String>(cardWidth, cardHeight)

                val recyclerViewPreLoader = RecyclerViewPreloader(
                    GlideApp.with(requireActivity()),
                    adapter as CharacterListAdapter,
                    viewPreloadSizeProvider,
                    Constants.DEFAULT_PAGE_SIZE
                )

                addOnScrollListener(recyclerViewPreLoader)
            }
        }
        // update list
        (binding.rvList.adapter as? CharacterListAdapter)?.submitList(list)
    }

    private fun goToDetails(character: CharacterUiModel) {
        val action = CharactersFragmentDirections.actionNavigationCharactersToNavigationDetails(
            id = character.id,
            name = character.name,
            description = character.description ?: "",
            imgUrl = character.imgUrl ?: ""
        )
        findNavController().safeNavigate(action)
    }
}