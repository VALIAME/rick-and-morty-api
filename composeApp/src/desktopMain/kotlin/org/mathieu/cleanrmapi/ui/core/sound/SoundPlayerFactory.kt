package org.mathieu.cleanrmapi.ui.core.sound

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.FloatControl
import javax.sound.sampled.LineEvent

actual class SoundPlayerFactory actual constructor() : SoundPlayer {
    override fun playSound(resourceName: String) {
        try {
            val soundURL = SoundPlayerFactory::class.java.getResourceAsStream("/sounds/$resourceName.wav")
            println("SoundPlayerFactory: playSound: $resourceName, soundURL: $soundURL")
            soundURL?.let {
                val audioIn = AudioSystem.getAudioInputStream(BufferedInputStream(it))
                val clip = AudioSystem.getClip()
                clip.open(audioIn)


                clip.addLineListener { event ->
                    if (event.type == LineEvent.Type.STOP)
                    {
                        clip.close()
                    }
                }
                clip.start()
            }
        } catch (e: Exception) {
            // Handle exceptions silently
        }
    }
}