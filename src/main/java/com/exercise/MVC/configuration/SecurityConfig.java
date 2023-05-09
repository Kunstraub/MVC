package com.exercise.MVC.configuration;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final RsaKeyProperties keyProperties;

    public SecurityConfig(RsaKeyProperties keyProperties) {
        this.keyProperties = keyProperties;
    }

    @Bean
    public InMemoryUserDetailsManager user() {
        return new InMemoryUserDetailsManager(User.withUsername("stefan")
                .password("pw").authorities("read").build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                // Hier wird das Passwort mit dem öffentlichen Schlüssel verschlüsselt
                PublicKey publicKey = null;
                try {
                    publicKey = loadPublicKey();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                byte[] encryptedPassword = new byte[0];
                try {
                    encryptedPassword = encryptWithPublicKey(rawPassword.toString().getBytes(), publicKey);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return new String(Base64.getEncoder().encode(encryptedPassword));
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // Hier wird das verschlüsselte Passwort mit dem privaten Schlüssel entschlüsselt und überprüft
                PrivateKey privateKey = null;
                try {
                    privateKey = loadPrivateKey();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                byte[] decryptedPassword = new byte[0];
                try {
                    decryptedPassword = decryptWithPrivateKey(Base64.getDecoder().decode(encodedPassword), privateKey);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String decryptedPasswordString = new String(decryptedPassword);
                return rawPassword.toString().equals(decryptedPasswordString);
            }

            private PublicKey loadPublicKey() throws Exception{
                // Hier den öffentlichen Schlüssel aus keypair.pem oder public.pem laden
                // und als PublicKey-Objekt zurückgeben
                // ...
                String publicKeyPath = "src/main/resources/certs/public.pem";
                byte[] publicKeyBytes = readPEMFile(publicKeyPath);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                return keyFactory.generatePublic(publicKeySpec);
            }

            private PrivateKey loadPrivateKey() throws Exception {
                // Hier den privaten Schlüssel aus keypair.pem oder private.pem laden
                // und als PrivateKey-Objekt zurückgeben
                // ...
                String privateKeyPath = "src/main/resources/certs/private.pem";
                byte[] privateKeyBytes = readPEMFile(privateKeyPath);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                return keyFactory.generatePrivate(privateKeySpec);
            }

            private byte[] encryptWithPublicKey(byte[] data, PublicKey publicKey) throws Exception {
                // Hier die Daten mit dem öffentlichen Schlüssel verschlüsseln
                // und als byte-Array zurückgeben
                // ...
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(data);
            }

            private byte[] decryptWithPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws Exception {
                // Hier die verschlüsselten Daten mit dem privaten Schlüssel entschlüsseln
                // und als byte-Array zurückgeben
                // ...
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(encryptedData);
            }

            private byte[] readPEMFile(String filePath) throws IOException{
                Path path = Paths.get(filePath);
                byte[] pemBytes = Files.readAllBytes(path);
                String pemString = new String(pemBytes);
                String base64String = pemString
                        .replaceAll("-----BEGIN (.*)-----", "")
                        .replaceAll("-----END (.*)-----", "")
                        .replaceAll("\r\n", "")
                        .replaceAll("\n", "");
                return Base64.getDecoder().decode(base64String);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
       return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keyProperties.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(keyProperties.publicKey()).privateKey(keyProperties.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }


}



