package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import database.Database;

// Cart <> Product many to many relation
@DatabaseTable(tableName = "cartProduct")
public class CartProduct {
    public static final String CART_ID_FIELD_NAME = "cart_id";
    public static final String PRODUCT_FIELD_NAME = "product_id";

    @DatabaseField(generatedId = true)
    Integer id;

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

    public Product getProduct() {
        try {
            return Database.getProduct(this.product);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Integer getId() {
        return this.id;
    }

    static public void createTable() throws Exception {
        try {
            createTable("jbdc:sqlite:database.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static public void createTable(String database) throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(database);) {
            TableUtils.createTableIfNotExists(connectionSource, CartProduct.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
