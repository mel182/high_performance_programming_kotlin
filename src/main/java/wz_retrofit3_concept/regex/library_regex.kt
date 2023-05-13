package wz_retrofit3_concept.regex

import java.util.regex.Pattern

const val PARAM = "[a-zA-Z][a-zA-Z0-9_-]*"
val PARAM_URL_REGEX = Pattern.compile("\\{($PARAM)\\}")
val PARAM_NAME_REGEX = Pattern.compile(PARAM)