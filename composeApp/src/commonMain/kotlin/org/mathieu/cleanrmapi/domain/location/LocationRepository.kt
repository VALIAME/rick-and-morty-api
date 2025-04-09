package org.mathieu.cleanrmapi.domain.location

import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

/**
 * Repository interface for managing location-related data.
 *
 * This interface defines methods for retrieving locations and their previews,
 * as well as fetching specific location details by ID.
 */
interface LocationRepository {
    /**
     * Retrieves a flow of all locations.
     *
     * @return A [Flow] emitting a list of [Location] objects.
     */
    fun getLocations(): Flow<List<Location>>

    /**
     * Retrieves a flow of all location previews.
     *
     * @return A [Flow] emitting a list of [LocationPreview] objects.
     */
    fun getLocationPreviews(): Flow<List<LocationPreview>>

    /**
     * Retrieves a specific location by its ID.
     *
     * @param id The ID of the location to retrieve.
     * @return A [Location] object if found, or null if not found.
     */
    suspend fun getLocation(id: Int): Location?

    /**
     * Retrieves a specific location preview by its ID.
     *
     * @param id The ID of the location preview to retrieve.
     * @return A [LocationPreview] object if found, or null if not found.
     */
    suspend fun getLocationPreview(id: Int): LocationPreview?
}