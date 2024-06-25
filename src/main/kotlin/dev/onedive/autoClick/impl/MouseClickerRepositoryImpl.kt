package dev.onedive.autoClick.impl

import dev.onedive.autoClick.repository.MouseClickerRepository
import java.awt.Robot
import java.awt.event.InputEvent
import kotlinx.coroutines.delay

class MouseClickerRepositoryImpl : MouseClickerRepository {

    private var robot : Robot? = null

    var currentTimeIsStart = false

    override suspend fun startClicker(delayInMillis : Long) {
        robot = Robot().apply {
            mousePress(InputEvent.BUTTON1_DOWN_MASK)
            mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
            delay(delayInMillis.toLong())
            currentTimeIsStart = true
        }
    }

    override fun stopClicker() {
        currentTimeIsStart = false
        robot = null
    }


}