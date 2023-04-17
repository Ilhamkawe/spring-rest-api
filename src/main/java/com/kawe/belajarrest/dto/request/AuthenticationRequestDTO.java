package com.kawe.belajarrest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequestDTO {
    
    private String username; 
    private String password;

}
