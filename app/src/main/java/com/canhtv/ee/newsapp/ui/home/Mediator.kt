package com.canhtv.ee.newsapp.ui.home

import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ee.newsapp.R
import com.canhtv.ee.newsapp.adapters.SourceRecyclerViewAdapter
import com.canhtv.ee.newsapp.viewmodels.ExploreTopicViewModel
import com.canhtv.ee.newsapp.viewmodels.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Mediator : Fragment() {

    @Inject
    lateinit var exploreTopicViewModel: ExploreTopicViewModel

    @Inject
    lateinit var webViewModel: WebViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_mediator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = resources.getString(R.string.mediator)

        val sourceIcons: TypedArray = resources.obtainTypedArray(R.array.following_source_icons)
        val followingRecyclerView = view.findViewById<RecyclerView>(R.id.followingRecyclerView)
        val itemDivider = DividerItemDecoration(followingRecyclerView.context, LinearLayoutManager.VERTICAL)
        itemDivider.setDrawable(AppCompatResources.getDrawable(followingRecyclerView.context,R.drawable.item_divider)!!)
        followingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = SourceRecyclerViewAdapter(sourceIcons) { sourceId, sourceName ->
                onSourceItemClick(
                    sourceId,
                    sourceName
                )
            }
            hasFixedSize()
            addItemDecoration(itemDivider)
        }
    }

    private fun onSourceItemClick(sourceId: String, sourceName: String){
        exploreTopicViewModel.tag = sourceName
        exploreTopicViewModel.fetchSourceHeadline(sourceId)
        val mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
        mainNavController.navigate(R.id.action_global_to_exploreTopicFragment)
    }
}