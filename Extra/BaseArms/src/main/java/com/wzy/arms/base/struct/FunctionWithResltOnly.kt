package com.wzy.arms.base.struct


abstract class FunctionWithResltOnly<Result>(funNmae: String) : BaseFunction(funNmae) {

    abstract fun function(): Result
}