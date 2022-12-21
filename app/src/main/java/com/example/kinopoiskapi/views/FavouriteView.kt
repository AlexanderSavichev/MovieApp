package com.example.kinopoiskapi.views

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.example.kinopoiskapi.R

private const val DEFAULT_FACE_COLOR = Color.GRAY
private const val DEFAULT_LINES_COLOR = Color.BLACK
private const val DEFAULT_TONGUE_COLOR = Color.RED
private const val DEFAULT_BORDER_WIDTH = 0.8F
private const val DEFAULT_RADIUS = 33F

class FavouriteView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.favouriteViewStyle,
    defStyleRs: Int = R.style.FavouriteViewStyle
) : View(context, attrs, defStyleAttr, defStyleRs) {
    companion object {
        private const val HAPPINESS = "happinessState"
        private const val STATE = "superState"
    }

    enum class Happiness(val param: Long) {
        HAPPY(0L),
        NEUTRAL(1L),
        SAD(2L)
    }

    private var faceColor = DEFAULT_FACE_COLOR
    private var linesColor = DEFAULT_LINES_COLOR
    private var tongueColor = DEFAULT_TONGUE_COLOR
    private var borderWidth = context.toDp(DEFAULT_BORDER_WIDTH)
    private val tonguePath = Path()
    private val mouthPath = Path()
    private var size = context.toDp(DEFAULT_RADIUS)
    private var startX = size * 0.2f
    private var startY = size * 0.6f
    private var middleX = size * 0.5f
    private var endPoint = size * 0.8f
    private var tonguePoint = size *0.4f

    var happinessState = Happiness.HAPPY.param
        set(state) {
            field = state
            invalidate()
        }

    private fun setupAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRs: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.FavouriteView,
            defStyleAttr, defStyleRs
        )
        happinessState =
            typedArray.getInt(R.styleable.FavouriteView_state, Happiness.NEUTRAL.param.toInt())
                .toLong()
        faceColor = typedArray.getColor(R.styleable.FavouriteView_faceColor, DEFAULT_FACE_COLOR)
        linesColor = typedArray.getColor(R.styleable.FavouriteView_linesColor, DEFAULT_LINES_COLOR)
        tongueColor =
            typedArray.getColor(R.styleable.FavouriteView_tongueColor, DEFAULT_TONGUE_COLOR)
        borderWidth = typedArray.getDimension(
            R.styleable.FavouriteView_borderWidth,
            context.toDp(DEFAULT_BORDER_WIDTH)
        )
        typedArray.recycle()
    }

    private val paintShapes: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            strokeWidth = context.toDp(borderWidth)
        }
    }

    init {
        attrs?.let { setupAttributes(it, defStyleAttr, defStyleRs) }
    }

    private val paintLines: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = linesColor
            strokeWidth = context.toDp(borderWidth)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putLong(HAPPINESS, happinessState)
        bundle.putParcelable(STATE, super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var viewState = state
        if (viewState is Bundle) {
            happinessState = viewState.getLong(HAPPINESS, Happiness.HAPPY.param)
            viewState = viewState.getParcelable(STATE)!!
        }
        super.onRestoreInstanceState(viewState)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
        if (happinessState == Happiness.HAPPY.param)
            drawTongue(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            resolveSize(size.toInt(), widthMeasureSpec) + paddingLeft + paddingRight,
            resolveSize(size.toInt(), heightMeasureSpec) + paddingTop + paddingBottom
        )
    }

    private fun drawFaceBackground(canvas: Canvas) {
        when (happinessState) {
            Happiness.HAPPY.param -> paintShapes.color = Color.YELLOW
            Happiness.SAD.param -> paintShapes.color = Color.RED
            Happiness.NEUTRAL.param -> paintShapes.color = Color.GRAY
        }
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paintShapes)
        canvas.drawCircle(radius, radius, radius - paintLines.strokeWidth / 2f, paintLines)
    }

    private fun drawEyes(canvas: Canvas) {
        paintShapes.color = linesColor
        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, middleX)
        canvas.drawOval(leftEyeRect, paintShapes)
        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, middleX)
        canvas.drawOval(rightEyeRect, paintShapes)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.reset()
        when (happinessState) {
            Happiness.HAPPY.param -> {
                mouthPath.moveTo(startX, startY)
                mouthPath.quadTo(middleX, startY, endPoint, startY)
                mouthPath.quadTo(middleX, size * 1.20f, startX, startY)
            }
            Happiness.NEUTRAL.param -> {
                mouthPath.moveTo(startX, startY)
                mouthPath.quadTo(middleX, startY, endPoint, startY)
                mouthPath.quadTo(middleX, size * 0.66f, startX, startY)
            }
            Happiness.SAD.param -> {
                mouthPath.moveTo(startX, endPoint)
                mouthPath.quadTo(middleX, tonguePoint, endPoint, endPoint)
                mouthPath.quadTo(middleX, startY, startX, endPoint)
            }
        }
        canvas.drawPath(mouthPath, paintShapes)
    }

    private fun drawTongue(canvas: Canvas) {
        tonguePath.reset()
        paintShapes.color = tongueColor
        tonguePath.moveTo(tonguePoint, endPoint)
        tonguePath.quadTo(middleX, size * 0.7f, startY, endPoint)
        tonguePath.quadTo(middleX, size * 0.9f, tonguePoint, endPoint)
        canvas.drawPath(tonguePath, paintShapes)
    }

    fun Context.toDp(value: Float): Float {
        return resources.displayMetrics.density * value
    }
}