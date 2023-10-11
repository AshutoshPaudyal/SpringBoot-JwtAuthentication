package com.example.newoffceproject.service.serviceimpl;


import com.example.newoffceproject.model.User;
import com.example.newoffceproject.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    @Override
    public void generateOtpThroughAkashSms(String toSendOtp, User user) {
        WebClient webClient = WebClient.builder().baseUrl("https://sms.aakashsms.com").build();
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sms/v3/send")
                        .queryParam("auth_token", "{put token here}")
                        .queryParam("to", user.getPhoneNumber())
                        .queryParam("text","Your OTP to reset password is "+toSendOtp+". Use this OTP to change password.")
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
