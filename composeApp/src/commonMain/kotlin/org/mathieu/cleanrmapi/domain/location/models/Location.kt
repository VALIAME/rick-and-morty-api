package org.mathieu.cleanrmapi.domain.location.models

import org.mathieu.cleanrmapi.domain.character.models.Character


/**
 * Represents a specific location within a universe or dimension.
 * Extends [LocationPreview] with additional location details.
 *
 * @property id The unique identifier for the location (inherited from [LocationPreview]).
 * @property name The name of the location (inherited from [LocationPreview]).
 * @property type The type or category of the location.
 * @property dimension The specific dimension or universe where this location exists.
 * @property residents A list of [Character] who have been known to reside or appear in this location.
 */
data class Location(
    override val id: Int,
    override val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>
) : LocationPreview(id, name)