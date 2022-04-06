package database;

import java.sql.*;

public class DatabaseConnection {
    private static void initialization(Connection connection) {
        try {
            Statement productsDatabase = connection.createStatement();
            // TABLE POPULATION
            productsDatabase.execute(
                    "CREATE TABLE IF NOT EXISTS PRODUCT(ID INTEGER PRIMARY KEY, NAME VARCHAR(30), PRICE INTEGER)");
            productsDatabase
                    .execute(
                            "INSERT INTO PRODUCT(NAME, PRICE) VALUES ('Banheira', 250), ('Patinho de Borracha', 5), ('Desentupidor de Pia', 3), ('Carrinho de Controle Remoto', 100), ('Escova de Dentes', 5), ('Casa da Barbie', 1500), ('Batom', 20), ('Ingresso Cinemark', 50), ('Alexa', 250), ('TV DE TUBO 29\" + CONVERSOR HDMI + ANTENA PARABOLICA ANALOGICA', 150)");
            System.out.println("Database initialized! :D");

        } catch (SQLException e) {
            System.out.println("--------------DATABASE INITIALIZATION FAILED--------------");
            System.out.println("ERROR MESSAGE: ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }

    }

    public static void connect() {
        // TABLE CREATION
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db")) {
            System.out.println("Database connected! :D");
            initialization(connection);

        } catch (SQLException e) {
            System.out.println("--------------DATABASE CONNECTION FAILED--------------");
            System.out.println("ERROR MESSAGE: ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
    }
}
