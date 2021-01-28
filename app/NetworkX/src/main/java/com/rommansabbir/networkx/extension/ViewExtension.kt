package com.rommansabbir.networkx.extension

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Return an instance of a [Dialog] associated with the given [customStyle] & [layoutId]
 *
 * @param activity, [Activity]
 * @param layoutId, [Int]
 * @param customStyle, [Int]
 * @param setCancelable, [Boolean]
 * @param onSuccess, [(Dialog, V)]
 */
inline fun <V : ViewDataBinding> getDialogInstance(
    activity: Activity,
    layoutId: Int,
    @StyleRes customStyle: Int? = null,
    setCancelable: Boolean = false,
    crossinline onSuccess: (Dialog, V) -> Unit
) {
    val layout = DataBindingUtil.inflate<V>(LayoutInflater.from(activity), layoutId, null, false)
    val dialog = if (customStyle == null) Dialog(activity) else Dialog(activity, customStyle)
    dialog.setContentView(layout.root)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCanceledOnTouchOutside(setCancelable)
    dialog.setCancelable(false)
    onSuccess.invoke(dialog, layout)
}