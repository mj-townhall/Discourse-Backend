package com.townhall.discourse.service;

import com.townhall.discourse.dao.UserDataDao;
import com.townhall.discourse.dto.CredentialsDto;
import com.townhall.discourse.dto.RegisterDto;
import com.townhall.discourse.dto.UserDto;
import com.townhall.discourse.entities.UserData;
import com.townhall.discourse.exception.AuthException;
import com.townhall.discourse.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    private  UserDataDao userDataDao;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public UserDto login(CredentialsDto credentialsDto){
        UserData userData= userDataDao.getByEmail(credentialsDto.getEmail());
        if(userData==null){
            throw new AuthException("Unknown user", HttpStatus.NOT_FOUND);
                }
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), userData.getPassword())) {
            return userMapper.toUserDto(userData);
        }
        throw new AuthException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(RegisterDto registerDto){
        boolean oldUserExists= userDataDao.existsByEmail(registerDto.getEmail());
        if(oldUserExists)throw new AuthException("user already exist",HttpStatus.BAD_REQUEST);
        UserData userData=userMapper.signUpToUser(registerDto);
        userData.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));

        UserData savedUserData=userDataDao.save(userData);
        return userMapper.toUserDto(savedUserData);
    }

    public UserDto findByEmail(String login) {
        UserData userData = userDataDao.getByEmail(login);
        if(userData==null)throw new AuthException("Unknown user", HttpStatus.NOT_FOUND);
        return userMapper.toUserDto(userData);
    }

}
