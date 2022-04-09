package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

public class Cart {
    public static final String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    public Cart() {

    }

    public Cart(String name) {
        this.name = name;
    }

    static public void createTable() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");) {
            TableUtils.createTableIfNotExists(connectionSource, Cart.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getId() {
        return this.id;
    }
}
