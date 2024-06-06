package com.jwt.springsecurity.controller;

import com.jwt.springsecurity.entity.AuthRequest;
import com.jwt.springsecurity.entity.UserInfo;
import com.jwt.springsecurity.service.JwtService;
import com.jwt.springsecurity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/home")
    public String welcome(){
        return "Home page";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @GetMapping("/getUsers")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUser/{id}")
    public UserInfo getUser(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }
}
