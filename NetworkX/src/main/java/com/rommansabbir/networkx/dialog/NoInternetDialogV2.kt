package com.rommansabbir.networkx.dialog

import android.app.Activity
import android.app.Dialog
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.rommansabbir.networkx.R
import com.rommansabbir.networkx.databinding.ContentDialogNoInternetBinding
import com.rommansabbir.networkx.extension.getDialogInstance
import java.lang.ref.WeakReference

class NoInternetDialogV2 constructor(
    private val activity: WeakReference<Activity>,
    private val title: String,
    private val message: String,
    private val buttonTitle: String,
    private val isCancelable: Boolean,
    @DrawableRes private val drawable: Int? = null,
    private val callback: () -> Unit
) {
    init {
        activity.get()?.let { activity ->
            getDialogInstance<ContentDialogNoInternetBinding>(
                activity,
                R.layout.content_dialog_no_internet,
                R.style.my_dialog,
                isCancelable
            ) { dialog, binding ->
                NoInternetDialogV2.dialog = dialog
                binding.cdniBtnRetry.text = buttonTitle
                binding.cdniTvTitle.text = title
                binding.cdniTvMessage.text = message
                drawable?.let {
                    binding.imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity,
                            drawable
                        )
                    )
                }
                NoInternetDialogV2.dialog?.setOnDismissListener {
                    NoInternetDialogV2.dialog = null
                }
                binding.cdniBtnRetry.setOnClickListener { callback.invoke();NoInternetDialogV2.dialog?.cancel() }
                NoInternetDialogV2.dialog?.show()
            }
        } ?: kotlin.run { dialog = null }
    }

    companion object {
        private var dialog: Dialog? = null
        val isVisible: Boolean = dialog != null && dialog!!.isShowing
    }
}