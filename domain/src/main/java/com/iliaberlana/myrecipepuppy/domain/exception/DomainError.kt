package com.iliaberlana.myrecipepuppy.domain.exception

sealed class DomainError : Exception() {
    object NoMoreRecipesException : DomainError()
    object IncorrectIngredientException : DomainError()

    object NoExistFavoriteException : DomainError()
    object CantSaveRecipeInDBException : DomainError()
    object CantGetRecipeFromDBException : DomainError()

    object NoInternetConnectionException : DomainError()
    object UnknownException: DomainError()
}