package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.OnlineShop;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.Product;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.db.OnlineShopDB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * The ProductResource is responsible for getting the resources
 *
 * <br><br>
 * Path: /products
 * <br>
 * GET: getAllProducts()
 * <br><br>
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    /**
     * Get all Products in the {@link OnlineShop}
     * @return all Products in the {@link OnlineShop}
     */
    @GET
    public List<Product> getAllProducts () {
        return OnlineShopDB.getMyInstance().getOnlineShop().getProductList();
    }
}
