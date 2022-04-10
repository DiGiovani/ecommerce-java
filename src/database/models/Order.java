package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Order {
    public static final String ID_FIELD_NAME = "id";
    public static final String CART_ID_FIELD_NAME = "cart_id";
    public static final String CLIENT_ID_FIELD_NAME = "client_id";

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private Client client;

    @DatabaseField(foreign = true)
    private Cart cart;

    public Order() {

    }

    public Order(Cart cart, Client client) {
        this.client = client;
        this.cart = cart;
    }

    public Cart getCart() {
        return this.cart;
    }

    public Integer getId() {
        return this.id;
    }

    static public void createTable() throws Exception {
        try {
            createTable("jdbc:sqlite:database.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void createTable(String database) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(database);) {
            TableUtils.createTableIfNotExists(connectionSource, Order.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
