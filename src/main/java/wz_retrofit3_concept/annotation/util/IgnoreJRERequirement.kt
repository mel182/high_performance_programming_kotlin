package wz_retrofit3_concept.annotation.util

import wz_retrofit3_concept.annotation.util.CustomRetention

@CustomRetention(java.lang.annotation.RetentionPolicy.CLASS)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.TYPE,
    AnnotationTarget.CLASS
)
annotation class IgnoreJRERequirement
