package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.udacity.util.dp
import com.udacity.util.sp
import kotlinx.android.synthetic.main.content_main.view.*
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var robotoFont = "roboto"
    private var mButtonText: String
    private var mBackgroundColor: Int = 0
    private var progress: Float = 0f
    private var valueAnimator = ValueAnimator()
    private val textRect = Rect()

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { _, _, newValue ->
        when (newValue) {
            ButtonState.Loading -> {
                valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener {
                        progress = animatedValue as Float
                        invalidate()
                    }
                    repeatMode = ValueAnimator.REVERSE
                    repeatCount = ValueAnimator.INFINITE
                    duration = 750
                    start()
                }

                setText(context.getString(R.string.loading))
                setBgColor(context.getColor(R.color.colorPrimaryDark))
                disableLoadingButton()
            }

            ButtonState.Completed -> {
                valueAnimator.cancel()
                resetProgress()
                setText(context.getString(R.string.download))
                setBgColor(context.getColor(R.color.colorPrimary))
                enableLoadingButton()
            }

            ButtonState.Clicked -> {
                // Do nothing just here so the when statement is exhaustive :<
            }
        }
        invalidate()
    }

    init {
        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingButton,
                0, 0).apply {

            try {
                mButtonText = getString(R.styleable.LoadingButton_text).toString()
                mBackgroundColor = getColor(R.styleable.LoadingButton_loadingColor, Color.WHITE)
            } finally {
                recycle()
            }
        }
    }

    // Used for the styling of the text...
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 18.sp
        color = Color.WHITE
        typeface = Typeface.create(robotoFont, Typeface.BOLD)
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimary)
    }

    private val inProgressBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
    }

    private val inProgressArcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.colorAccent)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cornerRadius = 4.dp
        val backgroundWidth  = measuredWidth.toFloat()
        val backgroundHeight = measuredHeight.toFloat()

        canvas.drawColor(mBackgroundColor)
        textPaint.getTextBounds(mButtonText, 0, mButtonText.length, textRect)
        canvas.drawRect(0f, 0f, backgroundWidth, backgroundHeight, backgroundPaint)

        if (buttonState == ButtonState.Loading) {
            var progressVal = progress * backgroundWidth
            canvas.drawRect(0f, 0f, progressVal, backgroundHeight, inProgressBackgroundPaint)

            val arcDiameter = cornerRadius * 2
            val arcRectSize = backgroundHeight - paddingBottom.toFloat() - paddingTop.toFloat() - arcDiameter

            progressVal = progress * 360f
            canvas.drawArc( backgroundWidth -arcDiameter - arcRectSize- paddingStart.toFloat(),
                            paddingTop.toFloat() + arcDiameter,
                            backgroundWidth - arcDiameter,
                            arcRectSize,
                            0f,
                            progressVal,
                            true,
                            inProgressArcPaint)
        }
        val centerX = backgroundWidth / 2
        val centerY = backgroundHeight / 2 - textRect.centerY()

        canvas.drawText(mButtonText, centerX, centerY, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
                MeasureSpec.getSize(w),
                heightMeasureSpec,
                0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

    private fun disableLoadingButton() {
        loadingButton.isEnabled = false
    }

    private fun enableLoadingButton() {
        loadingButton.isEnabled = true
    }

    // Used to provide a way to change the button state from the main activity
    fun setLoadingButtonState(state: ButtonState) {
        buttonState = state
    }

    //Sets the button text.
    private fun setText(buttonText: String) {
        mButtonText = buttonText
        invalidate()
        requestLayout()
    }

    private fun setBgColor(backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        invalidate()
        requestLayout()
    }

    private fun resetProgress() {
        progress = 0f
    }
}