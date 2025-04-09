package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel

sealed interface LocationDetailsAction {
    /**
     * Action triggered when a character is selected.
     *
     * @property character The selected character.
     */
    data class SelectedCharacter(val character: Character): LocationDetailsAction
}

class LocationDetailsViewModel :
    ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    private val locationRepository: LocationRepository by inject()

    fun init(locationId: Int) {
        fetchData(
            source = { locationRepository.getLocation(locationId) ?: throw IllegalStateException("Location not found") }
        ) {
            onSuccess { location ->
                updateState {
                    LocationDetailsState.Loaded(
                        name = location.name,
                        type = location.type,
                        dimension = location.dimension,
                        residents = location.residents
                    )
                }
            }

            onFailure {
                updateState {
                    LocationDetailsState.Error(message = it.message ?: it.toString())
                }
            }
        }
    }

    fun handleAction(action: LocationDetailsAction) {
        when(action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.character.id.toString()))
        }
    }
}

sealed interface LocationDetailsState {
    data object Loading : LocationDetailsState
    data class Error(val message: String) : LocationDetailsState
    data class Loaded(
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<Character>
    ) : LocationDetailsState
}