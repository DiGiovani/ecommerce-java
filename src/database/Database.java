package database;

import java.sql.*;

public class Database {
    public static void initialization() {
        try {
            Connection connection = connect();

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

    public static void adicionaProduto() { // Precisamos adicionar a classe Produto como parâmetro
        try {
            String name = "", price = "";

            Connection connection = connect();
            Statement productInsertion = connection.createStatement();
            productInsertion.execute("INSERT INTO PRODUCT(NAME, PRICE) VALUES (" + name + ", " + price + ")");
            System.out.println("");

        } catch (SQLException e) {
        }

    }

    private static Connection connect() {
        // TABLE CREATION
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            System.out.println("Database connected! :D");
            return connection;

        } catch (SQLException e) {
            System.out.println("--------------DATABASE CONNECTION FAILED--------------");
            System.out.println("ERROR MESSAGE: ");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
        return null;
    }
}
