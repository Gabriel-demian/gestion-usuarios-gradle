package com.bci.gestionusuarios.config

import com.bci.gestionusuarios.service.impl.TokenServiceImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import spock.lang.Specification
import spock.lang.Title

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Title("AuthFilter unit tests")
class AuthFilterTest extends Specification {

    def userDetailsService = Mock(UserDetailsService)
    def tokenService = Mock(TokenServiceImpl)
    def filter = new AuthFilter(userDetailsService, tokenService)

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    def "should authenticate user when valid token is provided"() {

        given:
        HttpServletRequest request = Mock(HttpServletRequest)
        HttpServletResponse response = Mock(HttpServletResponse)
        FilterChain filterChain = Mock(FilterChain)
        def jwtToken = "valid.jwt.token"
        def userId = "c195bbf0-4801-4f9f-bcec-545a174e519c"

        def claims = [(TokenServiceImpl.userId): userId]

        request.getHeader(AuthFilter.AUTHORIZATION_HEADER) >> "BEARER $jwtToken"

        tokenService.getClaims(jwtToken) >> claims

        def userDetails = Mock(UserDetails) {
            getAuthorities() >> []
            getUsername() >> "usuario51@example.com"
        }

        when:
        filter.doFilterInternal(request, response, filterChain)

        then:
        1 * userDetailsService.loadUserByUsername(userId) >> userDetails
        1 * filterChain.doFilter(request, response)

        SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken
        SecurityContextHolder.getContext().getAuthentication().getName() == "usuario51@example.com"
    }

    def "should not authenticate user when no token is provided"() {
        given:
        HttpServletRequest request = Mock(HttpServletRequest)
        HttpServletResponse response = Mock(HttpServletResponse)
        FilterChain filterChain = Mock(FilterChain)

        request.getHeader(AuthFilter.AUTHORIZATION_HEADER) >> null

        when:
        filter.doFilterInternal(request, response, filterChain)

        then:
        0 * userDetailsService.loadUserByUsername(_)
        1 * filterChain.doFilter(request, response)
        SecurityContextHolder.getContext().getAuthentication() == null
    }

    def "should not authenticate user when token is invalid"() {
        given:
        HttpServletRequest request = Mock(HttpServletRequest)
        HttpServletResponse response = Mock(HttpServletResponse)
        FilterChain filterChain = Mock(FilterChain)
        def invalidToken = "invalid.jwt.token"

        request.getHeader(AuthFilter.AUTHORIZATION_HEADER) >> "BEARER $invalidToken"

        tokenService.getClaims(invalidToken) >> [:]

        when:
        filter.doFilterInternal(request, response, filterChain)

        then:
        0 * userDetailsService.loadUserByUsername(_)
        1 * filterChain.doFilter(request, response)
        SecurityContextHolder.getContext().getAuthentication() == null
    }

}
