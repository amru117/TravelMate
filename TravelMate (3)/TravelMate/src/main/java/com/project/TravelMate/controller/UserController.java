package com.project.TravelMate.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.TravelMate.model.User;
import com.project.TravelMate.service.OTPService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private OTPService otpService;

    // Register a user and send OTP
    
    @PostMapping("/register") // Define the endpoint for registration
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        // Implement your user registration logic here
        otpService.registerUser(user); // Example method to register user
        otpService.sendOtp(user.getPhoneNo()); // Example method to send OTP

        return ResponseEntity.ok("User registered. OTP sent.");
    }

    // Verify OTP
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNo, @RequestParam int otp) {
        Optional<User> verifiedUser = otpService.verifyUser(phoneNo, otp);

        if (verifiedUser.isPresent()) {
            return ResponseEntity.ok("User successfully verified.");
        }

        return ResponseEntity.badRequest().body("Invalid OTP or phone number.");
    }
}
