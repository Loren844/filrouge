package r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1", "$19.99", "Description 1", R.drawable.product1));
        productList.add(new Product("Product 2", "$29.99", "Description 2", R.drawable.product2));
        productList.add(new Product("Product 3", "$39.99", "Description 3", R.drawable.product3));
    }
}

