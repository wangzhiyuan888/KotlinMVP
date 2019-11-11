package com.wzy.arms.base.struct

abstract class FunctionWithParamOnly<Param>(funNmae: String) : BaseFunction(funNmae) {

    abstract fun function(param: Param)
}