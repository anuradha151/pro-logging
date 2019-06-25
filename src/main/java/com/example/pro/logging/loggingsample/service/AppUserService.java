package com.example.pro.logging.loggingsample.service;

import com.example.pro.logging.loggingsample.dto.AppUserDTO;
import org.springframework.http.ResponseEntity;

public interface AppUserService {

    ResponseEntity<?> registerUser(AppUserDTO appUserDTO);

    ResponseEntity<?> updateUser(AppUserDTO appUserDTO);

    ResponseEntity<?> removeUser(int user_id);

    ResponseEntity<?> searchUser(int user_id);

    ResponseEntity<?> getRefreshToken(String refresh_token);

    ResponseEntity<?> loginUser(AppUserDTO appUserDTO);

}
