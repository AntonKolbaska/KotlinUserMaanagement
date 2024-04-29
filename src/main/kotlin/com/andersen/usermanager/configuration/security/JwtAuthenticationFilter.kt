package com.andersen.usermanager.configuration.security

import com.andersen.usermanager.service.impl.JwtServiceImpl
import com.andersen.usermanager.util.ConstantsUtil.TOKEN_BEGIN_INDEX
import com.andersen.usermanager.util.ConstantsUtil.TOKEN_IDENTIFIER
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter


@Configuration
class JwtAuthenticationFilter(
    private val jwtServiceImpl: JwtServiceImpl,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val jwt: String
        val email: String?

        if (authHeader == null || !authHeader.startsWith(TOKEN_IDENTIFIER)) {
            filterChain.doFilter(request, response)
            return
        }

        jwt = authHeader.substring(TOKEN_BEGIN_INDEX)
        email = jwtServiceImpl.extractEmail(jwt)

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = userDetailsService.loadUserByUsername(email)
            if (jwtServiceImpl.isTokenValid(jwt, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
            }
        }

        filterChain.doFilter(request, response)
    }
}