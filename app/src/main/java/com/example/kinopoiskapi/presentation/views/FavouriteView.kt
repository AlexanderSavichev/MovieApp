package com.example.kinopoiskapi.presentation.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.kinopoiskapi.R

class FavouriteView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.favouriteViewStyle,
    defStyleRs: Int = R.style.FavouriteViewStyle
) : View(context, attrs, defStyleAttr, defStyleRs) {

    private var faceColor = DEFAULT_FACE_COLOR
    private var linesColor = DEFAULT_LINES_COLOR
    private var tongueColor = DEFAULT_TONGUE_COLOR
    private var borderWidth = context.toDp(DEFAULT_BORDER_WIDTH)
    private val tonguePath = Path()
    private val mouthPath = Path()
    private val size = context.toDp(DEFAULT_RADIUS)
    private val startX = size * 0.2f
    private val startY = size * 0.6f
    private val middleX = size * 0.5f
    private val endPoint = size * 0.8f
    private val tonguePoint = size *0.4f
    private val topRound = size * 0.23f
    private val leftEyeLeft = size * 0.32f
    private val rightEyeRight = size * 0.68f
    private val leftEyeRight = size * 0.43f
    private val rightEyeLeft = size * 0.57f
    private val mouthEndY = size * 1.20f
    private val tongueY = size * 0.9f
    private var happinessState: Happiness = Happiness.NEUTRAL

    fun setHappiness(state:Happiness){
        happinessState = state
        invalidate()
    }

    private fun setupAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRs: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.FavouriteView,
            defStyleAttr, defStyleRs
        )
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
        if (happinessState == Happiness.HAPPY)
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
            Happiness.HAPPY -> paintShapes.color = Color.YELLOW
            Happiness.SAD -> paintShapes.color = Color.RED
            Happiness.NEUTRAL -> paintShapes.color = Color.GRAY
        }
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paintShapes)
        canvas.drawCircle(radius, radius, radius - paintLines.strokeWidth / 2f, paintLines)
    }

    private fun drawEyes(canvas: Canvas) {
        paintShapes.color = linesColor
        val leftEyeRect = RectF(leftEyeLeft, topRound, leftEyeRight, middleX)
        canvas.drawOval(leftEyeRect, paintShapes)
        val rightEyeRect = RectF(rightEyeLeft, topRound, rightEyeRight, middleX)
        canvas.drawOval(rightEyeRect, paintShapes)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.reset()
        when (happinessState) {
            Happiness.HAPPY -> {
                mouthPath.moveTo(startX, startY)
                mouthPath.quadTo(middleX, startY, endPoint, startY)
                mouthPath.quadTo(middleX, mouthEndY, startX, startY)
            }
            Happiness.NEUTRAL -> {
                mouthPath.moveTo(startX, startY)
                mouthPath.quadTo(middleX, startY, endPoint, startY)
                mouthPath.quadTo(middleX, rightEyeRight, startX, startY)
            }
            Happiness.SAD -> {
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
        tonguePath.quadTo(middleX, rightEyeRight, startY, endPoint)
        tonguePath.quadTo(middleX, tongueY, tonguePoint, endPoint)
        canvas.drawPath(tonguePath, paintShapes)
    }

    private fun Context.toDp(value: Float): Float {
        return resources.displayMetrics.density * value
    }

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.GRAY
        private const val DEFAULT_LINES_COLOR = Color.BLACK
        private const val DEFAULT_TONGUE_COLOR = Color.RED
        private const val DEFAULT_BORDER_WIDTH = 0.8F
        private const val DEFAULT_RADIUS = 33F
    }

    enum class Happiness {
        HAPPY,
        NEUTRAL,
        SAD
    }
}