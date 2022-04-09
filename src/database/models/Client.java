package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Client {
    public static final String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    public Client() {
        // ORMLite precisa de um construtor sem argumentos
    }

    public Client(String name) {
        this.name = name;
    }

    static public void createTable() throws Exception {
        try (ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:database.db");) {
            TableUtils.createTableIfNotExists(connectionSource, Client.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
