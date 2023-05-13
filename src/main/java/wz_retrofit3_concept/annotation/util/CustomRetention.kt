package wz_retrofit3_concept.annotation.util

import java.lang.annotation.RetentionPolicy

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class CustomRetention(
    /**
     * Returns the retention policy.
     * @return the retention policy
     */
    val value: RetentionPolicy
)
