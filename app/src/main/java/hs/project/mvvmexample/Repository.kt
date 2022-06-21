package hs.project.mvvmexample

import hs.project.mvvmexample.network.ApiService

class Repository(private val apiService: ApiService) {
    fun getCharacters(page: String) = apiService.fetchCharacters(page)
}