package org.mathieu.cleanrmapi.domain.location

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

interface LocationRepository {
    fun getLocations(): Flow<List<Location>>
    fun getLocationPreviews(): Flow<List<LocationPreview>>
    suspend fun getLocation(id: Int): Location?
    suspend fun getLocationPreview(id: Int): LocationPreview?
}