package wz_retrofit3_concept.delegates

import wz_retrofit3_concept.parameter.ParameterHandler

data class ParameterParserResult(val parameterHandlers: ArrayList<ParameterHandler<*>>,val result: ParameterHandler<*>? = null, val gotUrl:Boolean = false, val isSuspendableFunction:Boolean = false)
