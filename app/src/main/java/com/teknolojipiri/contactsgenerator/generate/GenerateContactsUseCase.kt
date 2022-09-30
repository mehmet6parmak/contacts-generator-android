package com.teknolojipiri.contactsgenerator.generate

import android.content.Context
import contacts.async.commitWithContext
import contacts.core.Contacts
import contacts.core.entities.NewRawContact
import contacts.core.entities.PhoneEntity
import contacts.core.log.AndroidLogger
import contacts.core.util.addPhone
import contacts.core.util.setName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenerateContactsUseCase @Inject constructor(private val context: Context) {

    suspend fun generateContacts(prefix: String, rangeStart: String, rangeEnd: String) {
        val result = Contacts(context, logger = AndroidLogger()).insert()
            .allowBlanks(true)
            .rawContacts(buildContacts(prefix, rangeStart, rangeEnd))
            .forAccount(null)
            .commitWithContext()
        println(result)
    }

    private fun buildContacts(prefix: String, rangeStart: String, rangeEnd: String): List<NewRawContact> {
        try {
            val start = rangeStart.toDouble().toLong()
            val end = rangeEnd.toDouble().toLong()

            val result = mutableListOf<NewRawContact>()
            for (suffix in start..end) {
                val number = "$prefix${suffix}"
                val name = "${getRandomString(5)} ${getRandomString(5)}"

                result.add(buildNewContact(name, number))
            }
            return result
        } catch (numberFormatException: NumberFormatException) {
            throw ContactGenerationError.InvalidInputs
        }
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun buildNewContact(name: String, phone: String): NewRawContact {
        return NewRawContact().apply {
            setName { givenName = name }
            addPhone {
                type = PhoneEntity.Type.MOBILE
                number = phone
            }
        }
    }

    sealed class ContactGenerationError : RuntimeException() {
        object InvalidInputs : ContactGenerationError()
        object InsertionFailed : ContactGenerationError()
    }
}