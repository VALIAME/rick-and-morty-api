package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mathieu.cleanrmapi.ui.core.sound.SoundPlayerFactory
import org.mathieu.cleanrmapi.ui.core.theme.OnBackgroundColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

/**
 * Composable function for displaying an icon with text.
 *
 * This function renders an icon and a text label, either in a vertical or horizontal orientation.
 * It supports customization of colors, background, click behavior, and optional sound effects.
 *
 * @param modifier The modifier to be applied to the composable.
 * @param orientation The orientation of the layout, either vertical or horizontal.
 * @param imageVector The vector image to display as the icon.
 * @param text The text label to display alongside the icon.
 * @param backgroundColor The background color of the composable.
 * @param color The color of the icon and text.
 * @param isClickable Whether the composable is clickable.
 * @param onClick The callback to invoke when the composable is clicked.
 * @param soundIsActive Whether we want a sound effect on click.
 * @param sound The name of the sound resource to play on click (without extension).
 */
@Composable
fun IconWithImage(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    imageVector: ImageVector,
    text: String,
    backgroundColor: Color = SurfaceColor,
    color: Color = OnBackgroundColor,
    isClickable: Boolean = false,
    onClick: () -> Unit = {},
    soundIsActive: Boolean = false,
    sound: String = "menu_selection_click", // Resource name without extension
) {
    val soundPlayer = SoundPlayerFactory()

    // Create a modifier that includes clickable if isClickable is true
    val actualModifier = if (isClickable) {
        modifier.clickable {
            onClick()
            // Play sound if both conditions are met
            if (soundIsActive && sound.isNotEmpty()) {
                soundPlayer.playSound(sound)
            }
        }
    } else {
        modifier
    }

    when (orientation) {

        Orientation.Vertical ->
            Column(
                modifier = actualModifier
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = { Content(color, imageVector, text) }
            )

        Orientation.Horizontal ->
            Row(
                modifier = actualModifier,
                verticalAlignment = Alignment.CenterVertically,
                content = { Content(color, imageVector, text) }
            )

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    color: Color,
    imageVector: ImageVector,
    text: String
) {
    Image(
        modifier = Modifier,
        imageVector = imageVector,
        contentDescription = "",
        colorFilter = ColorFilter.tint(color)
    )

    Text(
        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
        text = text,
        color = color,
        fontSize = 14.sp
    )

}