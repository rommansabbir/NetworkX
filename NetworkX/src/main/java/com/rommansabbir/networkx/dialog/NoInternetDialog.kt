package com.rommansabbir.networkx.dialog

import android.app.Activity
import com.rommansabbir.networkx.R
import com.rommansabbir.networkx.databinding.ContentDialogNoInternetBinding
import com.rommansabbir.networkx.exceptions.NoInternetDialogException
import com.rommansabbir.networkx.extension.getDialogInstance
import java.lang.ref.WeakReference

class NoInternetDialog private constructor(private var builder: Builder) {
    companion object {
        private var isDialogVisible: Boolean = false

        /**
         * Check if the [NoInternetDialog] is currently visible or not
         *
         * @return [Boolean]
         */
        fun isDialogVisible(): Boolean = isDialogVisible

        class Builder {
            private var activity: WeakReference<Activity>? = null
            internal fun getActivity() = activity!!.get()!!

            private var title: String = ""
            internal fun getTitle() = title

            private var message: String = ""
            internal fun getMessage() = message

            private var callback: () -> Unit = {}
            internal fun getCallback(): () -> Unit = callback


            /**
             * Provide activity instance to create a new instance of [NoInternetDialog].
             *
             * @param activity, [Activity]
             * @return [Builder]
             */
            fun withActivity(activity: Activity): Builder {
                this.activity = WeakReference(activity)
                return this
            }

            /**
             * Provide custom message to show.
             *
             * @param message, [String]
             * @return [Builder]
             */
            fun withMessage(message: String): Builder {
                this.message = message
                return this
            }

            /**
             * Provide custom title to show.
             *
             * @param title, [String]
             * @return [Builder]
             */
            fun withTitle(title: String): Builder {
                this.title = title
                return this
            }

            /**
             * Register [NoInternetDialog] action click listener
             *
             * @param callback, [()]
             * @return [Builder]
             */
            fun withActionCallback(callback: () -> Unit = {}): Builder {
                this.callback = callback
                return this
            }

            /**
             * Finally, build an instance of [NoInternetDialog].
             *
             * Check if the [activity] reference is provided properly or not.
             *
             * If not provided, throw a new [NoInternetDialogException]
             *
             * @return [NoInternetDialog]
             */
            @Throws(Exception::class)
            fun build(): NoInternetDialog {
                when (activity == null) {
                    true -> {
                        throw NoInternetDialogException()
                    }
                    else -> {
                        return NoInternetDialog(this)
                    }
                }
            }
        }
    }

    fun show() {
        builder.apply {
            getDialogInstance<ContentDialogNoInternetBinding>(
                getActivity(),
                R.layout.content_dialog_no_internet,
                R.style.my_dialog,
                false
            ) { dialog, binding ->
                //Set dialog visible status to true
                isDialogVisible = true

                //Update title if provided
                if (getTitle().isNotEmpty()) binding.cdniTvTitle.text = getTitle()

                //Update message if provided
                if (getMessage().isNotEmpty()) binding.cdniTvMessage.text = getMessage()
                binding.cdniBtnRetry.setOnClickListener {
                    //Set dialog visible status to false
                    isDialogVisible = false

                    //Cancel the dialog
                    dialog.cancel()

                    //Invoke the callback
                    getCallback().invoke()
                }

                //Show the dialog
                dialog.show()
            }
        }
    }
}