package com.project.TravelMate.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.TravelMate.model.User;
import com.project.TravelMate.repository.UserRepository;

@Service
public class OTPService {

    @Autowired
    private UserRepository userRepository;

    private Map<String, Integer> otpStorage = new HashMap<>();
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserRepository getUserRepository() {
        return userRepository;
    }
    // Register user without OTP verification
    public User registerUser(User user) {
        user.setVerified(false);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        return userRepository.save(user);
    }

    // Send OTP (simulation)
    public void sendOtp(String phoneNo) {
        int otp = new Random().nextInt(999999); // Generate 6-digit OTP
        otpStorage.put(phoneNo, otp);
        System.out.println("OTP sent to " + phoneNo + ": " + otp); // Simulate OTP sending
    }

    // Verify OTP
    public boolean verifyOtp(String phoneNo, int otp) {
        Integer storedOtp = otpStorage.get(phoneNo);
        return storedOtp != null && storedOtp == otp;
    }

    // Verify user after OTP
    public Optional<User> verifyUser(String phoneNo, int otp) {
        if (verifyOtp(phoneNo, otp)) {
            Optional<User> user = userRepository.findByPhoneNo(phoneNo);
            user.ifPresent(u -> {
                u.setVerified(true); // Mark user as verified
                userRepository.save(u); // Save updated user
            });
            otpStorage.remove(phoneNo); // Remove OTP after verification
            return user;
        }
        return Optional.empty();
    }
   
}