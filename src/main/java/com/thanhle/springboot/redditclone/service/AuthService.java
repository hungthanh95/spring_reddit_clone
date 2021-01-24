package com.thanhle.springboot.redditclone.service;

import com.thanhle.springboot.redditclone.dto.request.LoginRequest;
import com.thanhle.springboot.redditclone.dto.request.RegisterRequest;
import com.thanhle.springboot.redditclone.dto.response.AuthenticationResponse;
import com.thanhle.springboot.redditclone.exception.SpringRedditException;
import com.thanhle.springboot.redditclone.model.NotificationEmail;
import com.thanhle.springboot.redditclone.model.User;
import com.thanhle.springboot.redditclone.model.VerificationToken;
import com.thanhle.springboot.redditclone.repository.UserRepository;
import com.thanhle.springboot.redditclone.repository.VerificationTokenRepository;
import com.thanhle.springboot.redditclone.security.JwtProvider;
import com.thanhle.springboot.redditclone.util.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static com.thanhle.springboot.redditclone.common.Constants.ACTIVATION_EMAIL;

@Service
public class AuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final MailService mailService;
    private final MailContentBuilder mailContentBuilder;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, VerificationTokenRepository tokenRepository, MailService mailService, MailContentBuilder mailContentBuilder, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailService = mailService;
        this.mailContentBuilder = mailContentBuilder;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = userMapper.request2Entity(registerRequest);
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + ACTIVATION_EMAIL + "/" + token);
        mailService.sendMail(new NotificationEmail("Please Activate you account", user.getEmail(), message));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);
        return token;
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token).orElseThrow(() ->new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken);
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authToken = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(authToken, loginRequest.getUsername());
    }

}
