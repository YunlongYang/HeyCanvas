package com.pensilstub.android.heycanvas.core.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.pensilstub.android.heycanvas.R
import com.pensilstub.android.heycanvas.vertex.BitmapVerticesParam

class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val renderInstance =
        CanvasViewRenderHolder.getRenderInstance(
            CanvasViewRenderHolder.getRenderClass()
        )

    init {
        renderInstance.attachView(this)
        renderInstance.onCreate(context)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        renderInstance.onLayoutChange(
            left, top, right, bottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        renderInstance.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        renderInstance.onTouchEvent(event)
        return true
    }
}