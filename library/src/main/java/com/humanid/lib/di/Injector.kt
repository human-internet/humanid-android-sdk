package com.humanid.lib.di

import com.humanid.lib.data.DataStore
import com.humanid.lib.data.Repository
import com.humanid.lib.domain.Interactor
import com.humanid.lib.domain.UseCase

object Injector {
    fun getRepository(): Repository = DataStore.getInstance()
    
    fun getUseCase(): UseCase = Interactor(getRepository())
}