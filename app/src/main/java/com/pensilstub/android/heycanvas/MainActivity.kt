package com.pensilstub.android.heycanvas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pensilstub.android.heycanvas.core.widget.CanvasViewRenderHolder
import com.pensilstub.android.heycanvas.lession.vertex.VertexRender
import com.pensilstub.android.heycanvas.lession.vertex.VertexRenderLocal
import com.pensilstub.android.heycanvas.ui.test.canvas.TestActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        CanvasViewRenderHolder.setRenderClass(VertexRenderLocal::class.java)
        CanvasViewRenderHolder.setRenderClass(VertexRender::class.java)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this,TestActivity::class.java))
    }
}