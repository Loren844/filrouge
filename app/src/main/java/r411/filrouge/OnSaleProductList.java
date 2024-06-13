package r411.filrouge;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class OnSaleProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private static OnSaleProductList instance;
    private List<Product> onSaleProductList;

    private OnSaleProductList() {
        onSaleProductList = new ArrayList<>();
    }

    public static synchronized OnSaleProductList getInstance() {
        if (instance == null) {
            instance = new OnSaleProductList();
        }
        return instance;
    }

    public void addProduct(Product product) {
        onSaleProductList.add(product);
    }

    public List<Product> getUserList() {
        return onSaleProductList;
    }
}
