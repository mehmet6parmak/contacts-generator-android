package com.teknolojipiri.contactsgenerator.di

import android.content.Context
import com.teknolojipiri.contactsgenerator.ContactsGenerator
import contacts.core.Contacts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SingletonsModule {

    @Provides
    fun provideContext(): Context = ContactsGenerator.INSTANCE

    @Provides
    fun provideContactsModifier(context: Context): Contacts {
        return Contacts(context)
    }
}