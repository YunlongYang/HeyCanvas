package com.pensilstub.android.heycanvas.lession.vertex

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.pensilstub.android.heycanvas.R
import com.pensilstub.android.heycanvas.core.widget.CanvasViewRender
import com.pensilstub.android.heycanvas.vertex.BitmapVerticesParam

class VertexRenderLocal : CanvasViewRender() {

    private lateinit var paint : Paint

    private lateinit var bitmap : Bitmap

    private val normalPaint = Paint()

    val param = BitmapVerticesParam()

    private val mMatrix = Matrix()
    private val mInverse = Matrix()

    override fun onCreate(context: Context) {

        bitmap = BitmapFactory.decodeResource(
            context.resources, R.mipmap.ic_red_bag
        )

        paint = Paint().apply {
            val shader = BitmapShader(
                bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
            )
            this.shader = shader
        }

        param.verts = FloatArray(10)
        param.vertexCount = param.verts.size
        val w = bitmap.width.toFloat()
        val h = bitmap.height.toFloat()

        val param_texs = FloatArray(10)
        setXY(param_texs, 0, w / 2, h / 2);
        setXY(param_texs, 1, 0f, 0f);
        setXY(param_texs, 2, w, 0f);
        setXY(param_texs, 3, w, h);
        setXY(param_texs, 4, 0f, h);
        param.texs = param_texs
        param.texOffset = 0

        setXY(param.verts, 0, w / 2, h / 2);
        setXY(param.verts, 1, 0f, 0f);
        setXY(param.verts, 2, w, 0f);
        setXY(param.verts, 3, w, h);
        setXY(param.verts, 4, 0f, h);

        param.indices = shortArrayOf(0, 1, 2, 3, 4, 1)
        param.indexCount = 6
        param.indexOffset = 0

        mMatrix.setScale(0.8f, 0.8f);
        mMatrix.preTranslate(20f, 20f);
        mMatrix.invert(mInverse);

//        val jsonObject = JSON.parseObject(context.resources.assets.open("body_vertics.json.json").bufferedReader().readText())
//        val vertexArray = jsonObject.getJSONArray("skins")
//            .getJSONObject(0)
//            .getJSONObject("attachments")
//            .getJSONObject("WechatIMG850")
//            .getJSONObject("WechatIMG850")
//            .getJSONArray("vertices")
//        val floatList = mutableListOf<Float>()
//        for (i in 0 until vertexArray.size){
//            floatList.add(vertexArray.getFloatValue(i))
//        }
//        originVerts = floatList.toFloatArray()
//        originVerts = floatArrayOf(
//            120f,180f,
//            0f,421f,
//            253f,0f,
//            253f,421f,
//        )
//        param.verts = originVerts
//        param.vertexCount =param.verts.size
//        param.texs = floatArrayOf(
//            10f,10f,
//            0f,421f,
//            253f,0f,
//            253f,421f,
//        )
//        param.texOffset = 0
    }

    override fun onLayoutChange(left: Int, top: Int, right: Int, bottom: Int) {
//        param.verts = FloatArray(originVerts.size)
//        for (i in originVerts.indices){
//            param.verts[i] = originVerts[i]
//        }
//        param.texs = param.verts
//
//        param.vertexCount = param.verts.size
//
//        param.texOffset = 0
//
//        param.verts = floatArrayOf(31f,21f,32f,151f,150f,104f)
//        param.vertexCount = param.verts.size
//
//        param.texs = floatArrayOf(1f,1f,62f,251f,150f,104f)
//        param.texOffset = 0
    }


    override fun onDraw(canvas: Canvas) {

        /**
        @NonNull VertexMode mode, int vertexCount, @NonNull float[] verts,
        int vertOffset, @Nullable float[] texs, int texOffset, @Nullable int[] colors,
        int colorOffset, @Nullable short[] indices, int indexOffset, int indexCount,
        @NonNull Paint paint
         */

        canvas.save();
        canvas.drawColor(Color.GRAY)
        canvas.concat(mMatrix);
        canvas.drawVertices(
            Canvas.VertexMode.TRIANGLE_FAN, param.vertexCount, param.verts, 0,
            param.texs, 0, null, 0, null, 0, 0, paint
        );
        canvas.translate(0f, 240f);
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
        canvas.restore()

//        canvas.drawBitmap(bitmap,0f,0f,normalPaint)
    }

    override fun onTouchEvent(event: MotionEvent) {
        val pt = floatArrayOf(event.x, event.y)
        mInverse.mapPoints(pt)
        setXY(param.verts, 0, pt[0], pt[1])

//        param.indices = shortArrayOf( 0, 1, 2, 3, 4, 1 )
//        param.

        mHostView?.invalidate()
    }

    companion object{
        fun setXY(array: FloatArray, index: Int, x: Float, y: Float) {
            array[index * 2 + 0] = x
            array[index * 2 + 1] = y
        }
    }
}