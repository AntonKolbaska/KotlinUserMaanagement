package com.andersen.usermanager.configuration.swagger

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.TYPE

@Target(FUNCTION, TYPE)
@Retention(RUNTIME)
@ApiResponses(
    ApiResponse(responseCode = "200", description = "The request was processed successfully"),
    ApiResponse(responseCode = "401", description = "Indicates that the request requires authentication or the provided credentials are invalid."),
    ApiResponse(responseCode = "500", description = "Internal server error"),
)
@SecurityRequirement(name = "bearerAuth")
annotation class SwaggerService
