package com.example.javaeats;

public class CartEntry {
    private FoodItem foodItem;
    private int quantity;

    public CartEntry(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(){
        this.quantity++;
    }

    public void decreaseQuantity(){
        if(this.quantity >0) {
            this.quantity--;
        }
    }
}
