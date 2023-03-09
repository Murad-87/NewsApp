package com.example.testapp1.utils.baseClasses

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.testapp1.R
import com.example.testapp1.utils.handleBackButtonPressed
import com.google.android.material.appbar.AppBarLayout


abstract class BaseFragment<VB : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _viewBinding: VB? = null
    val viewBinding get() = _viewBinding!!

    protected val navController: NavController by lazy { findNavController() }
    protected val resourceProvider: Resources by lazy { requireContext().resources }
    protected val theme: Resources.Theme by lazy { requireActivity().theme }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = inflate(inflater, container, false)
        return viewBinding.root
    }

    protected open fun initToolbar(
        toolbar: Toolbar,
        appBar: AppBarLayout,
        doNotShowShadow: Boolean = false,
        @DrawableRes navigateIconResId: Int = R.drawable.ic_arrow_back,
        title: String,
        actionBack: () -> Boolean = navController::popBackStack
    ) {
        toolbar.navigationIcon = ResourcesCompat.getDrawable(
            resourceProvider,
            navigateIconResId,
            theme
        )
        toolbar.title = title
        if (doNotShowShadow) {
            appBar.outlineProvider = null
        }
        toolbar.setNavigationOnClickListener { actionBack() }
        handleBackButtonPressed(actionBack)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}