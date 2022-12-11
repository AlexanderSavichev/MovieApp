package com.example.kinopoiskapi.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.kinopoiskapi.R

class FavouriteView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    companion object {
        private const val DEFAULT_FACE_COLOR = Color.GRAY
        private const val DEFAULT_LINES_COLOR = Color.BLACK
        private const val DEFAULT_TONGUE_COLOR = Color.RED
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val NEUTRAL = 1L
        const val SAD = 2L
    }


    private val paint = Paint()
    private var faceColor = Color.GRAY
    private var linesColor = Color.BLACK
    private var tongueColor = Color.RED
    private var borderWidth = 4.0f

    private val tonguePath = Path()
    private val mouthPath = Path()
    private var size = 80

    var happinessState = HAPPY
        set(state) {
            field = state
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.FavouriteView,
            0, 0)

        happinessState = typedArray.getInt(R.styleable.FavouriteView_state, NEUTRAL.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.FavouriteView_faceColor, DEFAULT_FACE_COLOR)
        linesColor = typedArray.getColor(R.styleable.FavouriteView_linesColor, DEFAULT_LINES_COLOR)
        tongueColor = typedArray.getColor(R.styleable.FavouriteView_tongueColor, DEFAULT_TONGUE_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.FavouriteView_borderWidth,
            DEFAULT_BORDER_WIDTH)

        typedArray.recycle()
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
//        drawTongue(canvas)
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(size, size)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        if(happinessState == HAPPY){
            paint.color = Color.YELLOW
        }
        else if (happinessState == SAD){
            paint.color = Color.RED
        }
        else
            paint.color = Color.GRAY

        paint.style = Paint.Style.FILL
        val radius = size / 2f
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)
        paint.color = linesColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.color = linesColor
        paint.style = Paint.Style.FILL
        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftEyeRect, paint)
        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas.drawOval(rightEyeRect, paint)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.reset()
        mouthPath.moveTo(size * 0.2f, size * 0.6f)
        if (happinessState == HAPPY) {
            // 1
            mouthPath.quadTo(size * 0.50f, size * 0.6f, size * 0.85f, size * 0.60f)
            mouthPath.quadTo(size * 0.50f, size * 1.20f, size * 0.2f, size * 0.60f)
        } else if(happinessState == NEUTRAL){
            // 2
            mouthPath.quadTo(size * 0.50f, size * 0.6f, size * 0.85f, size * 0.63f)
            mouthPath.quadTo(size * 0.50f, size * 0.66f, size * 0.2f, size * 0.60f)
        }
        else{
            mouthPath.quadTo(size * 0.50f, size * 0.4f, size * 0.85f, size * 0.5f)
            mouthPath.quadTo(size * 0.50f, size * 0.5f, size * 0.2f, size * 0.60f)
        }

        paint.color = linesColor
        paint.style = Paint.Style.FILL
        canvas.drawPath(mouthPath, paint)
    }

//    private fun drawTongue(canvas: Canvas) {
//        tonguePath.reset()
//        paint.color = tongueColor
//        paint.style = Paint.Style.FILL
//        tonguePath.moveTo(size * 0.4f, size * 0.8f)
//        tonguePath.quadTo(size * 0.50f, size * 0.7f, size * 0.6f, size * 0.8f)
//        tonguePath.quadTo(size * 0.50f, size * 0.9f, size * 0.4f, size * 0.8f)
//        canvas.drawPath(tonguePath, paint)
//    }
}