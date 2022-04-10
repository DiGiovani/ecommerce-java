import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import database.Database;
import database.models.*;

public class Main {
    private static Client currentClient = null;
    private static String clientName = "Visitante";

    public static void main(String[] args) {
        Database.initialization();
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Banheira", 1500));
        products.add(new Product("Patinho de borracha", 20));
        products.add(new Product("Notebook da Xuxa", 200));
        products.add(new Product("Fantasia do Homem Aranha", 120));
        products.add(new Product("Dipirona", 10));
        products.add(new Product("TV DE TUBO 29\" + CONVERSOR ANALOGICO + ADAPTADOR HDMI", 300));
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
        while (true) {
            if (currentClient != null) {
                clientName = currentClient.getName();
            } else {
                clientName = "Visitante";
            }
            menuSelect();
        }

    }

    public static void menuSelect() {
        Scanner sc1 = new Scanner(System.in);
        clearConsole();

        System.out.println("\nBem vindo, " + clientName + "!");
        System.out.println("\n====== SALES ======");
        System.out.println(
                "[1] Cliente" + "\n[2] Produtos" + "\n[3] Pedido" + "\n[4] Carrinho de compras" + "\n[5] Sair");
        int op = sc1.nextInt();

        switch (op) {
            case 1:
                menuCustomer();
                break;
            case 2:
                menuProduct();
                break;
            case 3:
                menuOrders();
                break;
            case 4:
                menuCartProduct();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.print("Opcao invalida!");
                menuSelect();
                break;
        }

        close(sc1);
    }

