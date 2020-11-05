package com.pensilstub.android.heycanvas.lession.vertex

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.alibaba.fastjson.JSON
import com.pensilstub.android.heycanvas.core.widget.CanvasViewRender
import com.pensilstub.android.heycanvas.util.FloatUtil
import com.pensilstub.android.heycanvas.util.JsonObjectWrapper
import com.pensilstub.android.heycanvas.vertex.BitmapVerticesParam
import kotlin.random.Random

class AnimationVertexData{
    var offset = 0
    var vertices = FloatArray(0)

    fun maxIndex():Int{
        return offset + vertices.size
    }

    fun contain(i:Int):Boolean{
        if(vertices.isEmpty()){
            return false
        }else{
            return i in offset until offset+vertices.size
        }
    }

    fun getValue(i:Int):Float{
        return vertices[i-offset]
    }
}

class VertexRender : CanvasViewRender() {

    private lateinit var paint: Paint

    private lateinit var bitmap: Bitmap

    private val normalPaint = Paint()

    val param = BitmapVerticesParam()

    var originVerts = FloatArray(0)

    var animationVertsList = mutableListOf<AnimationVertexData>()

    private val random = Random(10000)

    private var mAnimationIndex = -1

    override fun onCreate(context: Context) {

        bitmap =
            BitmapFactory.decodeStream(context.resources.assets.open("10d8e0e4e0f047b398999e9190042487.png"))

        paint = Paint().apply {
            val shader = BitmapShader(
                bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            this.shader = shader
        }

        val jsonObject = JSON.parseObject(
            context.resources.assets.open("body_vertics.json.json").bufferedReader().readText()
        )
        val attachment = jsonObject.getJSONArray("skins")
            .getJSONObject(0)
            .getJSONObject("attachments")
            .getJSONObject("WechatIMG850")
            .getJSONObject("WechatIMG850")
        val attachmentWrapper = JsonObjectWrapper(attachment)
        originVerts = attachmentWrapper.getFloatArray("vertices")
        FloatUtil.exchangeXY(originVerts,bitmap.width.toFloat(),bitmap.height.toFloat())
        FloatUtil.handleChangeToCanvas(originVerts,bitmap)
//        originVerts = floatArrayOf(
//            120f,180f,
//            0f,421f,
//            253f,0f,
//            253f,421f,
//        )
        param.verts = originVerts.copyOf(originVerts.size)
        param.vertexCount = param.verts.size
        param.texs = originVerts.copyOf(originVerts.size)
        param.texOffset = 0
        param.indices = attachmentWrapper.getShortArray("triangles")
        param.indexOffset = 0
        param.indexCount = param.indices?.size ?: 0

        animationVertsList.clear()
        val animationArray = jsonObject.getJSONObject("animations")
            .getJSONObject("animation")
            .getJSONObject("deform")
            .getJSONObject("default")
            .getJSONObject("WechatIMG850")
            .getJSONArray("WechatIMG850")
        animationVertsList.add(AnimationVertexData())
        for (i in animationArray.indices){
            val item = JsonObjectWrapper(animationArray.getJSONObject(i))
            val vertexData = AnimationVertexData()
            vertexData.offset = item.jsonObject.getIntValue("offset")
            vertexData.vertices = item.getFloatArray("vertices")
//            FloatUtil.exchangeXY( vertexData.vertices)
            FloatUtil.handleOffsetChangeToCanvas(vertexData.vertices)
            animationVertsList.add(
                vertexData
            )
        }

    }

    override fun onLayoutChange(left: Int, top: Int, right: Int, bottom: Int) {
//        for (i in originVerts.indices) {
//            if (i % 2 == 1) {
//                param.verts[i] = bottom - originVerts[i]
//                param.texs?.let { param_texs ->
//                    param_texs[i] = param.verts[i]
//                }
//            }
//        }
    }


    override fun onDraw(canvas: Canvas) {

        canvas.save()

        canvas.translate(380f, 380f)

        canvas.drawVertices(
            param.mode,
            param.vertexCount,
            param.verts,
            param.vertOffset,
            param.texs,
            param.texOffset,
            param.colors,
            param.colorOffset,
            param.indices,
            param.indexOffset,
            param.indexCount,
            paint
        )

//        canvas.drawBitmap(
//            bitmap,
//            0f,
//            0f,
//            normalPaint
//        )


        normalPaint.color = Color.RED
        normalPaint.strokeWidth = 8f
        normalPaint.textSize = 18f

        normalPaint.style = Paint.Style.STROKE
        canvas.drawRect(
            0f,0f,bitmap.width.toFloat(),bitmap.height.toFloat(),normalPaint
        )

        normalPaint.style = Paint.Style.FILL

        for (i in 0 until param.vertexCount / 2) {
            canvas.drawCircle(
                param.verts[2 * i],
                param.verts[2 * i + 1],
                12f,
                normalPaint
            )
            canvas.drawText(
                String.format("%02d",i), param.verts[2 * i]+18,
                param.verts[2 * i + 1], normalPaint
            )
        }

        param.texs?.let {
            normalPaint.color = Color.GREEN
            for (i in 0 until it.size/ 2) {
                canvas.drawCircle(
                    it[2 * i],
                    it[2 * i + 1],
                    6f,
                    normalPaint
                )
                canvas.drawText(
                    String.format("%02d",i), param.verts[2 * i]+18,
                    param.verts[2 * i + 1], normalPaint
                )
            }
        }

        canvas.restore()
//        canvas.drawBitmap
    }

    private var lastX = 0f
    private var lastY = 0f

    override fun onTouchEvent(event: MotionEvent) {
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE->{
                val x = event.x
                val y = event.y
                onMove(x-lastX,y-lastY)
                lastX = x
                lastY = y
            }
        }
    }

    private fun onMove(distanceX:Float,distanceY: Float){
        mAnimationIndex++
        if(mAnimationIndex>=animationVertsList.size){
            mAnimationIndex = 0
        }
        param.texs?.let {
            if(mAnimationIndex>=0 && mAnimationIndex<animationVertsList.size){
                val animationVertexData = animationVertsList[mAnimationIndex]
                for (i in it.indices){
                    if(!animationVertexData.contain(i)){
                        param.verts[i] = it[i]
                    }else {
                        param.verts[i] = it[i] + animationVertexData.getValue(i)
                    }
                }
//                val size = it.size
//                for (i in 0 until (size/2)){
//                    if(i!=14){
//                        param.verts[2*i] = it[2*i]
//                        param.verts[2*i+1] = it[2*i+1]
//                    }else {
//                        param.verts[2*i] -= distanceX
//                        param.verts[2*i] -= distanceY
//                    }
//                }
            }

        }
        mHostView?.invalidate()
    }
}