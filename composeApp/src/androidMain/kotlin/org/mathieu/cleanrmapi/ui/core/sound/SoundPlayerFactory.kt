package org.mathieu.cleanrmapi.ui.core.sound

import android.content.Context
import android.media.MediaPlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.R

actual class SoundPlayerFactory actual constructor(): SoundPlayer, KoinComponent {

    private val context: Context by inject()
    private var mediaPlayer: MediaPlayer? = null
    private val enabled = true // You can make this configurable

    // Map of sound names to their resource IDs
    private val soundResourceMap = mapOf(
        "rpg_the_last_sylph" to R.raw.rpg_the_last_sylph,
        "menu_selection_click" to R.raw.menu_selection_click,
    )

    override fun playSound(resourceName: String) {
        println("SoundPlayerFactory: playSound: $resourceName")
        if (!enabled || context == null) return

        try {
            // Clean up previous player if exists
            mediaPlayer?.release()

            val normalizedName = resourceName.replace("__", " - ")

            // Get resource ID
            val resId = soundResourceMap[normalizedName] ?: run {
                println("Sound not found in map: $normalizedName")
                R.raw.rpg_the_last_sylph // Default fallback sound
            }


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