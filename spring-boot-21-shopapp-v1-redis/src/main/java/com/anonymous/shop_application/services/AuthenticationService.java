package com.anonymous.shop_application.services;

import com.anonymous.shop_application.dtos.requests.AuthenticationRequest;
import com.anonymous.shop_application.dtos.requests.LogoutRequest;
import com.anonymous.shop_application.dtos.responses.AuthenticationResponse;
import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.models.InvalidToken;
import com.anonymous.shop_application.models.User;
import com.anonymous.shop_application.repositories.InvalidTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {

    UserService userService;

    PasswordEncoder passwordEncoder;

    InvalidTokenRepository invalidTokenRepository;

    @NonFinal
    @Value("${jwt.valid-duration}")
    Long validDuration;

    @NonFinal
    @Value("${jwt.refresh_duration}")
    Long refreshDuration;

    @NonFinal
    @Value("${jwt.signer-key}")
    String signerKey;

    public AuthenticationResponse login(AuthenticationRequest request) {

        User user = userService.findUserByPhoneNumber(request.getPhoneNumber());

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.USER_UNAUTHENTICATED);

        return AuthenticationResponse.builder()
                .token(generateToken(user))
                .build();
    }

    public boolean introspect(String token) throws JOSEException, ParseException{
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return isValid;
    }

    public String logout(LogoutRequest request) throws ParseException, JOSEException {

        try {
            var signToken = verifyToken(request.getToken(), true);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidatedToken = InvalidToken.builder()
                    .id(jit)
                    .expirationTime(expiryTime)
                    .build();

            invalidTokenRepository.save(invalidatedToken);

        } catch (AppException e) {
            return "Token already expired";
        }

        return "Successfully logout";
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = isRefresh
                ? new Date(signedJWT.getJWTClaimsSet().getExpirationTime()
                    .toInstant().plusSeconds(refreshDuration).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date())))
            throw new AppException(ErrorCode.USER_UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // payload - set claim
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getPhoneNumber())
                .issuer("anonymous.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        // object
        JWSObject object = new JWSObject(header, payload);

        try {
            object.sign(new MACSigner(signerKey));
            return object.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> stringJoiner.add("ROLE_" + role.getName()));

        return stringJoiner.toString();
    }
}
