package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.screens.characters.CharactersContracts.UiAction

sealed interface CharacterDetailsAction {
    data class SelectedEpisode(val episode: Episode): CharacterDetailsAction
    /**
     * Represents an action triggered when a location is selected.
     *
     * @property location The preview of the selected location.
     */
    data class SelectedLocation(val location: LocationPreview): CharacterDetailsAction
}

class CharacterDetailsViewModel :
    ViewModel<CharacterDetailsState>(CharacterDetailsState.Loading) {

    private val characterRepository: CharacterRepository by inject()

    fun init(characterId: Int) {

        fetchData(
            source = { characterRepository.getCharacterDetailed(id = characterId) }
        ) {

            onSuccess { details ->
                updateState {
                    CharacterDetailsState.Loaded(
                        name = details.name,
                        avatarUrl = details.avatarUrl,
                        episodes = details.episodes,
                        status = details.status,
                        gender = details.gender,
                        origin = details.origin,
                        location = details.location
                    )
                }
            }

            onFailure {
                updateState {
                    CharacterDetailsState.Error(message = it.message ?: it.toString())
                }
            }


        }


    }

    /**
     * Handles user actions related to character details.
     *
     * This function processes the provided action and performs the corresponding navigation
     * or updates based on the type of action. Supported actions include selecting an episode
     * or a location.
     *
     * @param action The action to handle, which can be one of the following:
     * - `CharacterDetailsAction.SelectedEpisode`: Triggers navigation to the episode details screen.
     * - `CharacterDetailsAction.SelectedLocation`: Triggers navigation to the location details screen.
     */
    fun handleAction(action: CharacterDetailsAction) {
        when(action) {
            is CharacterDetailsAction.SelectedEpisode ->
                sendEvent(Destination.EpisodeDetails(action.episode.id.toString()))
            is CharacterDetailsAction.SelectedLocation -> selectedLocation(action.location)
        }
    }

    /**
     * Navigates to the location details screen if the location has a valid ID.
     *
     * This function checks if the provided `LocationPreview` object contains a non-null ID.
     * If the ID is present, it triggers navigation to the location details screen using the ID.
     *
     * @param location The `LocationPreview` object representing the location to navigate to.
     */
    private fun selectedLocation(location: LocationPreview) {
        location.id?.let {
            sendEvent(Destination.LocationDetails(it.toString()))
        }
    }
}

sealed interface CharacterDetailsState {
    object Loading : CharacterDetailsState

    data class Error(val message: String) : CharacterDetailsState

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<Episode>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: LocationPreview,
        val location: LocationPreview,
    ) : CharacterDetailsState

}