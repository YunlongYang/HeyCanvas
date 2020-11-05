package com.pensilstub.android.heycanvas.util

import com.alibaba.fastjson.JSONObject

class JsonObjectWrapper(val jsonObject: JSONObject) {
    fun getFloatArray(key:String):FloatArray{
        val jsonArray = jsonObject.getJSONArray(key)
        val result = FloatArray(jsonArray.size)
        for (i in jsonArray.indices){
            result[i] = jsonArray.getFloatValue(i)
        }
        return result
    }

    fun getShortArray(key:String):ShortArray{
        val jsonArray = jsonObject.getJSONArray(key)
        val result = ShortArray(jsonArray.size)
        for (i in jsonArray.indices){
            result[i] = jsonArray.getShortValue(i)
        }
        return result
    }
}