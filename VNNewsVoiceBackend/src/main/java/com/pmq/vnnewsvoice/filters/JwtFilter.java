package com.pmq.vnnewsvoice.filters;

import com.pmq.vnnewsvoice.service.UserDetailService;
import com.pmq.vnnewsvoice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailService userDetailService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if(httpServletRequest.getRequestURI().startsWith(String.format("%s/api/secure", httpServletRequest.getContextPath())) == true){
            String header = httpServletRequest.getHeader("Authorization");

            if(header == null || !header.startsWith("Bearer ")){
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
            }

            else{
                String token = header.substring(7);
                try{
                    if(jwtUtils.validateJwtToken(token)){
                        String username = jwtUtils.getUserNameFromJwtToken(token);
                        if(username != null){
                            UserDetails userDetails = userDetailService.loadUserByUsername(username);

                            httpServletRequest.setAttribute("username", username);
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Token không hợp lệ hoặc hết hạn");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
