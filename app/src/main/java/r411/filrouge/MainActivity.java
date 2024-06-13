package r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static String comment;

    public static String getComment() {
        return comment;
    }

    public static void setComment(String comment) {
        MainActivity.comment = comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProductListAdapter adapter = new ProductListAdapter(this);

        // Animation du logo Zonama
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        float startTranslationX = -screenWidth;
        float endTranslationX = screenWidth;
        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.logo), "translationX", startTranslationX, endTranslationX);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Product>> call = apiService.getProducts();

        Button buttonSendComment = findViewById(R.id.commentButton);
        if (getIntent().hasExtra("comment")) {
            String comment = getIntent().getStringExtra("comment");
            this.comment=comment;
        }

        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("comment", comment);
                v.getContext().startActivity(intent);


            }
        });


        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    OnSaleProductList onSaleProductList = OnSaleProductList.getInstance();
                    List<Product> productList = response.body();
                    for (Product product : productList) {
                        onSaleProductList.addProduct(product);
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_product_list, ProductListFragment.newInstance())
                            .commit();
                } else {
                    Toast.makeText(MainActivity.this, "Erreur de chargement des produits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("MainActivity", "Erreur de requête API", t);
                Toast.makeText(MainActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
            }
        });

        // Passer au panier d'achat
        findViewById(R.id.button_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Passer aux produits likés
        findViewById(R.id.button_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LikedItemsActivity.class);
                startActivity(intent);
            }
        });
    }
}

