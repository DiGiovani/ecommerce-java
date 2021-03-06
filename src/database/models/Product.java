package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = "product")
public class Product {
    public static final String ID_FIELD_NAME = "id";

    private static int counter = 1;

    @DatabaseField(id = true)
    private Integer id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int price;

    public Product() {
        // ORMLite precisa de um construtor sem argumentos
    }

    public Product(String name, int price) {
        this.id = counter;
        this.name = name;
        this.price = price;
        counter++;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
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
            TableUtils.createTableIfNotExists(connectionSource, Product.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
