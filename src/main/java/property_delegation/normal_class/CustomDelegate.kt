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