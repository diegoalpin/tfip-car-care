package sg.edu.nus.iss.app.tfip_carcare.filters;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.app.tfip_carcare.constants.SecurityConstant;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final SecretKey key;

    public JWTTokenValidatorFilter(SecretKey key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtHeader = request.getHeader(SecurityConstant.JWT_HEADER);//"Authorization"
        String jwt=null;
        if(jwtHeader!=null){
            String[] jwtSplit = jwtHeader.split(" ");
            if(jwtSplit.length==1){
                jwt = jwtSplit[0].toString();
            }
            else{
                jwt = jwtSplit[1].toString(); //POSTMAN
            }
        }
        if (null != jwt) {
            try {

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                // throw new BadCredentialsException("Invalid Token received!");
            }

        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/login");//Not active during login
    }

}
