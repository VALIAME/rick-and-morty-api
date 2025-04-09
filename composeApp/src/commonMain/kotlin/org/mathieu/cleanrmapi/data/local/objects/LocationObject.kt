package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

/**
 * Represents a location entity stored in the SQLite database.
 *
 * @property id Unique identifier of the location.
 * @property name Name of the location.
 * @property type The type or category of the location.
 * @property dimension The specific dimension where this location exists.
 * @property residentsIds Comma-separated list of character IDs who reside in this location.
 * @property created Timestamp indicating when the location entity was created.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsIds: String,
    val created: String
)

internal fun LocationResponse.toDBObject() = LocationObject(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls(),
    created = created
)

internal fun LocationObject.toPreviewModel() = LocationPreview(
    id = id,
    name = name
)

internal suspend fun LocationObject.toDetailedModel(
    idsToCharactersConverter: suspend (characterIds: String) -> List<org.mathieu.cleanrmapi.domain.character.models.Character> = { emptyList() }
) = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = idsToCharactersConverter(residentsIds)
)