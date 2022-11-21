package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.filter;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.LoginResource;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.text.ParseException;

/**
 * The JWTNeededFilter is responsible to filter traffic before it enters the API,
 * it filters any traffic which contains a falls JWT
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@JWTNeeded
@Provider
@Priority(Priorities.AUTHORIZATION)
public class JWTNeededFilter implements ContainerRequestFilter {

    /**
     * Check if the JWT is valid
     * @param containerRequestContext context
     * @throws IOException throws Exception
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String token = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try {
            JWSObject object = JWSObject.parse(token);
            boolean verified = object.verify(new MACVerifier(LoginResource.KEY));

            if (!verified) {
                throw new Exception("unverified");
            }
            String payload = object.getPayload().toString();
            containerRequestContext.setProperty("payload", payload);
        } catch (Exception e) {
            e.printStackTrace();
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("UNAUTHORIZED access!").build());
        }
    }
}
