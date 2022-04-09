package database;

import java.util.*;
import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.support.ConnectionSource;

import database.models.*;

public class Database {
    public static void initialization() {
        try {
            Cart.createTable();
            Client.createTable();
            Product.createTable();
            ClientCart.createTable();
            CartProduct.createTable();
        } catch (Exception e) {
            System.out.println("--------------DATABASE INITIALIZATION FAILED--------------");
            System.out.println("ERROR MESSAGE: ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
    }

    static public List<Product> getProduct(Product product) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db")) {
            Dao<Product, Integer> productDao = DaoManager.createDao(connectionSource, Product.class);
            QueryBuilder<Product, Integer> queryBuilder = productDao.queryBuilder();
            PreparedQuery<Product> preparedQuery = queryBuilder.where().eq(Product.ID_FIELD_NAME, product.getId())
                    .prepare();
            List<Product> products = productDao.query(preparedQuery);

            return products;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public void addProduct(Product product) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db")) {
            Dao<Product, Integer> productDao = DaoManager.createDao(connectionSource, Product.class);
            productDao.createIfNotExists(product);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void addClient(Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db")) {
            Dao<Client, Integer> clientDao = DaoManager.createDao(connectionSource, Client.class);
            clientDao.createIfNotExists(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void addCart(Cart client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db")) {
            Dao<Cart, Integer> cartDao = DaoManager.createDao(connectionSource, Cart.class);
            cartDao.createIfNotExists(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void alterCart(Cart cart, List<Product> products) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db")) {
            Dao<Cart, Integer> cartDao = DaoManager.createDao(connectionSource, Cart.class);
            UpdateBuilder<Cart, Integer> updateBuilder = cartDao.updateBuilder();
            updateBuilder.updateColumnValue("products", products);
            updateBuilder.where().eq(Cart.ID_FIELD_NAME, cart.getId());
            updateBuilder.update();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
