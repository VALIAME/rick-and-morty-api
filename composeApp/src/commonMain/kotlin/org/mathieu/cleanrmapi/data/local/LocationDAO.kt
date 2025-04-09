package org.mathieu.cleanrmapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.mathieu.cleanrmapi.data.local.objects.LocationObject

/**
 * Data Access Object (DAO) for managing location-related database operations.
 *
 * This interface provides methods for querying, inserting, and saving location data
 * in the local database. It uses Room annotations to define SQL queries and operations.
 */
@Dao
interface LocationDAO {
    /**
     * Retrieves all locations from the database.
     *
     * @return A [Flow] emitting a list of [LocationObject] instances.
     */
    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE}")
    fun getLocations(): Flow<List<LocationObject>>

    /**
     * Retrieves a specific location by its ID.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return A [LocationObject] instance representing the location, or null if not found.
     */
    @Query("SELECT * FROM ${RMDatabase.LOCATION_TABLE} WHERE id = :id")
    suspend fun getLocation(id: Int): LocationObject?

    /**
     * Retrieves a list of locations by their IDs.
     *
     * @param ids A comma-separated string of location IDs to retrieve.
     * @return A [Flow] emitting a list of [LocationObject] instances matching the provided IDs.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocations(locations: List<LocationObject>)

    /**
     * Inserts a single location into the database.
     *
     * @param location The [LocationObject] instance to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationObject)
}