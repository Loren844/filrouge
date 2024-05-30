package r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_product_list, ProductListFragment.newInstance(productList))
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
    }
}

