package com.kawe.belajarrest.services.User;

import com.kawe.belajarrest.entity.Auth;

interface UserService {

    Auth findByID(int id);

    Auth login(String username, String password);

    
}
