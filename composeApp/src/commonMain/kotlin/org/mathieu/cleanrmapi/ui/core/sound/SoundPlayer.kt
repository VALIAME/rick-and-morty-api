package org.mathieu.cleanrmapi.ui.core.sound

interface SoundPlayer {
    fun playSound(resourceName: String)
}

expect class SoundPlayerFactory() : SoundPlayer