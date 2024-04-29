package com.andersen.usermanager.service

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails

interface JwtService {
    fun extractEmail(token: String): String
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T
    fun generateToken(userDetails: UserDetails): String
    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
}