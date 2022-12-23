package com.example.javaeats;

public enum FoodItem {
    CHICKENBURGER("Chicken Burger",1,150),
    ZINGERBURGER("Zinger Burger", 1, 250) ,
    BEEFBURGER("Beef Burger", 1,420),
    DOUBLEBEEFBURGER("Double Beef Burger", 1,750),
    ANDASHAMI("Anda Shami",1,100),
    ZINGERTOWER("Zinger Tower",1,650),
    CHICKENSHWARMA("Chicken Shwarma",1,120),
    ZINGERATHA("Zingeratha",1,300),
    BBQSHWARMA("BBQ Shwarma",1,180),
    SHWARMAPLATTER("Shwarma Platter",1,350),
    CHEESYSHWARMA("Cheesy Shwarma",1,160),
    BONELESSHANDI("Boneless Handi -1kg",1,1750),
    CHICKENKARAHI("Chicken Karahi -1kg",1,1600),
    SEEKHKABAB("Seekh Kabab",1,90),
    MALAIBOTI("Malai Boti",1,200),
    NAAN("Naan / Kulcha",1,35),
    ROTI("Saada Roti",1,20),
    MASALAFRIES("Masala Fries",1,100),
    LOADEDFRIES("Loaded Fries",1,180),
    EXTRACHEESE("Extra Cheese",1,40),
    MAYOCOLESLAW("Mayo Coleslaw",1,80),
    GARLICMAYODIP("Garlic Mayo Dip",1,40),
    COCACOLA("Coca Cola 500mg",1,80),
    SPRITE("Sprite 500mg",1,80),
    MIRINDA("Mirinda 500mg",1,80),
    MINTMARGARITA("Mint Margarita",1,120),
    CHAI("Special Chai",1,50),
    BEEFCOMBO("Beef Combo",1,990),
    PATTYCOMBO("Patty Combo",1,850),
    MIGHTYCOMBO("Mighty Combo",1,1550),
    SHWARMACOMBO("Shwarma Combo",1,400),
    BBQPLATTER("BBQ Platter",1,1600),
    ;
    String name;
    int quantity;
    double price;
    FoodItem(String name ,int quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    public void increaseQuantity(){
        this.quantity++;
    }

    public double getPrice(){
        return this.quantity * this.price;
    }
}
