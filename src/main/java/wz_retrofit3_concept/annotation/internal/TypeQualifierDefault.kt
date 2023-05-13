package wz_retrofit3_concept.annotation.internal

import java.lang.annotation.ElementType

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TypeQualifierDefault(
    val value: Array<ElementType> = []
)
