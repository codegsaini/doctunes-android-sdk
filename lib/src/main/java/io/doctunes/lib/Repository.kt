package io.doctunes.lib

import io.doctunes.lib.interfaces.Repository

class Repository(
    private val service: Service
): Repository {

    override suspend fun syncStrings() {
        service.syncString()
    }

}