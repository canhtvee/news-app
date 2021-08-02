package com.canhtv.ee.newsapp.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LinePagerIndicatorDecoration() : ItemDecoration() {
    private val colorActive = 0xFFFFFFFF
    private val colorInactive = 0x66FFFFFF

    // Height of the space the indicator takes up at the bottom of the view.
    private val mIndicatorHeight = (DP * 16)

    // Indicator stroke width.
    private val mIndicatorStrokeWidth = (DP * 2)

    // Indicator width.
    private val mIndicatorItemLength = (DP * 40)

    // Padding between indicators.
    private val mIndicatorItemPadding = (DP * 6)

    // Some more natural animation interpolation
    private val mInterpolator: Interpolator = AccelerateDecelerateInterpolator()
    private val mPaint: Paint = Paint()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter!!.itemCount

        // center horizontally, calculate width and subtract half from center
        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = ((parent.width - indicatorTotalWidth) / 2f)

        // center vertically in the allotted space
        val indicatorPosY = (parent.height - mIndicatorHeight / 2f)

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

        // find active page (which should be highlighted)
        val layoutManager = parent.layoutManager as LinearLayoutManager?
        val firstActivePosition = layoutManager!!.findFirstVisibleItemPosition()
        val lastActivePosition = layoutManager.findLastVisibleItemPosition()

        // find offset of active page (if the user is scrolling)
        val activeChild: View? = layoutManager.findViewByPosition(firstActivePosition)
        val width = activeChild!!.width
        val left = activeChild.left
        val activePosition = if (left < -width/2f) lastActivePosition else firstActivePosition

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition)

    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = colorInactive

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawLine(start,
                indicatorPosY,
                (start + mIndicatorItemLength), indicatorPosY, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int
    ) {
        mPaint.color = colorActive.toInt()
        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0..highlightPosition) {
            // draw the line for every item
            c.drawLine(start, indicatorPosY,start + mIndicatorItemLength, indicatorPosY, mPaint)
            start += itemWidth
        }
    }

    companion object {
        private val DP: Float = Resources.getSystem().displayMetrics.density
    }

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }
}