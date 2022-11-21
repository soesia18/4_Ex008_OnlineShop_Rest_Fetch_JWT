package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The OnlineShop class holds a List of Products, Baskets and User,
 * this class stores all the Data for the OnlineShop
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class OnlineShop {
    /**
     * Holds all Products
     */
    private List<Product> productList = new ArrayList<>();
    /**
     * Holds all Baskets
     */
    private List<Basket> basketList = new ArrayList<>();
    /**
     * Holds all Users
     */
    private List<User> userList = new ArrayList<>();
}
