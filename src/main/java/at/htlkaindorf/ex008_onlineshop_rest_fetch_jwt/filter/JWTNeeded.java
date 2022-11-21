package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.filter;

import jakarta.ws.rs.NameBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An Interface for the Authorization
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface JWTNeeded {
}
