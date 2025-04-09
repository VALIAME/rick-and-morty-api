package org.mathieu.cleanrmapi.domain.location.models

/**
 * Represents a preview of a location with basic information.
 *
 * @property id The unique identifier for the location.
 * @property name The name of the location.
 */
open class LocationPreview(
    open val id: Int,
    open val name: String
)