package property_delegation.singleton

import property_delegation.interfaces.ReadOnlyProperty
import kotlin.reflect.KProperty

object SingletonDelegate: ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return property.name
    }
}