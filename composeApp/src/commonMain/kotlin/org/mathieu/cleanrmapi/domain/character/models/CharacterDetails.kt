package org.mathieu.cleanrmapi.domain.character.models

import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview

/**
 * Represents detailed information about a character.
 *
 * @property id Unique identifier for the character.
 * @property name Name of the character.
 * @property status Current status of the character.
 * @property species Biological species of the character.
 * @property type The type or subspecies of the character.
 * @property gender Gender identity of the character.
 * @property origin The origin location of the character.
 * @property location The last known location of the character.
 * @property avatarUrl URL pointing to the character's image.
 * @property episodes List of episodes in which the character appears.
 */
data class CharacterDetails(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: CharacterGender,
    val origin: LocationPreview,
    val location: LocationPreview,
    val avatarUrl: String,
    val episodes: List<Episode>
){
    /**
     * Converts a detailed character to its simplified representation.
     *
     * @return A simplified [Character] model.
     */
    fun toCharacter(): Character = Character(
        id = id,
        name = name,
        species = species,
        type = type,
        avatarUrl = avatarUrl
    )
}

/**
 * Describes the current state or condition of a character.
 */
enum class CharacterStatus {
    Alive, Dead, Unknown
}

/**
 * Represents the gender classification of a character.
 */
enum class CharacterGender {
    Female, Male, Genderless, Unknown
}
