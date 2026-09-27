//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.*;

public class ElectronicStore {
    private String name;
    //stock stored as a map to be able to easily access each item's quantity
    private Map<Product, Integer> stock;
    private double revenue;
    private int sales;


    public ElectronicStore(String initName) {
        this.revenue = 0.0;
        this.name = initName;
        this.stock = new HashMap<>();
        this.sales = 0;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public List<Product> getStockList() {
        return new ArrayList<>(stock.keySet());
    }

    public Map<Product, Integer> getStockMap() {
        return stock;
    }
    public void setStockMap(Product p, int s) {
        stock.put(p, s);
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int s) {
        sales = s;
    }

    public double getRevenue() {
        return revenue;
    }
    public void setRevenue(double s) {
        revenue = s;
    }

    //Adds a product to the map
    public boolean addProduct(Product newProduct) {
            stock.put(newProduct, newProduct.getStockQuantity());
            return true;
    }

    //Makes a list of the 3 most popular products
    public List<Product> getPopularProducts() {
        List<Product> oldProducts = new ArrayList<>(getStockList());
        List<Product> popularProducts = new ArrayList<>();

        for (int i = 0 ; i < 3 ; i++) {
            Product popular = oldProducts.getFirst();
            for (Product p : oldProducts) {
                if (p.getSoldQuantity() > popular.getSoldQuantity()) {
                    popular = p;
                }
            }
            popularProducts.add(popular);

            oldProducts.remove(popular);
        }

        return popularProducts;
    }

    //Creates temporary list for a client-side view of the stock
    //Accounts for items which are in the cart, but not sold
    public List<Product> getTempList(Cart cart) {
        int cartItem;
        List<Product> tempList = new ArrayList<>();

        for (Product s : this.getStockList()) {
            if (cart.getCartList().contains(s)) {
                cartItem = cart.getProductAmount(s);
            } else {
                cartItem = 0;
            }

            if (this.getStockMap().get(s) - cartItem > 0) {
                tempList.add(s);
            }
        }
        return tempList;
    }

    //Gets $ earned per sale on average
    public double getRatio() {
        if (sales == 0) {
            return 0;
        }
        return revenue / sales;
    }

    //Creates store
    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 2, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
}