package mx.edu.utez.firstapp.security.jwt;

import io.jsonwebtoken.*;
import mx.edu.utez.firstapp.security.model.UserAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    public final static Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}") //busca en el properties tal valor
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UserAuth userAuth = (UserAuth) authentication.getPrincipal();//obtenemos al usuario que esta intentando entrar, lo convertimos a un userAuth que es un userdetails
        return Jwts.builder().setSubject(userAuth.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+expiration*1000L)).signWith(SignatureAlgorithm.HS512, secret).compact();//forma de encriptacion
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){//jwt no reconoce ese token
            LOGGER.error("Token mal formado");
        }catch (UnsupportedJwtException e){
            LOGGER.error("Token no soportado");
        }catch (ExpiredJwtException e){
            LOGGER.error("Token expirado");
        }catch (IllegalArgumentException e){
            LOGGER.error("Token no provisto");//no le pasaron el token
        }catch (SignatureException e){
            LOGGER.error("Error en la firma del token");
        }
        return false;
    }
}
