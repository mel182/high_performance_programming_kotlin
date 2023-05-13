package wz_retrofit3_concept.annotation.internal

import java.lang.annotation.ElementType


@TypeQualifierDefault([ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER])
@Retention(AnnotationRetention.RUNTIME)
annotation class EverythingIsNonNull
