package at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt;

import at.htlkaindorf.ex008_onlineshop_rest_fetch_jwt.db.OnlineShopDB;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;

import java.io.File;

/**
 * API
 * <br>
 * PATH: /api
 *
 * last modified 28.02.2022
 * @author Simon Schöggler
 * @version 1.0
 */

@WebListener
@ApplicationPath("/api")
public class OnlineShopApplication extends Application implements ServletContextListener {

    /**
     * to load the file and the DB
     * @param sce {@link ServletContextEvent}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        File file = new File(sce.getServletContext().getRealPath("onlineshop.xml"));
        OnlineShopDB.getMyInstance().loadDB(file);
    }
}