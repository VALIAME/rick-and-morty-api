package org.mathieu.cleanrmapi.ui.core.sound

import android.content.Context
import android.media.MediaPlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.R

/**
 * Implementation of the `SoundPlayer` interface using Android's `MediaPlayer`.
 *
 * This class is responsible for playing sound effects in the application. It uses
 * dependency injection to obtain the application context and supports a configurable
 * map of sound resource names to their corresponding resource IDs.
 *
 * @constructor Creates an instance of `SoundPlayerFactory`.
 */
actual class SoundPlayerFactory actual constructor(): SoundPlayer, KoinComponent {

    /**
     * The application context, injected via Koin.
     */
    private val context: Context by inject()

    /**
     * The `MediaPlayer` instance used for playing sounds.
     */
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Flag indicating whether sound playback is enabled.
     * This can be made configurable as needed.
     */
    private val enabled = true

    /**
     * A map of sound resource names to their corresponding resource IDs.
     */
    private val soundResourceMap = mapOf(
        "rpg_the_last_sylph" to R.raw.rpg_the_last_sylph,
        "menu_selection_click" to R.raw.menu_selection_click,
    )

    /**
     * Plays a sound based on the provided resource name.
     *
     * If sound playback is disabled or the context is unavailable, the method returns early.
     * If the resource name is not found in the map, a default fallback sound is used.
     *
     * @param resourceName The name of the sound resource to play.
     */
    override fun playSound(resourceName: String) {
        if (!enabled || context == null) return

        try {
            // Clean up the previous MediaPlayer instance if it exists
            mediaPlayer?.release()

            // Normalize the resource name by replacing "__" with " - "
            val normalizedName = resourceName.replace("__", " - ")

            // Retrieve the resource ID from the map, or use a default fallback sound
            val resId = soundResourceMap[normalizedName] ?: run {
                R.raw.rpg_the_last_sylph // Default fallback sound
            }

            // If a valid resource ID is found, create and start the MediaPlayer
            if (resId != 0) {
                mediaPlayer = MediaPlayer.create(context, resId)
                mediaPlayer?.setOnCompletionListener { it.release() }
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            // Handle exceptions silently
            println("SoundPlayerFactory: Exception: ${e.message}")
        }
    }
}