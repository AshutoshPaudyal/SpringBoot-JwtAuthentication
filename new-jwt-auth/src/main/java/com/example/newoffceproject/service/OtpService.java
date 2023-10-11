package com.example.newoffceproject.service;

import com.example.newoffceproject.model.User;

public interface OtpService {
    void generateOtpThroughAkashSms(String toSendOtp, User user);
}
