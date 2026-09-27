// Represents the current customer's shopping cart.
// The cart tracks products that have been selected but not yet sold.
// It should not permanently modify the store until a sale is completed.

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
    //Cart stored as a map so that there's easy access to see how much stock of each item exists
public class Cart {
    private Map<Product, Integer> productList;

    public Cart() {
        this.productList = new HashMap();
    }

    //Adds a product to the map
    public void addCartProduct(Product p) {
        if (productList.containsKey(p)) {
            productList.put(p, productList.get(p) + 1);
        } else {
            productList.put(p, 1);
        }
    }

    //getters and setters
    public List<Product> getCartList() {
        return new ArrayList<>(productList.keySet());
    }

    public Map<Product, Integer> getCartMap() {
        return productList;
    }

    public int getProductAmount(Product p) {
        return productList.get(p);
    }

    public int getCurrProducts() {
        return getCartList().size();
    }

    public void setCartMap(Product p, int s) {
        productList.put(p, s);

        if (productList.get(p) <= 0) {
            productList.remove(p);
        }
    }

    //Calculates the cart price and then returns it
    public double getCartPrice() {
        double totalPrice = 0;

        for (Product p : getCartList()) {
            totalPrice += productList.get(p) * p.getPrice();
        }

        return totalPrice;
    }

    //Clears the cart
    public void clearCart() {
        productList = new HashMap<>();
    }

}