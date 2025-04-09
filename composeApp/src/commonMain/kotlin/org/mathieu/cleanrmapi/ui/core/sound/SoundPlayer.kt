package org.mathieu.cleanrmapi.ui.core.sound

/**
 * Interface for playing sound effects.
 *
 * This interface defines a method for playing sound resources by their name.
 */
interface SoundPlayer {
    /**
     * Plays a sound based on the provided resource name.
     *
     * @param resourceName The name of the sound resource to play.
     */
    fun playSound(resourceName: String)
}

/**
 * Expected class for creating a platform-specific implementation of `SoundPlayer`.
 *
 * This class is expected to provide the actual implementation of the `SoundPlayer` interface
 * for the target platform.
 */
expect class SoundPlayerFactory() : SoundPlayer