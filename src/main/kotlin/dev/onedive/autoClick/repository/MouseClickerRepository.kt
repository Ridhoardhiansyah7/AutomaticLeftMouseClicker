package dev.onedive.autoClick.repository

interface MouseClickerRepository {

    suspend fun startClicker(delayInMillis : Long)

    fun stopClicker()

}