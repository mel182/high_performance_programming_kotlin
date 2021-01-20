package property_delegation.normal_class

import property_delegation.interfaces.ReadWriteProperty
import kotlin.reflect.KProperty

class CustomDelegate<T>: ReadWriteProperty<Any?, T?>
{
    private var value:T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = value
    }
}

class DoubleDelegate: ReadWriteProperty<Any?, Double>
{
    private var value:Double = 0.0

    override fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Double)
    {
        when(value)
        {
            1.0 -> this.value = 12.0
            else -> this.value = 5.0
        }
    }
}