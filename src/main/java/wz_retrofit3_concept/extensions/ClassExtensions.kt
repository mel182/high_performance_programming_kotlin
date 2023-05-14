package wz_retrofit3_concept.extensions

fun Class<*>.boxIfPrimitive(): Class<*> = when(this) {
    Boolean::class.java -> this::class.java
    Byte::class.java -> this::class.java
    Character::class.java -> this::class.java
    Double::class.java -> this::class.java
    Float::class.java -> this::class.java
    Integer::class.java -> this::class.java
    Long::class.java -> this::class.java
    Short::class.java -> this::class.java
    else -> this
}