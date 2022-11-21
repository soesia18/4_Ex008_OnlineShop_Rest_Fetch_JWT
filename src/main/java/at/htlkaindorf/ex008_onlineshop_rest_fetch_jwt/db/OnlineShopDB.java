package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.db;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.Basket;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.OnlineShop;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.Product;
import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.beans.User;
import jakarta.xml.bind.JAXB;
import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OnlineShopDB is a Database class, which holds all the OnlineShop Data and
 * is responsible for adding, removing and changing the data
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@Data
public class OnlineShopDB {

    /**
     * Singleton Pattern Instance Object
     */
    private static OnlineShopDB myInstance;

    /**
     * Stores the OnlineShop data
     */
    private OnlineShop onlineShop = new OnlineShop();

    /**
     * the XML - FIle
     */
    private File file;

    /**
     * private Constructor for Singleton Pattern
     */
    private OnlineShopDB() {
        /*onlineShop.getProductList().add(new Product("G5 Samsung Monitor 34 Zoll 144Hz", 1, "https://th.bing.com/th/id/R.c54ebd4e391565cd222e58c1d3b8d7e5?rik=lkzQ4yy%2fmIUTkQ&pid=ImgRaw&r=0", "PC Monitor",250f));
        onlineShop.getProductList().add(new Product("RTX 2080TI", 2, "https://img.flaticon.com/icons/png/512/3826/3826132.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF", "Grafikkarte",670f));
        onlineShop.getProductList().add(new Product("MSI BM250", 3, "https://th.bing.com/th/id/R.a0ea955929686127ddae0a657a55fb68?rik=jBV7JAVh1%2bl70w&pid=ImgRaw&r=0", "Motherboard",400f));
        onlineShop.getProductList().add(new Product("Razer Maus", 4, "https://th.bing.com/th/id/R.64e07e0bdcc0b23e22294ec356a8c88b?rik=aTjgdYz2oK4moQ&pid=ImgRaw&r=0", "Maus",30f));
        onlineShop.getProductList().add(new Product("Razer Keyboard", 5, "https://th.bing.com/th/id/R.897b12c7badaebbfcdf0da45c6c6e09d?rik=pqRpr9V2o1I%2b1Q&pid=ImgRaw&r=0", "Tastatur",200f));

        onlineShop.getUserList().add(new User("admin", "admin"));
        onlineShop.getUserList().add(new User("user", "user"));*/
    }

    /**
     * Singleton Pattern, for get the Instance, if it is present it Returns, otherwise it creates
     * the instance and then it returns
     * @return OnlineShopDB Object
     */
    public synchronized static OnlineShopDB getMyInstance () {
        if (myInstance == null) {
            myInstance = new OnlineShopDB();
        }
        return myInstance;
    }

    /**
     * Creates a new Basket, if there is no Basket for the given User yet
     * @param user user
     * @return the id of the Basket
     */
    public int createBasket (User user) {
        if (onlineShop.getBasketList().stream().noneMatch(basket -> basket.getUser().equals(user))) {
            int id = onlineShop.getBasketList().size() + 1;
            onlineShop.getBasketList().add(new Basket(id, new HashMap<>(), user));
            return id;
        }
        saveXMLData();
        return onlineShop.getBasketList().stream().filter(basket -> basket.getUser().equals(user)).collect(Collectors.toList()).get(0).getId();
    }

    /**
     * Adds a given Product to the given Basket
     * @param id id from the Basket
     * @param productID id from the Product
     * @return the Basket from the given id
     */
    public Basket addToBasket (int id, int productID) {
        Product product = getProductFromId(productID);
        Basket basket = getBasketFromID(id);

        if (basket.getProductMap().containsKey(product)) {
            basket.getProductMap().put(product, basket.getProductMap().get(product) + 1);
        } else {
            basket.getProductMap().put(product, 1);
        }
        saveXMLData();
        return basket;
    }

    /**
     * Returns the Product from the given id
     * @param id id from a Product
     * @return the Product from the given id
     */
    public Product getProductFromId (int id){
        return onlineShop.getProductList().stream().filter(product -> product.getId() == id).findFirst().get();
    }

    /**
     * Returns the Basket from the given id
     * @param id id from a Basket
     * @return the Basket from the given id
     */
    public Basket getBasketFromID (int id) {
        return onlineShop.getBasketList().stream().filter(basketS -> basketS.getId() == (id)).findFirst().get();
    }

    /**
     * Deletes a given Product from a given Basket
     * @param id id form the Basket
     * @param productID id from the Product
     */
    public void deleteFromBasket (int id, int productID) {
        int amount = getBasketFromID(id).getProductMap().get(getProductFromId(productID));

        if (amount == 1) {
            getBasketFromID(id).getProductMap().remove(getProductFromId(productID));
        } else {
            getBasketFromID(id).getProductMap().put(getProductFromId(productID), getBasketFromID(id).getProductMap().get(getProductFromId(productID)) - 1);
        }
        saveXMLData();
    }

    /**
     * Returns if the User exsits in the OnlineShop
     * @param user user
     * @return true if the User exists, otherwise false
     */
    public boolean existUser (User user) {
        return onlineShop.getUserList().contains(user);
    }

    /**
     * Loads the data from the XML File
     * @param file file from the XML
     */
    public void loadDB (File file) {
        this.file = file;
        this.onlineShop = JAXB.unmarshal(file, OnlineShop.class);
    }

    /**
     * Writes the onlineShop data in the XML File
     */
    public void saveXMLData () {
        JAXB.marshal(onlineShop, file);
    }
}
