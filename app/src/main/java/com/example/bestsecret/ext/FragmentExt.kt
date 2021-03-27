package com.example.bestsecret.ext

import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.bestsecret.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.showError(throwable: Throwable?) {
    val error = throwable?.message ?: return
    Snackbar.make(view!!, error, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showInProgress() {
    try {
        loadingView()?.isVisible = true
        requireActivity().window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    } catch (e: Exception) {
        Log.e("requireActivity()", "Fragment.showInProgress()")
    }
}

fun Fragment.hideInProgress() {
    try {
        loadingView()?.isVisible = false
        requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    } catch (e: Exception) {
        Log.e("requireActivity()", "FragmentExt")
    }
}

fun Fragment.showSnackbar(text: String) {
    Snackbar.make(view!!, text, Snackbar.LENGTH_LONG).show()
}

internal fun Fragment.loadingView(): View? {
    try {
        return view?.findViewById(R.id.loadingView)
            ?: requireActivity().findViewById(R.id.loadingView)
    } catch (e: Exception) {
        Log.e("requireActivity()", "Fragment.loadingView()")
    }

    return null
}