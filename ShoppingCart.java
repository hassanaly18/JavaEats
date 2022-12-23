package com.example.javaeats;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<String, CartEntry> entries;

    public ShoppingCart() {
        this.entries = new HashMap<>();
    }

    public void addProduct(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry!= null){
            productEntry.increaseQuantity();
        }else{
            FoodItem foodItem = FoodItem.valueOf(productName);
            CartEntry entry = new CartEntry(foodItem, 1);
            entries.put(productName.toUpperCase(),entry);
        }
    }

    public void removeProduct(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry!= null){
            productEntry.decreaseQuantity();
        }
    }

    public int getQuantity(String productName){
        CartEntry productEntry = entries.get(productName.toUpperCase());
        if(productEntry!= null){
            productEntry.getQuantity();
        }
        return 0;
    }

    public double calculateTotal(){
        double total = 0;
        for(CartEntry entry: entries.values()){
            double entryCost = entry.getFoodItem().price * entry.getQuantity();
            total += entryCost;
        }
        return total;
    }
}
