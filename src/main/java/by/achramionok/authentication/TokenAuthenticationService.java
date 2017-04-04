package by.achramionok.authentication;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Kirill on 30.03.2017.
 */
public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000;
    static final String SECRET = "IHaveFriendsEverywhere";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "authorization";

    static void addAuthentication(HttpServletResponse res, String username){
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }
    static Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        System.out.println(request.getMethod());
        if(token != null){
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                    .getBody()
                    .getSubject();
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                    null;
        }
        return null;
    }
}
