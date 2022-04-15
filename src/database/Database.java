package database;

import java.util.*;
import java.sql.SQLException;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.*;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import database.models.*;

public class Database {
    public static String source = "jdbc:sqlite:database.db";

    public static void setSource(String newSource) {
        source = "jdbc:sqlite:" + newSource;
    }

    public static void initialization() {
        try {
            Cart.createTable(source);
            Client.createTable(source);
            Product.createTable(source);
            CartProduct.createTable(source);
            Order.createTable(source);
        } catch (Exception e) {
            System.out.println("--------------DATABASE INITIALIZATION FAILED--------------");
            System.out.println("ERROR MESSAGE: ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
    }

    public static void dropDatabase() {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            TableUtils.clearTable(connectionSource, Product.class);
            TableUtils.clearTable(connectionSource, Client.class);
            TableUtils.clearTable(connectionSource, Cart.class);
            TableUtils.clearTable(connectionSource, CartProduct.class);
            TableUtils.clearTable(connectionSource, Order.class);
        } catch (Exception e) {
        }
    }

    static public Product getProduct(Product product) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Product, Integer> productDao = DaoManager.createDao(connectionSource, Product.class);
            QueryBuilder<Product, Integer> queryBuilder = productDao.queryBuilder();
            PreparedQuery<Product> preparedQuery = queryBuilder.where().eq(Product.ID_FIELD_NAME, product.getId())
                    .prepare();
            List<Product> products = productDao.query(preparedQuery);

            return products.get(0);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public Cart getCart(Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Cart cart;

            Dao<Cart, Integer> cartDao = DaoManager.createDao(connectionSource, Cart.class);
            QueryBuilder<Cart, Integer> queryBuilder = cartDao.queryBuilder().orderBy("id", false);
            PreparedQuery<Cart> preparedQuery = queryBuilder.where().eq(Cart.CLIENT_FIELD_NAME, client).prepare();
            List<Cart> carts = cartDao.query(preparedQuery);

            if (carts.size() == 0) {
                cart = new Cart(client);
                addCart(cart);
                return cart;
            }

            cart = carts.get(0);
            return cart;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public Cart getCart(Order order) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Cart cart;

            Dao<Cart, Integer> cartDao = DaoManager.createDao(connectionSource, Cart.class);
            QueryBuilder<Cart, Integer> queryBuilder = cartDao.queryBuilder().orderBy("id", true);
            PreparedQuery<Cart> preparedQuery = queryBuilder.where().eq(Cart.ID_FIELD_NAME, order.getCart().getId())
                    .prepare();
            List<Cart> carts = cartDao.query(preparedQuery);

            cart = carts.get(0);
            return cart;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public void addProductToCart(Product product, Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Cart cart = getCart(client);
            Dao<CartProduct, Integer> cartProductDao = DaoManager.createDao(connectionSource, CartProduct.class);

            CartProduct cartProduct = new CartProduct(cart, product);
            cartProductDao.create(cartProduct);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void removeProductFromCart(Product product, Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Cart cart = getCart(client);
            Dao<CartProduct, Integer> cartProductDao = DaoManager.createDao(connectionSource, CartProduct.class);
            QueryBuilder<CartProduct, Integer> queryBuilder = cartProductDao.queryBuilder();
            PreparedQuery<CartProduct> preparedQuery = queryBuilder.where()
                    .eq(CartProduct.CART_ID_FIELD_NAME, cart.getId()).and()
                    .eq(CartProduct.PRODUCT_FIELD_NAME, product.getId())
                    .prepare();
            List<CartProduct> cartProducts = cartProductDao.query(preparedQuery);
            cartProductDao.delete(cartProducts.get(0));
        } catch (Exception e) {

        }
    }

    static public List<Product> getProductsFromCart(Cart cart) {
        List<Product> products = new ArrayList<Product>();
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {

            Dao<CartProduct, Integer> cartProductDao = DaoManager.createDao(connectionSource, CartProduct.class);
            QueryBuilder<CartProduct, Integer> queryBuilder = cartProductDao.queryBuilder();
            PreparedQuery<CartProduct> preparedQuery = queryBuilder.where()
                    .eq(CartProduct.CART_ID_FIELD_NAME, cart.getId())
                    .prepare();
            List<CartProduct> cartProducts = cartProductDao.query(preparedQuery);

            cartProducts.forEach(cartProduct -> {
                products.add(cartProduct.getProduct());
            });

        } catch (Exception e) {

        }
        return products;
    }

    static public List<Product> getProducts() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Product, Integer> productDao = DaoManager.createDao(connectionSource, Product.class);
            QueryBuilder<Product, Integer> queryBuilder = productDao.queryBuilder();
            PreparedQuery<Product> preparedQuery = queryBuilder.prepare();
            List<Product> products = productDao.query(preparedQuery);

            return products;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public List<Client> getClients() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Client, Integer> clientDao = DaoManager.createDao(connectionSource, Client.class);
            QueryBuilder<Client, Integer> queryBuilder = clientDao.queryBuilder();
            PreparedQuery<Client> preparedQuery = queryBuilder.prepare();
            List<Client> clients = clientDao.query(preparedQuery);

            return clients;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public Client getClient(String email) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Client, Integer> clientDao = DaoManager.createDao(connectionSource, Client.class);
            QueryBuilder<Client, Integer> queryBuilder = clientDao.queryBuilder();
            PreparedQuery<Client> preparedQuery = queryBuilder.where().eq("email", email).prepare();
            List<Client> clients = clientDao.query(preparedQuery);

            if (clients.size() > 0) {
                return clients.get(0);
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static public void addProduct(Product product) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Product, Integer> productDao = DaoManager.createDao(connectionSource, Product.class);
            productDao.createIfNotExists(product);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void addClient(Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Client alreadyExistsVerification = getClient(client.getEmail());
            if (alreadyExistsVerification != null) {
                throw new Exception("Already Exists");
            }
            Dao<Client, Integer> clientDao = DaoManager.createDao(connectionSource, Client.class);
            clientDao.createIfNotExists(client);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void addCart(Cart cart) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Cart, Integer> cartDao = DaoManager.createDao(connectionSource, Cart.class);
            cartDao.createIfNotExists(cart);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public void addOrder(Order order, Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Order, Integer> orderDao = DaoManager.createDao(connectionSource, Order.class);
            orderDao.createIfNotExists(order);
            Cart cart = new Cart(client);
            addCart(cart);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public List<Order> getOrders(Client client) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(source)) {
            Dao<Order, Integer> orderDao = DaoManager.createDao(connectionSource, Order.class);
            QueryBuilder<Order, Integer> queryBuilder = orderDao.queryBuilder();
            PreparedQuery<Order> preparedQuery = queryBuilder.where().eq(Order.CLIENT_ID_FIELD_NAME, client.getId())
                    .prepare();
            List<Order> orders = orderDao.query(preparedQuery);

            return orders;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
