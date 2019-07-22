package com.iliaberlana.myrecipepuppy.domain.exception

sealed class DomainError : Exception() {
    object NoRecipesException : DomainError()
    object NoMoreRecipesException : DomainError()
    object NoInternetConnectionException : DomainError()
    object UnknownException: DomainError()

    object NoExistFavoriteException : DomainError()
    object CantGetRecipeFromDBException : DomainError()
}
