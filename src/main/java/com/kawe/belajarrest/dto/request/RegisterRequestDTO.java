package com.kawe.belajarrest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data 
@Builder 
@AllArgsConstructor
// @NoArgsConstructor
public class RegisterRequestDTO {

    public String username; 

    public String password; 

    public String domisili; 

    public String noTelp; 

    public String  Role;
    
}
