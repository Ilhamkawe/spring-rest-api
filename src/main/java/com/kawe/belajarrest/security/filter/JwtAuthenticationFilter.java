package com.kawe.belajarrest.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kawe.belajarrest.services.Authentication.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //*define jwtService & UserDetailService
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
            //* get header
                final String authHeader = request.getHeader("Authorization");

            //* inisiasi jwttoken dan username 
                String jwtToken = null; 
                String username = null;

            //* cek apakah token kosong, atau tidak mengandung keyword bearer
                // ! v1 
                // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                //     // * lanjutkan proses filter 
                //     filterChain.doFilter(request, response);
                //     return;
                // }    
                
                // ! v2 
                if (authHeader != null && authHeader.startsWith("Bearer ")){
                    //* isi var jwtToken dengan token yang sudah dikirim
                    jwtToken = authHeader.substring(7);
            
                    //* ekstrak username  
                    username = jwtService.ExtractUsername(jwtToken);


                    System.out.println("test");
                }
            
            //* cek apakah usernamme null dan security konteks null 
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    //* ambil data 
                    UserDetails userDetail = this.userDetailsService.loadUserByUsername(username);
                    
                        if (jwtService.IsTokenValid(jwtToken, userDetail)){
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetail,
                                 null,
                                  userDetail.getAuthorities()
                                  );

                            authToken.setDetails(
                                new WebAuthenticationDetails(request)
                                );
                            
                            //* set token ke security context holder
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                        
                    }
                    filterChain.doFilter(request, response);
            }
}
