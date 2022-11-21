package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Product class represents a Product consisting of a description (String),
 * id (int), imageUrl (String), name (String) and a price (float)
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /**
     * Describes the Product
     */
    private String description;
    /**
     * Identify the Product
     */
    private int id;
    /**
     * The URL from the Product Image
     */
    private String imageUrl;
    /**
     * Represents the Product name
     */
    private String name;
    /**
     * Represents the price of a Product
     */
    private float price;
}
