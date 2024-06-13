package r411.filrouge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;
    OnSaleProductList onSaleProductList;

    public ProductListAdapter(Context context) {
        this.context = context;
        this.onSaleProductList = OnSaleProductList.getInstance();
        this.productList = onSaleProductList.getUserList();
    }

    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>(); // Éviter NullPointerException
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.title.setText(product.getTitle());
        holder.price.setText(String.valueOf(product.getPrice()) + " €");
        holder.rating.setText(String.valueOf("Note moyenne : " + product.getRating().getRate()));
        holder.count.setText(String.valueOf(product.getRating().getCount()) + " avis");
        String description = product.getDescription();
        int maxLength = 100;
        if (description.length() > maxLength) {
            int endIndex = maxLength;
            while (endIndex < description.length() && description.charAt(endIndex) != '.' && description.charAt(endIndex) != ',') {
                endIndex++;
            }
            if (endIndex < description.length()) {
                endIndex++;
            }
            description = description.substring(0, endIndex);
        }
        holder.description.setText(description);
        Picasso.get().load(product.getImage()).into(holder.image);

        //si le produit est déjà liké, on change l'icone
        if(LikedItems.getInstance().isProductLiked(product)) {
            holder.like_button.setBackground(context.getDrawable(R.drawable.liked_fill));
        } else {
            holder.like_button.setBackground(context.getDrawable(R.drawable.liked));
        }

        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int newCount = product.getRating().getCount();
                double newAvgRating;

                if(!product.isRated()) {
                    newCount = product.getRating().getCount() + 1;
                    holder.count.setText(String.valueOf(newCount) + " avis");
                    product.setRate((int) rating);
                    newAvgRating = ((product.getRating().getRate() * product.getRating().getCount()) + rating) / newCount;
                } else {
                    newAvgRating = ((product.getRating().getRate() * product.getRating().getCount()) - product.getRate() + rating) / newCount;
                    product.setRate((int) rating);
                }
                holder.rating.setText(String.format(Locale.getDefault(), "Note moyenne : %.1f", newAvgRating));
                newAvgRating = Math.round(newAvgRating * 10) / 10.0;
                product.getRating().setRate(newAvgRating);
                product.getRating().setCount(newCount);
            }
        });

        holder.ratingBar.setRating((float) product.getRate());

        //si l'activité est CartActivity, on cache certaines infos et on affiche d'autres
        if(context.getClass().getSimpleName().equals("CartActivity")) {
            holder.add_to_cart_button.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.GONE);
            holder.rating.setVisibility(View.GONE);
            holder.count.setVisibility(View.GONE);
            holder.delete_to_cart_button.setVisibility(View.VISIBLE);
            holder.nb_product_in_cart.setVisibility(View.VISIBLE);
            holder.nb_product_in_cart.setText("Nombre de produits : " + String.valueOf(Cart.getInstance().getProductList().get(product)));
        } else {
            holder.delete_to_cart_button.setVisibility(View.GONE);
            holder.nb_product_in_cart.setVisibility(View.GONE);
        }

        holder.add_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToCartButtonClicked(product);
            }
        });

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (LikedItems.getInstance().isProductLiked(product)) {
                    LikedItems.getInstance().removeProduct(product);
                    holder.like_button.setBackground(context.getDrawable(R.drawable.liked));
                    //si l'activité est LikedItemsActivity, on supprime le produit de la liste
                    if(context.getClass().getSimpleName().equals("LikedItemsActivity")) {
                        productList.remove(product);
                        notifyDataSetChanged();
                    }
                } else {
                    LikedItems.getInstance().addProduct(product);
                    holder.like_button.setBackground(context.getDrawable(R.drawable.liked_fill));
                }
            }
        });

        holder.delete_to_cart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Sélectionner la quantité à supprimer");

                // Créer le NumberPicker
                final NumberPicker numberPicker = new NumberPicker(context);
                numberPicker.setMinValue(1); // Valeur minimale (1)
                numberPicker.setMaxValue(Cart.getInstance().getProductList().get(product)); // Valeur maximale
                numberPicker.setValue(1); // Valeur par défaut

                // Ajouter le NumberPicker au dialogue
                builder.setView(numberPicker);

                // Boutons pour confirmer ou annuler
                builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int quantityToRemove = numberPicker.getValue();
                        if(quantityToRemove == Cart.getInstance().getProductList().get(product)) {
                            productList.remove(product);
                            Cart.getInstance().removeProduct(product);
                        } else {
                            Cart.getInstance().setProductQuantity(product, quantityToRemove);
                        }
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Afficher le dialogue
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void onAddToCartButtonClicked(Product product) {
        Cart.getInstance().addProduct(product, 1);
        Toast.makeText(this.context, "Produit ajouté au panier", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, rating, count, description;
        ImageView image;
        RatingBar ratingBar = itemView.findViewById(R.id.product_ratingBar);
        Button add_to_cart_button = itemView.findViewById(R.id.add_to_cart_button);
        Button delete_to_cart_button = itemView.findViewById(R.id.delete_to_cart_button);
        ImageButton like_button = itemView.findViewById(R.id.like_button);
        TextView nb_product_in_cart = itemView.findViewById(R.id.nb_product_in_cart);

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            image = itemView.findViewById(R.id.product_image);
            rating = itemView.findViewById(R.id.product_avgRating);
            count = itemView.findViewById(R.id.product_count);
            description = itemView.findViewById(R.id.product_description);
        }
    }
}
