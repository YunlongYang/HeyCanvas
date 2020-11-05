package com.pensilstub.android.heycanvas.core.widget

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View

abstract class CanvasViewRender {
    protected var mHostView : View? = null

    fun attachView(hostView: View){
        mHostView = hostView
    }

    abstract fun onCreate(context:Context)

    abstract fun onLayoutChange(left:Int,top:Int,right:Int,bottom:Int)

    abstract fun onDraw(canvas: Canvas)

    abstract fun onTouchEvent(event: MotionEvent)
}