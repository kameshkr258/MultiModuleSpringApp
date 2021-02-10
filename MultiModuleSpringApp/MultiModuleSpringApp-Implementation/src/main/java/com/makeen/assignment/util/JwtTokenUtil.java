package com.makeen.assignment.util;


import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {

    private final String jwtSecret = "MakeenS3cR3TK3YTHqw4#$%^";
    private final String jwtIssuer = "makeen.io";

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject( format( "%s", username ) )
                .setIssuer( jwtIssuer )
                .setIssuedAt( new Date() )
                .setExpiration( new Date( System.currentTimeMillis() + 60 * 60 * 1000 ) )
                .signWith( SignatureAlgorithm.HS512, jwtSecret )
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( jwtSecret )
                .parseClaimsJws( token )
                .getBody();

        return claims.getSubject().split( "," )[0];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey( jwtSecret )
                .parseClaimsJws( token )
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey( jwtSecret ).parseClaimsJws( token );
            return true;
        } catch (SignatureException ex) {
            System.out.println( "Invalid JWT signature - " + ex.getMessage() );
        } catch (MalformedJwtException ex) {
            System.out.println( "Invalid JWT token - " + ex.getMessage() );
        } catch (ExpiredJwtException ex) {
            System.out.println( "Expired JWT token - " + ex.getMessage() );
        } catch (UnsupportedJwtException ex) {
            System.out.println( "Unsupported JWT token - " + ex.getMessage() );
        } catch (IllegalArgumentException ex) {
            System.out.println( "JWT claims string is empty - " + ex.getMessage() );
        }
        return false;
    }

}
