package co.uk.akm.test.postlist.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import co.uk.akm.test.postlist.R
import kotlinx.android.synthetic.main.view_request_status.view.*

/**
 * Utility view that combines a progress bar and an error message in a single widget.
 * This view has utility method to show 3 status values of the request:
 * 1) in progress
 * 2) success
 * 3) error
 */
class RequestStatusView : FrameLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_request_status, this)
        showSuccess()
    }

    fun showSuccess() {
        statusError.text = null
        statusError.visibility = View.INVISIBLE
        statusProgress.visibility = View.INVISIBLE
    }

    fun showProgress() {
        statusError.text = null
        statusError.visibility = View.INVISIBLE
        statusProgress.visibility = View.VISIBLE
    }

    fun showError(errorResId: Int) {
        showError(resources.getString(errorResId))
    }

    fun showError(error: String) {
        statusError.text = error
        statusProgress.visibility = View.INVISIBLE
        statusError.visibility = View.VISIBLE
    }
}