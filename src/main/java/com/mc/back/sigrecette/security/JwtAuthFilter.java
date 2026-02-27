package com.mc.back.sigrecette.security;

import com.mc.back.sigrecette.config.SecurityProperties;
import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.service.IAdmUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IAdmUserService admUserService;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                                 HttpServletResponse response,
                                 FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Allow public paths
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        Long idUser;
        try {
            idUser = admUserService.getUserIdFromTokenString(token);
            AdmUser user = admUserService.findUserById(idUser);

            if (StringUtils.hasText(token) && jwtUtil.validateToken(token, user)) {
                // Authorize request
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    public boolean isPublicPath(String path) {
        return securityProperties.getPublicPaths().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

}
