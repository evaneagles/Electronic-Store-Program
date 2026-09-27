import java.util.ArrayList;
import java.util.List;

public class ElectronicStoreController {

    private ElectronicStore store;
    private Cart cart;
    private ElectronicStoreView view;
    private ElectronicStore model;
    private int selectedIndex;

    public ElectronicStoreController(ElectronicStore store, Cart cart, ElectronicStoreView view) {
        this.store = store;
        this.cart = cart;
        this.view = view;
        this.model = ElectronicStore.createStore();
        this.selectedIndex = 0;
    }

    //Main menu + view loop
    public void start() {
        while (true) {
            int choice;

            view.update(model, cart, selectedIndex);
            view.displayMenu();
            choice = view.promptMenuChoice();

            //No choice 1 because the view already displays the stock naturally
            if (choice == 2) {
                handleSelect();
            } else if (choice == 3) {
                handleAddToCart();
            } else if(choice == 4) {
                handleRemoveFromCart();
            } else if (choice == 5) {
                handleSale();
            } else if (choice == 6) {
                handleReset();
            } else if (choice == 7) {
                break;
            }
        }
    }

    //Changes selected index if it's valid
    private void handleSelect() {
        if (model.getTempList(cart).isEmpty()) {
            System.out.println("\nThere are no items to choose from\n");
        } else {
            this.selectedIndex = view.promptInt("Choose your product: ", 1, model.getTempList(cart).size()) - 1;
        }
    }

    //Adds item at selectedIndex to cart
    private void handleAddToCart() {
        if (model.getTempList(cart).isEmpty()) {
            System.out.println("\nThere are no more items left in the stock. Come back another time.\n");
        } else {
            Product selectedProduct = model.getTempList(cart).get(selectedIndex);
            if (cart.getCartList().contains(selectedProduct)) {
                if (cart.getProductAmount(selectedProduct) >= model.getStockMap().get(selectedProduct)) {
                    System.out.println("\nThis item has no more stock.\n\n");
                } else {
                    cart.addCartProduct(selectedProduct);
                }
            } else {
                cart.addCartProduct(selectedProduct);
            }
        }

        //Second condition accounts for client side edge case related to stock emptiness
        if (selectedIndex > model.getTempList(cart).size() - 1 && selectedIndex - 1 > -1) {
            selectedIndex --;
        }
    }

    //Removes product from the cart if there's anything to remove
    private void handleRemoveFromCart() {
        Product selectedProduct;

        if (cart.getCartList().isEmpty()) {
            System.out.println("The cart is empty.\n");
        } else {
            selectedProduct = cart.getCartList().get(view.promptInt("Choose the item number you wish to remove from the cart: ", 1, cart.getCurrProducts()) - 1);

            cart.setCartMap(selectedProduct, cart.getCartMap().get(selectedProduct) - 1);
        }
    }

    //Sells all products in the cart
    private void handleSale() {
        if (cart.getCartList().isEmpty()) {
            System.out.println("There are no items in the cart\n\n");
        } else {
            for (Product p : cart.getCartMap().keySet()) {
                //Adjusts the stock numbers
                model.setStockMap(p, model.getStockMap().get(p) - cart.getProductAmount(p));

                //Updates the sold quantity (which updates popularity as a result)
                p.setSoldQuantity(p.getSoldQuantity() + cart.getProductAmount(p));
            }
            //Adjusts revenue accordingly
            model.setRevenue(model.getRevenue() + cart.getCartPrice());

            //Clears... The cart...
            cart.clearCart();

            //Updates the number of sales
            model.setSales(model.getSales() + 1);
        }
    }

    //Resets the store
    private void handleReset() {
        this.model = ElectronicStore.createStore();
        cart.clearCart();
    }

}