    private static void menuProduct() {
        Scanner sc1 = new Scanner(System.in);
        try {
            List<Product> products = Database.getProducts();
            clearConsole();

            if (currentClient == null) {
                System.out.println("Você precisa estar logado para acessar essa parte.");
                System.out.println("\n======CARRINHO DE COMPRAS======");
                System.out.println("[1] Área do Cliente" + "\n[2] Menu Inicial" + "\n[3] Sair");
                int op = sc1.nextInt();
                switch (op) {
                    case 1:
                        menuCustomer();
                        break;
                    case 2:
                        menuSelect();
                        break;
                    case 3:
                        System.exit(0);

                }
                close(sc1);
                return;
            }

            System.out.println("\nBem vindo, " + clientName + "!");
            System.out.println("\n====== PRODUCTS ======");
            System.out.println("\nSelecione um produto para adicionar ao carrinho");
            System.out.println("\n======================");
            for (int index = 0; index < products.size(); index++) {
                System.out.println(index + " - " + products.get(index).getName());
            }

            Integer productIndex = sc1.nextInt();
            Database.addProductToCart(products.get(productIndex), currentClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        clearConsole();
        System.out.println("\n====== PRODUCTS ======");
        System.out.println(
                "[1] Adicionar mais produtos" + "\n[2] Finalizar pedido" + "\n[3] Sair");
        int op = sc1.nextInt();

        switch (op) {
            case 1:
                menuProduct();
                break;
            case 2:
                menuCartProduct();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.print("Opcao invalida!");
                menuSelect();
                break;
        }

        close(sc1);
    }

    public static void menuCustomer() {
        Scanner sc1 = new Scanner(System.in);
        clearConsole();

        System.out.println("\n======CLIENTES======");
        System.out.println(
                "[1] Cadastrar cliente" + "\n[2] Login" + "\n[3] Voltar" + "\n[4] Sair");
        int op = sc1.nextInt();

        switch (op) {
            case 1:
                createCustomer();
                break;
            case 2:
                authenticateCustomer();
                break;
            case 3:
                menuSelect();
            case 4:
                System.exit(0);
                break;
            default:
                System.out.print("Opcao invalida!");
                break;
        }

        close(sc1);
    }

    private static void createCustomer() {
        Scanner sc1 = new Scanner(System.in);
        clearConsole();

        Client client = new Client();

        System.out.println("Nome:");
        client.setName(sc1.nextLine());
        System.out.println("Endereco:");
        client.setAddress(sc1.nextLine());
        System.out.println("Email:");
        client.setEmail(sc1.nextLine());
        System.out.println("Senha de acesso:");
        client.setPassword(sc1.nextLine());
        try {
            Database.addClient(client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        close(sc1);
        menuCustomer();
    }

    private static void menuCartProduct() {
        Scanner sc1 = new Scanner(System.in);
        clearConsole();
        if (currentClient == null) {
            System.out.println("Você precisa estar logado para acessar essa parte.");
            System.out.println("\n======CARRINHO DE COMPRAS======");
            System.out.println("[1] Área do Cliente" + "\n[2] Menu Inicial" + "\n[3] Sair");
            int op = sc1.nextInt();
            switch (op) {
                case 1:
                    menuCustomer();
                    break;
                case 2:
                    menuSelect();
                    break;
                case 3:
                    System.exit(0);

            }
            close(sc1);
            return;
        }
        try {
            Cart cart = Database.getCart(currentClient);
            List<Product> products = Database.getProductsFromCart(cart);
            clearConsole();
            System.out.println("\n======CARRINHO DE COMPRAS======");

            for (int i = 0; i < products.size(); i++) {
                System.out.println(i + " - " + products.get(i).getName());
            }

            System.out.println("[1] Adicionar outros produtos" + "\n[2] Remover produto" + "\n[3] Finalizar pedido"
                    + "\n[4] Sair");
            int op = sc1.nextInt();

            switch (op) {
                case 1:
                    menuProduct();
                    break;
                case 2:
                    deleteProduct(products);
                    break;
                case 3:
                    createOrder();
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.print("Opcao invalida!");
                    menuCartProduct();
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        close(sc1);
    }

    private static void deleteProduct(List<Product> products) {
        Scanner sc1 = new Scanner(System.in);

        clearConsole();
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + " - " + products.get(i).getName());
        }
        System.out.println("\n===================");
        System.out.println("\nEscolha o item a ser excluído");
        int indexProduto = sc1.nextInt();
        try {
            Database.removeProductFromCart(products.get(indexProduto), currentClient);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        close(sc1);
        menuCartProduct();
    }

    private static void menuOrders() {
        Scanner sc1 = new Scanner(System.in);
        clearConsole();
        if (currentClient == null) {
            System.out.println("Você precisa estar logado para acessar essa parte.");
            System.out.println("\n======CARRINHO DE COMPRAS======");
            System.out.println("[1] Área do Cliente" + "\n[2] Menu Inicial" + "\n[3] Sair");
            int op = sc1.nextInt();
            switch (op) {
                case 1:
                    menuCustomer();
                    break;
                case 2:
                    menuSelect();
                    break;
                case 3:
                    System.exit(0);

            }
            close(sc1);
            return;
        }

        System.out.println("\n======PEDIDOS======");
        System.out.println("\n[1]Finalizar pedido" + "\n[2] Visualizar pedidos fechados" + "\n[3] Sair");
        int op = sc1.nextInt();

        switch (op) {
            case 1:
                createOrder();
                break;
            case 2:
                menuClosedOrders();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.print("Opcao invalida!");
                // menuProduct();
                break;
        }

        close(sc1);

    }

    private static void createOrder() {
        try {
            Cart cart = Database.getCart(currentClient);
            Order order = new Order(cart, currentClient);
            Database.addOrder(order, currentClient);

            closedOrder(order);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void closedOrder(Order order) {
        Scanner sc1 = new Scanner(System.in);

        try {
            Cart cart = Database.getCart(order);
            List<Product> products = Database.getProductsFromCart(cart);
            clearConsole();
            System.out.println("\n======PEDIDO #" + order.getId() + "======");
            for (int i = 0; i < products.size(); i++) {
                System.out.println(i + " - " + products.get(i).getName() + "    R$" + products.get(i).getPrice());
            }

            System.out.println("======================");
            System.out.println("\n[1] Imprimir NFE" + "\n[2] Menu Inicial" + "\n[3] Sair");
            int op = sc1.nextInt();

            switch (op) {
                case 1:
                    printOrder(order);
                    break;
                case 2:
                    menuSelect();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.print("Opcao invalida!");
                    // menuProduct();
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        close(sc1);
    }

    private static void menuClosedOrders() {
        Scanner sc1 = new Scanner(System.in);

        try {
            List<Order> orders = Database.getOrders(currentClient);
            clearConsole();
            System.out.println("\n======PEDIDOS======");
            for (int i = 0; i < orders.size(); i++) {
                System.out.println(i + " - Pedido #" + orders.get(i).getId());
            }

            System.out.println("\n======SELECIONE UM PEDIDO======");
            int op = sc1.nextInt();
            closedOrder(orders.get(op));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        close(sc1);
    }

    private static void authenticateCustomer() {
        Scanner sc1 = new Scanner(System.in);

        System.out.println("Email:");
        String email = sc1.nextLine();
        System.out.println("Senha de acesso:");
        String password = sc1.nextLine();

        currentClient = Client.authenticateUser(email, password);

        close(sc1);
    }

    private static void printOrder(Order order) {
        try {
            Cart cart = Database.getCart(order);
            List<Product> products = Database.getProductsFromCart(cart);
            clearConsole();
            String writePATH = new File("./Pedido #" + order.getId() + ".txt").getCanonicalPath();
            File arquivo_saida = new File(writePATH);
            FileWriter fw = new FileWriter(arquivo_saida);
            PrintWriter writer = new PrintWriter(fw);

            writer.println("========= NOTA FISCAL =========");
            writer.println("Cliente: " + currentClient.getName());
            writer.println("Endereco: " + currentClient.getAddress());
            writer.println("=========== PEDIDO ============");
            for (int i = 0; i < products.size(); i++) {
                writer.println(i + " - " + products.get(i).getName() + "    R$" + products.get(i).getPrice());
            }
            writer.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void close(Scanner sc1) {
    }

}
