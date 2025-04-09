package org.mathieu.cleanrmapi.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.remote.responses.PaginatedResponse
import org.mathieu.cleanrmapi.data.validators.IdListValidator
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds

internal class LocationAPI(private val client: HttpClient) {

    /**
     * Fetches a list of locations from the API.
     *
     * If the page parameter is not provided, it defaults to fetching the first page.
     *
     * @param page The page number to fetch. If null, the first page is fetched by default.
     * @return A paginated response containing a list of [LocationResponse] for the specified page.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocations(page: Int?): PaginatedResponse<LocationResponse> = client
        .get("location/") {
            if (page != null)
                url {
                    parameter("page", page)
                }
        }
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Fetches the details of a location with the given ID from the service.
     *
     * @param id The unique identifier of the location to retrieve.
     * @return The [LocationResponse] representing the details of the location.
     * @throws HttpException if the request fails or if the status code is not [HttpStatusCode.OK].
     */
    suspend fun getLocation(id: Int): LocationResponse? = client
        .get("location/$id")
        .accept(HttpStatusCode.OK)
        .body()

    /**
     * Retrieves multiple locations by their IDs.
     *
     * This function accepts a list of location IDs (`ids`) and makes an API call to fetch the corresponding
     * locations. The API expects a string of IDs separated by commas, such as "/location/1,2,3".
     *
     * @param ids A list of locations IDs to be retrieved.
     * @return A paginated response containing the requested locations as `EpisodeResponse`.
     *
     * @throws Exception if the request fails or the response status is not `HttpStatusCode.OK`.
     */
    suspend fun getLocationsFromIds(@MustBeCommaSeparatedIds ids: String): List<LocationResponse> {

        IdListValidator.assertValid(ids)

        return client
            .get("location/$ids")
            .accept(HttpStatusCode.OK)
            .body()
    }


}