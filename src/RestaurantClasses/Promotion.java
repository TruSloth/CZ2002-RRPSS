package RestaurantClasses;

import java.util.ArrayList;

public class Promotion {
    private ArrayList<MenuItem> itemsServed;
    private double price;

    public Promotion(ArrayList<MenuItem> itemsServed, double price) {
        this.itemsServed = itemsServed;
        this.price = price;
    }

    public void addPromotionItem(MenuItem promoItem) {
        itemsServed.add(promoItem);
    }

    public void deletePromotionItem(MenuItem promoItem) {
        itemsServed.remove(promoItem);
    }

    public void printPromotion() {
        for (MenuItem promoItem : itemsServed) {
            System.out.printf("%s ", promoItem.getName());
        }
        System.out.println();
    }
}
