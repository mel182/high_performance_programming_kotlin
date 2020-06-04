package property_delegation.interfaces

import kotlin.reflect.KProperty

interface ReadWriteProperty<in R, T> {
    operator fun getValue(thisRef: R, property: KProperty<*>):T
    operator fun setValue(thisRef: R, property: KProperty<*>, value: T)
}