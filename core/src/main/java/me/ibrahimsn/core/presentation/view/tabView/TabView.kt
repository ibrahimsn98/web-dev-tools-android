package me.ibrahimsn.core.presentation.view.tabView

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.BounceInterpolator
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import me.ibrahimsn.core.R

class TabView : View {

    private var itemBackgroundColor = Color.parseColor("#1c1e28")
    private var indicatorColor = Color.parseColor("#1DB954")
    private var itemTextColor = Color.parseColor("#b3ffffff")
    private var itemTextColorActive = Color.parseColor("#ffffff")
    private var itemTextSize = 0f
    private var itemMargin = 20f
    private var itemPaddingVertical = 10f
    private var itemPaddingHorizontal = 10f
    private var activeItem = 0

    @FontRes
    private var itemFontFamily: Int = 0
    private var items = listOf<TabItem>()

    private var _onItemSelectedListener: (Int) -> Unit = {}
    var onItemSelectedListener get() = _onItemSelectedListener
        set(value) { _onItemSelectedListener = value }

    private var currentActiveItemColor = itemTextColorActive
    private var indicatorLocation = 0f

    private val paintIndicator = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        color = indicatorColor
        strokeCap = Paint.Cap.ROUND
    }

    private val paintRect = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = itemBackgroundColor
    }

    private val paintText= Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = itemTextColor
        textSize = itemTextSize
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.TabView, 0, 0)

        itemMargin = typedArray.getDimension(R.styleable.TabView_itemMargin, this.itemMargin)
        itemPaddingVertical = typedArray.getDimension(R.styleable.TabView_itemPaddingVertical, this.itemPaddingVertical)
        itemPaddingHorizontal = typedArray.getDimension(R.styleable.TabView_itemPaddingHorizontal, this.itemPaddingHorizontal)
        indicatorColor = typedArray.getColor(R.styleable.TabView_indicatorColor, this.indicatorColor)
        itemTextColor = typedArray.getColor(R.styleable.TabView_textColor, this.itemTextColor)
        itemTextColorActive = typedArray.getColor(R.styleable.TabView_textColorActive, this.itemTextColorActive)
        itemTextSize = typedArray.getDimension(R.styleable.TabView_textSize, this.itemTextSize)
        itemFontFamily = typedArray.getResourceId(R.styleable.TabView_textFontFamily, this.itemFontFamily)
        items = TabParser(context, typedArray.getResourceId(R.styleable.TabView_menu, 0)).parse()
        typedArray.recycle()

        paintIndicator.color = indicatorColor
        paintText.color = itemTextColor
        paintText.textSize = itemTextSize

        if (itemFontFamily != 0) paintText.typeface = ResourcesCompat.getFont(context, itemFontFamily)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            measuredWidth,
            (itemTextSize + itemPaddingVertical * 2).toInt()
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val strokeWidth = itemTextSize + itemPaddingVertical * 2
        paintIndicator.strokeWidth = strokeWidth

        var lastX = itemMargin

        for (item in items) {
            val itemWidth = paintText.measureText(item.title)

            item.rect = RectF(
                lastX,
                0f,
                itemWidth + lastX + itemPaddingHorizontal*2,
                itemTextSize + itemPaddingVertical*2
            )

            lastX += (itemWidth + itemPaddingHorizontal*2 + itemMargin)
        }

        indicatorLocation = items[0].rect.centerX()
        setActiveItem(activeItem)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (item in items) {
            canvas.drawRoundRect(item.rect, 200f, 200f, paintRect)
        }

        val indicatorWidth = (items[activeItem].rect.width() + 10f)/2

        canvas.drawLine(
            indicatorLocation - indicatorWidth/2,
            height/2f,
            indicatorLocation + indicatorWidth/2,
            height/2f,
            paintIndicator
        )

        val textHeight = (paintText.fontMetrics.ascent + paintText.fontMetrics.descent) / 2

        for ((i, item) in items.withIndex()) {
            this.paintText.color = if (i == activeItem) currentActiveItemColor else itemTextColor
            canvas.drawText(
                item.title,
                item.rect.centerX(),
                item.rect.centerY() - textHeight, paintText
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP)
            for ((i, item) in items.withIndex())
                if (item.rect.contains(event.x, event.y))
                    if (i != this.activeItem) {
                        setActiveItem(i)
                        _onItemSelectedListener(i)
                    }

        return true
    }

    fun setActiveItem(pos: Int) {
        activeItem = pos
        animateIndicator(pos)
        setItemColors()
    }

    private fun animateIndicator(pos: Int) {
        val animator = ValueAnimator.ofFloat(indicatorLocation, items[pos].rect.centerX())
        animator.interpolator = BounceInterpolator()
        animator.duration = 300

        animator.addUpdateListener {
            indicatorLocation = it.animatedValue as Float
            invalidate()
        }

        animator.start()
    }

    private fun setItemColors() {
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), itemTextColor, itemTextColorActive)
        animator.duration = 300
        animator.addUpdateListener { currentActiveItemColor = it.animatedValue as Int }
        animator.start()
    }
}
