package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * The Basket class is a related Basket to an user (User), which also needs id (int)
 * and a productMap (Map)
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    /**
     * Identifies the Basket
     */
    private int id;
    /**
     * Holds the products from the User and the amount of the Product
     */
    private Map<Product, Integer> productMap;
    /**
     * Identifies which User is related to the Basek
     */
    private User user;
}
