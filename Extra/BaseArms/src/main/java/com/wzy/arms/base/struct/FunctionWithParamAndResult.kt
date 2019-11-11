package com.wzy.arms.base.struct


abstract class FunctionWithParamAndResult<Result, Param>(funName: String) : BaseFunction(funName) {

    abstract fun function(param: Param): Result
}