package database.models;

import java.sql.SQLException;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import database.Database;

@DatabaseTable(tableName = "client")
public class Client {
    public static final String ID_FIELD_NAME = "id";

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField
    private String address;

    public Client() {
        // ORMLite precisa de um construtor sem argumentos para o retorno de querys
    }

    public Client(String name, String email, String password, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
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
            TableUtils.createTableIfNotExists(connectionSource, Client.class);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static public Client authenticateUser(String email, String password) {
        try {
            Client client = Database.getClient(email);

            if (client.password.equals(password)) {
                return client;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }
}
