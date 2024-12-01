package edu.upc.projecte;

public class PurchaseRequest {
    private User user;
    private Item item;

    // Constructor
    public PurchaseRequest(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}