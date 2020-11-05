package com.pensilstub.android.heycanvas.vertex

import android.graphics.Canvas

/**
@NonNull VertexMode mode;
int vertexCount;
float[] verts;
int vertOffset;
float[] texs;
int texOffset;
int[] colors;
int colorOffset;
short[] indices;
int indexOffset;
int indexCount;
Paint paint
 */
class BitmapVerticesParam {
    var mode: Canvas.VertexMode = Canvas.VertexMode.TRIANGLES
    var vertexCount = 0
    var verts : FloatArray = FloatArray(0)
    var vertOffset = 0
    var texs : FloatArray? = null
    var texOffset = 0
    var colors : IntArray? =null
    var colorOffset = 0
    var indices : ShortArray? = null
    var indexOffset = 0
    var indexCount = 0
}