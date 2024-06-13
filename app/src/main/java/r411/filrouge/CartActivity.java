package r411.filrouge;

import static android.content.Intent.getIntent;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "validation_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Récupérer le panier
        HashMap<Product, Integer> cart = Cart.getInstance().getProductList();

        List<Product> productList = new ArrayList<>();
        //Faire une liste des produits
        for (Product product : cart.keySet()) {
            productList.add(product);
        }

        //Afficher le panier dans la product list fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_product_list, ProductListFragment.newInstance(productList))
                .commit();

        //Listener pour le bouton de validation du panier
        findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateCart(view);
            }
        });

        //Listener pour le bouton de retour à l'accueil
        findViewById(R.id.button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome(view);
            }
        });

        // Listener pour passer aux produits likés
        findViewById(R.id.button_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLiked(view);
            }
        });

        //Listener pour vide le panier
        findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearCart(view);
            }
        });
    }


    public void onValidateCart(View view) {
        if (Cart.getInstance().getProductCount() == 0) {
            Toast.makeText(this, "Le panier est vide", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // Récupérer le panier
            HashMap<Product, Integer> cart = Cart.getInstance().getProductList();

            // Vider le panier
            Cart.getInstance().clearCart();

            // Afficher un message de confirmation avec un Toast
            Toast.makeText(this, "Votre commande a bien été validée", Toast.LENGTH_SHORT).show();

            // Envoyer une notification
            sendNotification();

            // Revenir à l'activité précédente
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Méthode pour envoyer une notification de validation de commande
    private void sendNotification() {
        // Créer un canal de notification pour Android Oreo et versions ultérieures
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Validation Channel";
            String description = "Canal pour les notifications de validation de commande";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Créer la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.cart)
                .setContentTitle("Commande Validée")
                .setContentText("Votre commande a bien été validée")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Afficher la notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Auto-generated method stub
            return;
        }
        notificationManagerCompat.notify(1, builder.build());
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToLiked(View view) {
        Intent intent = new Intent(this, LikedItemsActivity.class);
        startActivity(intent);
    }

    public void onClearCart(View view) {
        if(Cart.getInstance().getProductCount() == 0) {
            Toast.makeText(this, "Le panier est déjà vide", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cart.getInstance().clearCart();
            Toast.makeText(this, "Panier vidé", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
