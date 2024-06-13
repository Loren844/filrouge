package r411.filrouge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {

    private static Cart instance;
    private HashMap<Product, Integer> productList;

    // Constructeur privé pour empêcher l'instantiation
    private Cart() {
        productList = new HashMap<>();
    }

    // Méthode pour obtenir l'instance unique du singleton
    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Méthode pour ajouter un produit au panier
    public void addProduct(Product product, int quantity) {
        if(productList.containsKey(product)) {
            quantity += productList.get(product);
        }
        productList.put(product, quantity);
    }

    // Méthode pour récupérer le nombre de produits dans le panier
    public int getProductCount() {
        return productList.size();
    }

    // Méthode pour récupérer la liste des produits dans le panier
    public HashMap<Product, Integer> getProductList() {
        return new HashMap<>(productList);
    }

    // Méthode pour vider le panier
    public void clearCart() {
        productList.clear();
    }

    // Méthode pour supprimer un produit du panier
    public void removeProduct(Product product) {
        productList.remove(product);
    }

    // Méthode pour changer la quantité d'un produit dans le panier
    public void setProductQuantity(Product product, int quantity) {
        productList.put(product, quantity);
    }
}
