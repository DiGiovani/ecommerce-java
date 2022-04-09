import java.util.ArrayList;
import java.util.List;

import database.Database;
import database.models.Product;

public class Main {
    public static void main(String[] args) {
        Database.initialization();
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Banheira", 1500));
        products.add(new Product("Patinho de borracha", 20));
        products.add(new Product("Notebook da Xuxa", 200));
        products.add(new Product("Fantasia do Homem Aranha", 120));
        products.add(new Product("Dipirona", 10));
        products.add(new Product("TV DE TUBO 29\" + CONVERSOR ANALOGICO + ADAPTADOR HDMO", 300));
        products.add(new Product("Casa da Barbie", 1500));
        products.add(new Product("Barco da Poly", 500));
        products.add(new Product("Funko do Dr Estranho", 800));
        products.add(new Product("Colar", 300));

        products.forEach(product -> {
            try {
                Database.addProduct(product);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

    }
}
