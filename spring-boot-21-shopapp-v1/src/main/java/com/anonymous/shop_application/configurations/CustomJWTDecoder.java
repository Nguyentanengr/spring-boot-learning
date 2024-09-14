package com.anonymous.shop_application.configurations;

import com.anonymous.shop_application.exceptions.AppException;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.anonymous.shop_application.services.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomJWTDecoder implements JwtDecoder {

    @NonFinal
    @Value("${jwt.signer-key}")
    String signerKey;

    @NonFinal
    NimbusJwtDecoder nimbusJwtDecoder = null;

    AuthenticationService authenticationService;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            if (!authenticationService.introspect(token))
                throw new AppException(ErrorCode.USER_UNAUTHENTICATED);
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");

            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();

        }

        return nimbusJwtDecoder.decode(token);
    }
}
