package com.iliaberlana.myrecipepuppy.domain.exception

sealed class DomainError : Exception() {
    object NoRecipesException : DomainError()
    object NoMoreRecipesException : DomainError()
    object NoInternetConnectionException : DomainError()
    object UnknownException: DomainError()
}

sealed class RoomError : Exception() {
    object NoExistFavoriteException : RoomError()
    object CantSaveRecipeInDBException : RoomError()
    object CantGetRecipeFromDBException : RoomError()
}