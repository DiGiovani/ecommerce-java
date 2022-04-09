package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

// Cart <> Product many to many relation
public class CartProduct {
    @DatabaseField(foreign = true)
    Cart cart;

    @DatabaseField(foreign = true)
    Product product;

    public CartProduct() {

    }

    public CartProduct(Cart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    static public void createTable() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");) {
            TableUtils.createTableIfNotExists(connectionSource, CartProduct.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
