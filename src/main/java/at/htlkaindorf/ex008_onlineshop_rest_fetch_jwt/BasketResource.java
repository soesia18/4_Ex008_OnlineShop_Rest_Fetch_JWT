package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.Basket;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.Product;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.User;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.db.OnlineShopDB;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.filter.JWTNeeded;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.*;

import java.util.LinkedList;

/**
 * The BasketResource Class is responsible for creating Baskets, add Products to Baskets,
 * getting Baskets and deleting Products from Baskets
 *<br><br>
 * Path: /basket
 *<br>
 * GET: createBasket()
 *<br><br>
 * Path: /basket/id<br>
 * GET: getFromBasket(id)<br>
 * POST: addToBasket(id, prodId)
 *<br><br>
 * Path: /basket/id/productID<br>
 * DELETE: deleteFromBasket(id, productID)
 *<br><br>
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Path("/basket")
public class BasketResource {

    /**
     * {@link UriInfo} for the real Path
     */
    @Context
    UriInfo uriInfo;

    /**
     * {@link ContainerRequestContext} for data from the Filter
     */
    @Context
    ContainerRequestContext cre;

    /**
     * Creates a basket for the specific User
     * @return a {@link Response} with the Path from the Basket and the Status 201
     */
    @GET
    @JWTNeeded
    public Response createBasket() {
        ObjectMapper mapper = new ObjectMapper();
        String payload = cre.getProperty("payload").toString();
        User user = null;
        try {
            user = mapper.readValue(payload, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        int id = OnlineShopDB.getMyInstance().createBasket(user);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path("" + id);

        return Response.created(builder.build()).build();
    }

    /**
     * Add a given {@link Product} to the given {@link Basket}
     * @param id id from the {@link Basket}
     * @param prodId id form the {@link Product}
     * @return a {@link Response} with the new {@link Basket} and the Status 200
     */
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response addToBasket(@PathParam("id") int id, int prodId) {

        Basket basket = OnlineShopDB.getMyInstance().addToBasket(id, prodId);
        return Response.ok(basket).build();
    }

    /**
     * Return the {@link Basket} for a given BasketID
     * @param id id from the {@link Basket}
     * @return a {@link Response} with the {@link Basket} and the Status 200
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response getFromBasket(@PathParam("id") int id) {
        Basket basket = OnlineShopDB.getMyInstance().getBasketFromID(id);
        return Response.ok(basket).build();
    }

    /**
     * Delete a given {@link Product} form a given {@link Basket}
     * @param id id from the {@link Basket}
     * @param productID id from the {@link Product}
     * @return a {@link Response} with the Status 200
     */
    @DELETE
    @Path("/{id}/{productID}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response deleteFromBasket(@PathParam("id") int id, @PathParam("productID") int productID) {
        OnlineShopDB.getMyInstance().deleteFromBasket(id, productID);
        return Response.ok().build();
    }
}
