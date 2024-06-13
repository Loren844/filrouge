package r411.filrouge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LikedItemsActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_items);

        //Récupérer le panier
        List<Product> likedItems = LikedItems.getInstance().getLikedItems();

        //Afficher le panier dans la product list fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_product_list, ProductListFragment.newInstance(likedItems))
                .commit();

        // Listener pour le bouton de retour à l'accueil
        findViewById(R.id.button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(view);
            }
        });

        // Listener pour passer au panier
        findViewById(R.id.button_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCart(view);
            }
        });
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
