package wz_retrofit3_concept.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import wz_retrofit3_concept.Retrofit3
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


interface Converter<F,T> {

    @Throws(IOException::class)
    fun convert(value: F): T?

    /**
     * Creates [Converter] instances based on a type and target usage.
     */
    abstract class Factory {

        /**
         * Returns a Converter for converting an HTTP response body to type, or null if type cannot be handled by this factory.
         * This is used to create converters for response types such as SimpleResponse from a Call<SimpleResponse> declaration.
         */
        open fun responseBodyConverter(type: Type, annotations: Array<Annotation>?, retrofit: Retrofit3): Converter<ResponseBody, *>? = null

        /**
         * Returns a Converter for converting type to an HTTP request body, or null if type cannot be handled by this factory.
         * This is used to create converters for types specified by @Body, @Part, and @PartMap values.
         */
        open fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>?, methodAnnotations: Array<Annotation>?, retrofit: Retrofit3): Converter<*, RequestBody>? = null

        /**
         * Returns a Converter for converting type to a String, or null if type cannot be handled by this factory.
         * This is used to create converters for types specified by @Field, @FieldMap values, @Header, @HeaderMap, @Path, @Query, and @QueryMap values.
         */
        open fun stringConverter(type: Type, annotations: Array<Annotation>?, retrofit: Retrofit3): Converter<*, String>? = null

        /**
         * Extract the upper bound of the generic parameter at index from type.
         * For example, index 1 of Map<String, ? extends Runnable> returns Runnable.
         */
        protected open fun getParameterUpperBound(index: Int, type: ParameterizedType): Type? = wz_retrofit3_concept.getParameterUpperBound(index, type)

        /**
         * Extract the raw class type from type.
         * For example, the type representing List<? extends Runnable> returns List.class.
         */
        protected open fun getRawType(type: Type?): Class<*>? = wz_retrofit3_concept.getRawType(type)

    }

}