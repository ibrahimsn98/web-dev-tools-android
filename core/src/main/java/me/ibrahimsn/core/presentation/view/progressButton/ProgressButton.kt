package me.ibrahimsn.core.presentation.view.progressButton

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import me.ibrahimsn.core.R

class ProgressButton : AppCompatImageButton {

    private val loadingStrokeWidth = 5f

    private var icon: Drawable? = null
    private var reverseIcon: Drawable? = null

    private var loadingAngle = 0f
    private val loaderRect = RectF()

    private var _isReversed: Boolean = false
    private var _isProcessing: Boolean = false

    private val paintProgress = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = Color.parseColor("#01D36D")
        strokeWidth = loadingStrokeWidth
        strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        loaderRect.set(
            loadingStrokeWidth,
            loadingStrokeWidth,
            width - loadingStrokeWidth,
            height - loadingStrokeWidth
        )
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressButton, 0, 0)
        icon = typedArray.getDrawable(R.styleable.ProgressButton_icon)
        reverseIcon = typedArray.getDrawable(R.styleable.ProgressButton_reverseIcon)
        typedArray.recycle()

        setImageDrawable(icon)
    }

    var isReversed: Boolean get() = _isReversed
        set(value) {
            _isReversed = value
            setImageDrawable(
                if (value) reverseIcon else icon
            )
        }

    var isProgressing: Boolean get() = _isProcessing
        set(value) {
            _isProcessing = value
            isEnabled = !isProgressing

            if (isProgressing) {
                anim.start()
            } else {
                anim.end()
            }
        }

    private val anim = ValueAnimator.ofFloat(0f, 360f).apply {
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        duration = 500

        addUpdateListener {
            loadingAngle = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (isProgressing) {
            canvas.drawArc(loaderRect, loadingAngle, 35f, false, paintProgress)
        }
    }
}
