import java.util.Objects;

//Base class for all products the store will sell
public abstract class Product {
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        this.price = initPrice;
        this.stockQuantity = initQuantity;
        this.soldQuantity = 0;
    }

    //Getters and setters
    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }
    public void setSoldQuantity(int s) {
        soldQuantity = s;
    }

    public double getPrice() {
        return price;
    }
}