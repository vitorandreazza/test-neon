package com.example.neontest.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.neontest.ui.BaseViewModel

fun Fragment.setupToolbar(toolbar: Toolbar) {
    (activity as? AppCompatActivity)?.run {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }
}

fun Fragment.observeToast(viewModel: BaseViewModel) {
    viewModel.toastText.observe(viewLifecycleOwner, Observer {
        Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
    })
}

inline fun <reified T : ViewGroup.LayoutParams> View.updateLayoutParams(action: T.() -> Unit) {
    layoutParams.apply {
        action.invoke(this as T)
    }.also {
        layoutParams = it
    }
}