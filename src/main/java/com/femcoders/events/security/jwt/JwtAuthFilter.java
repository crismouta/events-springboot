package com.femcoders.events.security.jwt;

import com.femcoders.events.services.CustomUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserService customUserService;

    public JwtAuthFilter(JwtService jwtService, CustomUserService customUserService) {
        this.jwtService = jwtService;
        this.customUserService = customUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        if(jwtService.isValidToken(token)) {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails = customUserService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

/*
1.Intercepta cada request (OncePerRequestFilter)
2.Revisa la cabecera Authorization + Bearer
3.Recupera y Valida el token
4.Carga al usuario desde tu base de datos
5.Guarda el usuario en el contexto Spring Security
6.Llama a la cadena de filtros
*/
