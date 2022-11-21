package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The User class represents an User with an username as a String and
 * a Password as a String.
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * Represents the name of an User
     */
    private String username;
    /**
     * Represents a password of an User
     */
    private String password;
}
