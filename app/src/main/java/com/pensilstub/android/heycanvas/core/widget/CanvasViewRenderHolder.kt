package com.pensilstub.android.heycanvas.core.widget

class CanvasViewRenderHolder {
    companion object{

        private var renderCls : Class<out CanvasViewRender> = CanvasViewRender::class.java

        fun setRenderClass(cls:Class<out CanvasViewRender>){
            this.renderCls = cls
        }

        fun getRenderClass():Class<out CanvasViewRender>{
            return renderCls
        }

        fun getRenderInstance(renderCls:Class<out CanvasViewRender>):CanvasViewRender{
            return renderCls.newInstance()
        }
    }
}