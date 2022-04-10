package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.*;
import database.Database;
import database.models.Client;
import database.models.Product;

public class MainTest {

    @BeforeClass
    static public void start_database() {
        Database.setSource("testdatabase.db");
        Database.initialization();
    }

    @AfterClass
    static public void set_source() {
        Database.setSource("database.db");
    }

    @After
    public void drop_database() {
        Database.dropDatabase();
    }

    @Test
    public void test_client_creation() {
        try {
            Client client = new Client("Alesqui", "admin", "admin", "rua");
            Database.addClient(client);
            List<Client> clients = Database.getClients();
            assertEquals(1, clients.size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_product_creation() {
        Product product = new Product("Patin", 130);
        try {
            Database.addProduct(product);
            List<Product> products = Database.getProducts();
            assertEquals(1, products.size());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_client_get_name() {
        try {
            Client client = new Client("Alesqui", "admin", "admin", "rua");
            String name = client.getName();
            assertEquals("Alesqui", name);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
