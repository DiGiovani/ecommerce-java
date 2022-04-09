package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

// Client<>Cart many to many relation
public class ClientCart {
    @DatabaseField(foreign = true)
    Client client;

    @DatabaseField(foreign = true)
    Cart cart;

    public ClientCart() {

    }

    public ClientCart(Client client, Cart cart) {
        this.client = client;
        this.cart = cart;
    }

    static public void createTable() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");) {
            TableUtils.createTableIfNotExists(connectionSource, ClientCart.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
