package org.mathieu.cleanrmapi.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.objects.toDBObject
import org.mathieu.cleanrmapi.data.local.objects.toDetailedModel
import org.mathieu.cleanrmapi.data.local.objects.toPreviewModel
import org.mathieu.cleanrmapi.data.remote.LocationAPI
import org.mathieu.cleanrmapi.domain.character.CharacterRepository
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview


internal class LocationRepositoryImpl(
    private val locationApi: LocationAPI,
    private val locationDAO: LocationDAO,
    private val characterRepository: CharacterRepository
) : LocationRepository {

    override fun getLocations(): Flow<List<Location>> {
        return locationDAO.getLocations().map { locations ->
            locations.map { it.toDetailedModel() }
        }
    }

    override fun getLocationPreviews(): Flow<List<LocationPreview>> {
        return locationDAO.getLocations().map { locations ->
            locations.map { it.toPreviewModel() }
        }
    }

    override suspend fun getLocation(id: Int): Location? {
        val cachedLocation = locationDAO.getLocation(id)

        return if (cachedLocation != null) {
            cachedLocation.toDetailedModel { residentsIds ->
                val characterIds = residentsIds.split(",").mapNotNull { it.toIntOrNull() }
                characterIds.mapNotNull {
                    characterRepository.getCharacterDetailed(it)?.toCharacter()
                }
            }
        } else {
            try {
                val remoteLocation = locationApi.getLocation(id)?.toDBObject()
                if (remoteLocation != null) {
                    locationDAO.insert(remoteLocation)

                    remoteLocation.toDetailedModel { residentsIds ->
                        val characterIds = residentsIds.split(",").mapNotNull { it.toIntOrNull() }
                        characterIds.mapNotNull {
                            characterRepository.getCharacterDetailed(it)?.toCharacter()
                        }
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getLocationPreview(id: Int): LocationPreview? {
        return locationDAO.getLocation(id)?.toPreviewModel()
    }
}