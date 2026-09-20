package sg.edu.nus.iss.app.tfip_carcare.filters;

import javax.crypto.SecretKey;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;

class JWTKeyConfigurationTests {
    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private String issueToken(SecretKey key) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test-user", null,
                        AuthorityUtils.createAuthorityList("ROLE_USER")));
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/login");
        MockHttpServletResponse response = new MockHttpServletResponse();
        new JWTTokenGeneratorFilter(key).doFilter(request, response, new MockFilterChain());
        SecurityContextHolder.clearContext();
        return response.getHeader("Authorization");
    }

    private void validate(String token, SecretKey key) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/car");
        request.addHeader("Authorization", token);
        new JWTTokenValidatorFilter(key).doFilter(request, new MockHttpServletResponse(), new MockFilterChain());
    }

    @Test
    void injectedKeySignsAndValidatesTokens() throws Exception {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = issueToken(key);
        assertNotNull(token);
        validate(token, key);
        assertEquals("test-user", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void rotatedKeyRejectsPreviousTokens() throws Exception {
        String token = issueToken(Keys.secretKeyFor(SignatureAlgorithm.HS256));
        validate(token, Keys.secretKeyFor(SignatureAlgorithm.HS256));
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
