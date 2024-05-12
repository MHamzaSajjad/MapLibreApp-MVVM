package com.mhamzasajjad.mapapp.data.api

object ApiEndPoints {
    const val userProfileMockBaseUrl = "https://mock-server.trailsoffroadapi.com/"
    const val userProfileEndPoint = "accounts/user/"

    // API key normally would not be stored in code
    // but rather outside, following security practices
    // However, for demonstration in this project, keeping it here
    const val stadiaMapsApiKey = "925a0753-e46a-4917-82bb-dced5aacfa88"
    const val stadiaMapsStyleUrl = "https://tiles.stadiamaps.com/styles/styleId.json?api_key=$stadiaMapsApiKey"
}