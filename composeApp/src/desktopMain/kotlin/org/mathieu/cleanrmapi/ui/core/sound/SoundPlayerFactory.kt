package org.mathieu.cleanrmapi.ui.core.sound

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineEvent

/**
 * Implementation of the `SoundPlayer` interface for desktop platforms.
 *
 * This class is responsible for playing sound effects using the Java Sound API.
 * It retrieves sound resources from the `/sounds` directory and plays them as `.wav` files.
 */
actual class SoundPlayerFactory actual constructor() : SoundPlayer {
    /**
     * Plays a sound based on the provided resource name.
     *
     * The method attempts to load a `.wav` file from the `/sounds` directory in the classpath.
     * If the sound file is found, it is played using a `Clip` from the Java Sound API.
     * The clip is automatically closed when playback is complete.
     *
     * @param resourceName The name of the sound resource to play (without the `.wav` extension).
     */
    override fun playSound(resourceName: String) {
        try {
            // Retrieve the sound file as a resource stream
            val soundURL = SoundPlayerFactory::class.java.getResourceAsStream("/sounds/$resourceName.wav")
            soundURL?.let {
                // Wrap the input stream for buffered reading
                val audioIn = AudioSystem.getAudioInputStream(BufferedInputStream(it))
                val clip = AudioSystem.getClip()

                // Open the audio clip with the input stream
                clip.open(audioIn)

                // Add a listener to close the clip when playback stops
                clip.addLineListener { event ->
                    if (event.type == LineEvent.Type.STOP)
                    {
                        clip.close()
                    }
                }

                // Start playing the sound
                clip.start()
            }
        } catch (e: Exception) {
            // Handle exceptions silently
        }
    }
}