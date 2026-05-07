package com.rice.babchuk.domain.auth.client

import com.rice.babchuk.domain.auth.dto.DauthUser
import com.rice.babchuk.domain.auth.dto.DauthUserResponse
import com.rice.babchuk.domain.auth.error.AuthError
import com.rice.babchuk.global.error.CustomException
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient

@Component
class DauthClient {
    private val client: RestClient = RestClient.builder()
        .baseUrl("https://dodam-api.b1nd.com")
        .build()

    fun fetchUser(accessToken: String): DauthUser {
        try {
            val response = client.get()
                .uri("/user/me")
                .header("Authorization", "Bearer $accessToken")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, res ->
                    throw HttpClientErrorException(res.statusCode)
                }
                .body(DauthUserResponse::class.java)
                ?: throw CustomException(AuthError.DAUTH_SERVER_ERROR)

            return response.data ?: throw CustomException(AuthError.DAUTH_SERVER_ERROR)
        } catch (e: HttpClientErrorException.Unauthorized) {
            throw CustomException(AuthError.INVALID_DAUTH_TOKEN)
        } catch (e: HttpClientErrorException) {
            throw CustomException(AuthError.INVALID_DAUTH_TOKEN)
        } catch (e: CustomException) {
            throw e
        } catch (e: Exception) {
            throw CustomException(AuthError.DAUTH_SERVER_ERROR)
        }
    }
}
