package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.*;
import com.j256.ormlite.jdbc.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

@DatabaseTable(tableName = "cart")
public class Cart {
    public static final String ID_FIELD_NAME = "id";
    public static final String CLIENT_FIELD_NAME = "client_id";

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(foreign = true)
    private Client client;

    public Cart() {

    }

    public Cart(Client client) {
        this.client = client;
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
            TableUtils.createTableIfNotExists(connectionSource, Cart.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer getId() {
        return this.id;
    }
}
