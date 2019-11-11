package com.wzy.arms.base.struct

import android.text.TextUtils
import java.util.HashMap

class FunctionsManager{

    companion object {
        var mFunctionNoParamNoResult: HashMap<String, FunctionNoParamNoResult>? = null
        var mFunctionWithParamAndResult: HashMap<String, FunctionWithParamAndResult<Any,Any>>? = null
        var mFunctionWithParamOnly: HashMap<String, FunctionWithParamOnly<Any>>? = null
        var mFunctionWithResltOnly: HashMap<String, FunctionWithResltOnly<Any>>? = null
        private var ourInstance: FunctionsManager? = null
        fun getInstance(): FunctionsManager? {
            if(null == ourInstance){
                ourInstance = FunctionsManager()
            }
            return ourInstance
        }
    }

    constructor(){
        mFunctionNoParamNoResult = HashMap<String, FunctionNoParamNoResult>()
        mFunctionWithParamAndResult = HashMap<String, FunctionWithParamAndResult<Any,Any>>()
        mFunctionWithParamOnly = HashMap<String, FunctionWithParamOnly<Any>>()
        mFunctionWithResltOnly = HashMap<String, FunctionWithResltOnly<Any>>()
    }


    fun addFunction(function: FunctionNoParamNoResult?): FunctionsManager {
        mFunctionNoParamNoResult!![function!!.mFuctionName!!] = function
        return this
    }

    @Throws(FunctionException::class)
    fun invokeFunc(funcName: String) {
        if (!TextUtils.isEmpty(funcName) && mFunctionNoParamNoResult != null) {
            val f = mFunctionNoParamNoResult!![funcName]
            if (f != null) {
                f.function()
            } else {
                throw FunctionException("Has no this function:$funcName")
            }
        }
    }

    fun addFunction(function: FunctionWithParamAndResult<Any,Any>): FunctionsManager {
        mFunctionWithParamAndResult!![function.mFuctionName!!] = function
        return this
    }

    @Throws(FunctionException::class)
    fun <Result, Param> invokeFunc(funcName: String, param: Param, c: Class<Result>?): Any? {
        if (TextUtils.isEmpty(funcName)) {
            return null
        }
        if (mFunctionWithParamAndResult != null) {
            val f = mFunctionWithParamAndResult!![funcName]
            return if (f != null) {
                if (c != null) {
                    c.cast(f.function(param!!))
                } else {
                    f.function(param!!)
                }

            } else {
                throw FunctionException("Has no this function:$funcName")
            }

        }
        return null
    }

    fun addFunction(function: FunctionWithParamOnly<Any>): FunctionsManager {
        mFunctionWithParamOnly!![function.mFuctionName!!] = function
        return this
    }

    @Throws(FunctionException::class)
    fun <Pararm> invokeFunc(funcName: String, data: Pararm) {
        if (TextUtils.isEmpty(funcName)) {
            return
        }
        if (mFunctionWithParamOnly != null) {
            val f = mFunctionWithParamOnly!![funcName]
            if (f != null) {
                f.function(data!!)

            } else {
                throw FunctionException("Has no this function:$funcName")
            }

        }
    }

    fun addFunction(function: FunctionWithResltOnly<Any>): FunctionsManager {
        mFunctionWithResltOnly!![function.mFuctionName!!] = function
        return this
    }

    @Throws(FunctionException::class)
    fun <Result> invokeFunc(funcName: String, c: Class<Result>?): Any? {
        if (TextUtils.isEmpty(funcName)) {
            return null
        }
        if (null != mFunctionWithResltOnly) {
            val f = mFunctionWithResltOnly!![funcName]
            return if (f != null) {
                if (c != null) {
                    c.cast(f.function())
                } else {
                    f.function()
                }

            } else {
                throw FunctionException("Has no this function:$funcName")
            }

        }
        return null
    }



}