import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ElectronicStoreConsoleView implements ElectronicStoreView {

    private Scanner scanner;

    public ElectronicStoreConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void update(ElectronicStore store, Cart cart, int selectedIndex) {
        System.out.println(renderString(store, cart, selectedIndex));
        // Display store stock, cart contents, and store statistics
    }

    //Contains everything within the store that you can see
    //Helper method for update
    private String renderString(ElectronicStore model, Cart cart, int selectedIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        int count = 0;
        String list = "";
        String locRevenue = df.format(model.getRevenue());
        String locAvgSale = df.format(model.getRatio());

        //Store statistics
        list += model.getName() + "\n";
        list += "----------------------------------\n";
        list += "Sales: " + model.getSales() + "\n";
        list += "Revenue: " + locRevenue + "\n";
        list += "Average sale cost: " + locAvgSale + "\n\n";


        //Store Stock
        list += "Store Stock:\n\n";

        //Accounting for both client and store-side emptiness
        if (model.getStockList().isEmpty() || model.getTempList(cart).isEmpty()) {
            list += "(empty)\n\n";
        }
        else {
            List<Product> tempList = model.getTempList(cart);
            for (Product s : tempList) {

                int cartProductAmount = 0;
                String pointer = "   ";
                count++;

                //The second condition accounts for an edge case related to client-side emptiness
                if (count == selectedIndex + 1 || model.getTempList(cart).size() == 1) {
                    pointer = "-> ";
                }

                if (cart.getCartList().contains(s)) {
                    cartProductAmount = cart.getProductAmount(s);
                }

                list += pointer + count + ". " + s.toString() + "\n(" + (model.getStockMap().get(s) - cartProductAmount) + " in stock)\n";
            }
            count = 0;
            list += "\n";
        }

        //Current Cart
        list += "Current Cart (" + df.format(cart.getCartPrice()) + "):\n\n";

        if (cart.getCartList().isEmpty()) {
            list += "(empty)\n";
        } else {
            for (Product p : cart.getCartList()) {
                count++;

                list += count + ". " + cart.getCartMap().get(p) + " x " + p.toString() + "\n";
            }

            count = 0;
        }

        //Popular Products (items)
        list += "\nMost Popular Items:\n\n";
        if (model.getStockList().isEmpty() && model.getSales() == 0) {
            list += "(empty)\n";
        } else {
            for (Product p : model.getPopularProducts()) {
                count ++;

                list += count + ". " + p.toString() + "  (" + p.getSoldQuantity() + " sold)\n";
            }
        }

        return list;
    }

    @Override
    public void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. View Store Stock");
        System.out.println("2. Select Product");
        System.out.println("3. Add Selected Product to Cart");
        System.out.println("4. Remove Product from Cart");
        System.out.println("5. Complete Sale");
        System.out.println("6. Reset Store");
        System.out.println("7. Quit");
    }

    @Override
    public int promptMenuChoice() {
        return promptInt("Enter choice: ", 1, 7);
    }

    @Override
    public String promptString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    @Override
    public int promptInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max)
                    return value;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}