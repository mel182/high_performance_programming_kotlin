package kotlin_1_6.whenStatementChanges

// An exhaustive when statement contains branches for all possible types or values of its subject,
// or for some types plus an else branch. It covers all possible cases, making your code safer.

// We will soon prohibit non-exhaustive when statements to make the behavior consistent with when
// expressions.
// To ensure smooth migration, Kotlin 1.6.0 reports warnings about non-exhaustive when
// statements with an enum, sealed, or Boolean subject. These warnings will become errors in future releases.

sealed class Contact {
    data class PhoneCall(val number: String) : Contact()
    data class TextMessage(val number: String) : Contact()
}

//fun Contact.messageCost(): Int =
//        when(this) { // Error: 'When' expression must be exhaustive
//            is Contact.PhoneCall -> 42
//        }

fun sendMessage(contact: Contact, message: String) {
    // Starting with 1.6.0

    // Warning: Non exhaustive 'when' statements on Boolean will be prohibited
    // in 1.7, add 'false' branch or 'else' branch instead
    when(message.isEmpty()) {
        true -> return
    }

    // Warning: non exhaustive 'when' statements on sealed class/interface will
    // be prohibited in 1.7, add 'is TextMessage' branch or 'else' branch instead
    when(contact) {
        is Contact.PhoneCall -> TODO()
    }
}