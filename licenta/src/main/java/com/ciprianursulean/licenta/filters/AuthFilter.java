package com.ciprianursulean.licenta.filters;

import com.ciprianursulean.licenta.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String headerAuth = httpServletRequest.getHeader("Authorization");
        if (headerAuth != null) {
            String[] headerAuthSplitted = headerAuth.split("Bearer ");
            if (headerAuthSplitted.length > 1 && headerAuthSplitted[1] != null) {
                String extractedToken = headerAuthSplitted[1];
                System.out.println("token: " + extractedToken);
                try {
                    Claims claims = Jwts.parser().setSigningKey(JwtConfig.API_SECRET_KEY)
                            .parseClaimsJws(extractedToken).getBody();
                    System.out.println("claimed_user_id: " + claims.get("userId").toString());
                    httpServletRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString() ));
                } catch (Exception exception) {
                    System.out.println("exception " + exception.getMessage());
                    httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Invalid or expired token!");
                    return;
                }
            } else {
                httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Token must be of format 'Bearer' [token]!");
                return;
            }
        } else {
            httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "Please provide an authentication token!");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
     }
}
