package com.bci.gestionusuarios.config;

import com.bci.gestionusuarios.service.impl.TokenServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private static final String AUTHORIZATION_HEADER="Authorization";

    private static final String AUTHORIZATION_KEY="BEARER ";

    private TokenServiceImpl tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //is someone already authenticated?
        if(SecurityContextHolder.getContext().getAuthentication() == null) {

            //check if the request has an  authorization header to further check if an
            final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

            if(authorizationHeader != null && authorizationHeader.toUpperCase().startsWith(AUTHORIZATION_KEY)) {
                String jwt = authorizationHeader.substring(AUTHORIZATION_KEY.length());
                Map<String, Object > claims= tokenService.getClaims(jwt);

                Object userId = claims.get(TokenServiceImpl.userId);

                if (userId != null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId.toString());

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
