package com.example.zbesp.network

import kotlinx.coroutines.flow.Flow

interface StatusObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Unknown, Lost, Mobile, WiFi
    }
}