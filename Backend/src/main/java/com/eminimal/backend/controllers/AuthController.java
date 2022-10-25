package com.eminimal.backend.controllers;

import com.eminimal.backend.models.users.Users;
import com.eminimal.backend.models.users.UsersToken;
import com.eminimal.backend.services.impl.users.UserAuthServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

   @Autowired
   private UserAuthServiceImpl auth;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<UsersToken> authenticateUser(@Valid @RequestBody Users users) throws Exception {
//        Users users = modelMapper.map(usersDto, Users.class);
        return new ResponseEntity<>(auth.login(users), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users users) throws Exception {
//        Users users = modelMapper.map(usersDto, Users.class);
        return ResponseEntity.ok().body(auth.register(users));
    }
}
