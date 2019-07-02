package com.example.pro.logging.loggingsample.controller;

import com.example.pro.logging.loggingsample.dto.AppUserDTO;
import com.example.pro.logging.loggingsample.exception.CustomException;
import com.example.pro.logging.loggingsample.exception.CustomValidateException;
import com.example.pro.logging.loggingsample.model.AuthToken;
import com.example.pro.logging.loggingsample.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app_user")
@CrossOrigin
public class UserController {

    private static final Logger app_log = LoggerFactory.getLogger("app_log");
    private static final Logger logfile = LoggerFactory.getLogger("logfile");

    private final AppUserService appUserService;
    private final CustomValidateException customValidateException;
    private String validateError;


    public UserController(AppUserService appUserService, CustomValidateException customValidateException) {
        this.appUserService = appUserService;
        this.customValidateException = customValidateException;
    }

    @PostMapping("/save")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody AppUserDTO appUserDTO, BindingResult bindingResult) {
        logfile.info("HIT - app_user/save | payload : {}", appUserDTO);
        app_log.info("User save analytics");
        if (bindingResult.hasErrors()) {
            validateError = customValidateException.validationException(bindingResult);
            throw new CustomException(validateError);
        }
        return appUserService.registerUser(appUserDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody AppUserDTO cmsUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateError = customValidateException.validationException(bindingResult);
            throw new CustomException(validateError);
        }
        return appUserService.updateUser(cmsUserDTO);
    }

    @DeleteMapping("/delete/{user_id:.+}")
    public ResponseEntity<?> deleteUser(@PathVariable int user_id) {
        return appUserService.removeUser(user_id);
    }

    @GetMapping("/search/{user_id:.+}")
    public ResponseEntity<?> searchUser(@PathVariable int user_id) {
        return appUserService.searchUser(user_id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AppUserDTO appUserDTO) {
        return appUserService.loginUser(appUserDTO);
    }

    @PostMapping("/getAccessToken")
    public ResponseEntity<?> getaccesstoken(@RequestBody AuthToken token) {
        return appUserService.getRefreshToken(token.getRefresh_token());
    }

}
