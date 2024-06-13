package r411.filrouge;

import java.util.ArrayList;
import java.util.List;

public class LikedItems {
    public static LikedItems instance;
    private List<Product> likedItems;

    // Constructeur privé
    private LikedItems() {
        likedItems = new ArrayList<>();
    }

    // Méthode pour récupérer l'instance unique
    public static synchronized LikedItems getInstance() {
        if (instance == null) {
            instance = new LikedItems();
        }
        return instance;
    }

    public void addProduct(Product product) {
        likedItems.add(product);
    }

    public void removeProduct(Product product) {
        likedItems.remove(product);
    }

    public List<Product> getLikedItems() {
        return new ArrayList<>(likedItems);
    }

    public void clearLikedItems() {
        likedItems.clear();
    }

    public int getLikedItemsCount() {
        return likedItems.size();
    }

    public boolean isProductLiked(Product product) {
        return likedItems.contains(product);
    }
}
