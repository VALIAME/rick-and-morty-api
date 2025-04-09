package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->
        LaunchedEffect(key1 = Unit) {
            viewModel.init(locationId = id)
        }

        Content(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
private fun Content(
    state: LocationDetailsState = LocationDetailsState.Loading,
    onAction: (LocationDetailsAction) -> Unit = { },
    onClickBack: () -> Unit = { }
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(),
    contentAlignment = Alignment.Center
) {
    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart)
            .zIndex(1f),
        onClick = onClickBack
    )

    Crossfade(targetState = state) {
        when (it) {
            is LocationDetailsState.Error -> ErrorView(error = it.message)
            is LocationDetailsState.Loaded -> LocationContent(
                state = it,
                onAction = onAction
            )
            LocationDetailsState.Loading -> {
                /** TODO: Could display a Loading Animation */
            }
        }
    }
}

@Composable
private fun ErrorView(error: String) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = error,
        textAlign = TextAlign.Center,
        color = PrimaryColor,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 36.sp
    )
}

private object LocationContentImpl {
    @Composable
    operator fun invoke(
        state: LocationDetailsState.Loaded,
        onAction: (LocationDetailsAction) -> Unit
    ) {
        var offsetY by remember { mutableFloatStateOf(0f) }

        Column(modifier = Modifier.fillMaxSize()) {
            Header(state = state, offsetY = offsetY)

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Residents",
                color = PrimaryColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(state.residents) { character ->
                    Box(modifier = Modifier.onGloballyPositioned {
                        if (offsetY == 0f) {
                            offsetY = it.positionInParent().y
                        }
                    }) {
                        CharacterCard(
                            modifier = Modifier.clickable {
                                onAction(LocationDetailsAction.SelectedCharacter(character))
                            },
                            character = character
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Header(
        state: LocationDetailsState.Loaded,
        offsetY: Float
    ) {
        val density = LocalDensity.current
        val additionalHeight: Dp = with(density) { offsetY.toDp() / 2 }
        val animatedHeight by animateDpAsState(targetValue = 180.dp + additionalHeight)

        Box(
            modifier = Modifier
                .height(animatedHeight)
                .fillMaxWidth()
                .background(SurfaceColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .background(SurfaceColor.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .padding(8.dp),
                    text = state.name,
                    fontSize = 21.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    color = PrimaryColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                LocationInfo(state = state)
            }
        }
    }

    @Composable
    private fun LocationInfo(state: LocationDetailsState.Loaded) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            InfoItem(title = "Type", value = state.type)
            Spacer(modifier = Modifier.width(24.dp))
            InfoItem(title = "Dimension", value = state.dimension)
        }
    }

    @Composable
    private fun InfoItem(title: String, value: String) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                color = PrimaryColor,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Text(
                text = value,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LocationContent(
    state: LocationDetailsState.Loaded,
    onAction: (LocationDetailsAction) -> Unit
) = LocationContentImpl(state, onAction)