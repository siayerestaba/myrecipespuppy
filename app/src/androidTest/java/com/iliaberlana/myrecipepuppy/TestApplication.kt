package com.iliaberlana.myrecipepuppy

class TestApplication : RecipePuppyApp() {
    private var apiUrlTest: String = "http://127.0.0.1:8080"

    override fun getApiUrl(): String = apiUrlTest

    override fun setApiUrl(apiUrl: String) {
        apiUrlTest = apiUrl
    }
}