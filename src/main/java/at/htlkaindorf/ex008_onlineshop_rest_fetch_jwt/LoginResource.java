package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.User;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.db.OnlineShopDB;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The LoginResource is responsible for creating a JWT and get the JWT from a user
 *
 * <br><br>
 * Path: /login
 * <br>
 * GET: getJwtString(user)
 * <br><br>
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Path("/login")
public class LoginResource {

    /**
     * JWT Key
     */
    public static final String KEY = "my-jwt-secret-is-not-long-enough";

    /**
     * Creating a JWT
     * @param payload payload for the JWT
     * @return the jwt {@link String}
     */
    public String createJWT(User payload) {
        ObjectMapper mapper = new ObjectMapper();
        JWSObject object = null;
        try {
            object = new JWSObject(
                    new JWSHeader(JWSAlgorithm.HS256),
                    new Payload(mapper.writeValueAsString(payload)));

            object.sign(new MACSigner(KEY.getBytes()));
        } catch (JOSEException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return object.serialize();
    }

    /**
     * Returns the jwt from a given User
     * @param user user
     * @return a {@link Response} with the Status 401
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getJwtString(User user) {
        if (OnlineShopDB.getMyInstance().existUser(user)) {
            String jwt = createJWT(user);
            System.out.println(jwt);
            return Response.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}