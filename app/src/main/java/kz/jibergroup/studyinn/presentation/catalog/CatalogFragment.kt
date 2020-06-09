package kz.jibergroup.studyinn.presentation.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myfirstapp.presentation.global.utils.EventObserver
import kotlinx.android.synthetic.main.catalog_fragment.*
import kotlinx.android.synthetic.main.error_message_view.*
import kotlinx.android.synthetic.main.error_not_found.*
import kz.jibergroup.studyinn.R
import kz.jibergroup.studyinn.domain.entity.ObjectData
import kz.jibergroup.studyinn.presentation.global.base.BaseFragment
import kz.jibergroup.studyinn.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : BaseFragment() {

    companion object {
        fun newInstance() = CatalogFragment()
    }

    override fun layoutId(): Int {
        return R.layout.catalog_fragment
    }

    val viewModel: CatalogViewModel by viewModel()
    lateinit var categoryPagerAdapter: CategoryPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.categories.observe(viewLifecycleOwner, categoryObserver)
        viewModel.categoryFragments.observe(viewLifecycleOwner, fragmentsObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errObserver)
        viewModel.getCourseCategories()
    }

    private fun initViewPager() {
        categoryPagerAdapter =
            CategoryPagerAdapter(childFragmentManager)
        categoryPagerAdapter.submitFragments(viewModel.categoryFragments.value ?: mutableListOf())
        categoryPagerAdapter.submitCategoryTitle(viewModel.categoryTitles.value ?: mutableListOf())
        catalogViewPager.adapter = categoryPagerAdapter

        catalogTablayout.setupWithViewPager(catalogViewPager)

    }

    private val errObserver = EventObserver<String> {
        handleError(HomeViewModel.Error.ServerError, it)
    }

    private fun handleError(error: HomeViewModel.Error, errorLabel: String? = null) {
        when (error) {
            HomeViewModel.Error.Not_found -> {
                catalogContainer.visibility = View.GONE
                errorLayout.visibility = View.GONE
                progressBar.visibility = View.GONE
                error404Layout.visibility = View.VISIBLE
                error404Description.text = getString(R.string.nothing_was_found)

                error404TryAgain.setOnClickListener {
                    progressBar.visibility = View.VISIBLE
                    viewModel.getCourseCategories()
                }
            }
            HomeViewModel.Error.ServerError -> {
                catalogContainer.visibility = View.GONE
                progressBar.visibility = View.GONE
                error404Layout.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
                errorDescription.text = errorLabel

                errorTryAgain.setOnClickListener {
                    progressBar.visibility = View.VISIBLE
                    viewModel.getCourseCategories()
                }
            }
            HomeViewModel.Error.Success -> {
                progressBar.visibility = View.GONE
                errorLayout.visibility = View.GONE
                error404Layout.visibility = View.GONE
                catalogContainer.visibility = View.VISIBLE
            }
        }
    }

    private val categoryObserver = Observer<MutableList<ObjectData>> {
        if(it.isEmpty()){
            handleError(HomeViewModel.Error.Not_found)
        }else{
            handleError(HomeViewModel.Error.Success)
            viewModel.mapCategoryTitle()
            viewModel.mapCategoryFragments()
        }

    }

    private val fragmentsObserver = Observer<MutableList<Fragment>> {
        initViewPager()
    }


}