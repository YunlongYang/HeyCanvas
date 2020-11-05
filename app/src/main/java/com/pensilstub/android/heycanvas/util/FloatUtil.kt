package com.pensilstub.android.heycanvas.util

import android.graphics.Bitmap

class FloatUtil {
    companion object{
        fun exchangeXY(floatArray: FloatArray,width:Float,height:Float){
            for (i in 0 until floatArray.size/2){
                val y = floatArray[2*i]
                val x = floatArray[2*i+1]
                floatArray[2*i] = x+2*(width/2-x)
                floatArray[2*i+1] = y
            }
        }

        fun mirrorX(floatArray: FloatArray,width:Float,height:Float){
            for (i in 0 until floatArray.size/2){
                val x = floatArray[2*i]
                floatArray[2*i] = x + (width/2-x)*2
            }
        }

        fun handleChangeToCanvas(floatArray: FloatArray, bitmap: Bitmap){
            for (i in 0 until floatArray.size){
                if(i%2==1){
                    floatArray[i] = -floatArray[i]
                }
            }
            for (i in 0 until floatArray.size){
                if(i%2==0){
                    floatArray[i]-=bitmap.width.toFloat()/2
                }
                if(i%2==1){
                    floatArray[i]+=bitmap.height.toFloat()/2
                }
            }
        }

        fun handleOffsetChangeToCanvas(floatArray: FloatArray){
            for (i in 0 until floatArray.size/2){
                val y = floatArray[2*i]
                val x = floatArray[2*i+1]
                floatArray[2*i] = -x
                floatArray[2*i+1] = -y
            }
        }
    }
}