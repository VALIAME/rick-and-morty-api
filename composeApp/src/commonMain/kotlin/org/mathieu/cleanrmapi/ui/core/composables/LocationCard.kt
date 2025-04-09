package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import org.mathieu.cleanrmapi.ui.core.theme.OnBackgroundColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

/**
 * Composable function for displaying a location card.
 *
 * This function renders a card with an icon and text, supporting customization of orientation,
 * colors, click behavior, and optional sound effects. It acts as a wrapper around the `IconWithImage` composable.
 *
 * @param modifier The modifier to be applied to the composable.
 * @param orientation The orientation of the layout, either vertical or horizontal. Defaults to vertical.
 * @param imageVector The vector image to display as the icon.
 * @param text The text label to display alongside the icon.
 * @param backgroundColor The background color of the card. Defaults to `SurfaceColor`.
 * @param color The color of the icon and text. Defaults to `OnBackgroundColor`.
 * @param isClickable Whether the card is clickable. Defaults to false.
 * @param onClick The callback to invoke when the card is clicked. Defaults to an empty lambda.
 * @param soundIsActive Whether a sound effect should play on click. Defaults to false.
 * @param sound The name of the sound resource to play on click (without extension). Defaults to "menu_selection_click".
 */
@Composable
fun LocationCard(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    imageVector: ImageVector,
    text: String,
    backgroundColor: Color = SurfaceColor,
    color: Color = OnBackgroundColor,
    isClickable: Boolean = false,
    onClick: () -> Unit = {},
    soundIsActive: Boolean = false,
    sound: String = "menu_selection_click",
) {
    IconWithImage(
        modifier = modifier,
        orientation = orientation,
        imageVector = imageVector,
        text = text,
        backgroundColor = backgroundColor,
        color = color,
        isClickable = isClickable,
        onClick = onClick,
        soundIsActive = soundIsActive,
        sound = sound,
    )
}