package org.mathieu.cleanrmapi.ui.core.utils

import org.koin.dsl.module
import org.mathieu.cleanrmapi.ui.core.sound.SoundPlayer
import org.mathieu.cleanrmapi.ui.core.sound.SoundPlayerFactory

val modules = module {
    single<SoundPlayer> {
        SoundPlayerFactory()
    }
}