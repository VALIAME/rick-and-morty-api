package org.mathieu.cleanrmapi.ui.core.utils

import org.koin.dsl.module
import org.mathieu.cleanrmapi.ui.core.sound.SoundPlayer
import org.mathieu.cleanrmapi.ui.core.sound.SoundPlayerFactory

/**
 * Defines a Koin module for dependency injection.
 *
 * This module provides a singleton instance of `SoundPlayer` using the `SoundPlayerFactory`.
 * The `SoundPlayer` is responsible for handling sound-related functionality in the application.
 */
val modules = module {
    single<SoundPlayer> {
        SoundPlayerFactory()
    }
}