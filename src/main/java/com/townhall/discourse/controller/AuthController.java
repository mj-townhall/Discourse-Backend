package com.townhall.discourse.controller;

import com.townhall.discourse.config.UserAuthenticationProvider;
import com.townhall.discourse.dto.CredentialsDto;
import com.townhall.discourse.dto.RegisterDto;
import com.townhall.discourse.dto.UserDto;
import com.townhall.discourse.exception.AuthException;
import com.townhall.discourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    UserService userService;

    @GetMapping("/tokenStatus")
    public String checkIsTokenStillValid() {
        return "valid";
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto){
        UserDto userDto=null;
        try {
             userDto=userService.register(registerDto);
//        logger.info("adding data");
            userDto.setToken(userAuthenticationProvider.createToken(userDto));
        } catch (AuthException e) {
            logger.warning(e.getMessage());
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto=new UserDto();
        System.out.println(credentialsDto);
        try {
            userDto = userService.login(credentialsDto);
            userDto.setToken(userAuthenticationProvider.createToken(userDto));
            return ResponseEntity.ok(userDto);
        } catch (AuthException e) {
            userDto.setMessage(e.getMessage());
            return new ResponseEntity<>(userDto,HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserDto("Internal server error occurred"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return new ResponseEntity<>("succesfully logged out",HttpStatus.OK);
    }

}
