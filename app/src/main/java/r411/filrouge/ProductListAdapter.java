package r411.filrouge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

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
        holder.description.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(holder.image);

        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int newCount = product.getRating().getCount();
                double newAvgRating;

                if(holder.rate == -1) {
                    newCount = product.getRating().getCount() + 1;
                    holder.count.setText(String.valueOf(newCount) + " avis");
                    holder.rate = (int) rating;
                    newAvgRating = ((product.getRating().getRate() * product.getRating().getCount()) + rating) / newCount;
                } else {
                    newAvgRating = ((product.getRating().getRate() * product.getRating().getCount()) - holder.rate + rating) / newCount;
                    holder.rate = (int) rating;
                }
                holder.rating.setText(String.format(Locale.getDefault(), "Note moyenne : %.1f", newAvgRating));
                product.getRating().setRate(newAvgRating);
                product.getRating().setCount(newCount);
            }
        });
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, rating, count, description;
        ImageView image;
        RatingBar ratingBar = itemView.findViewById(R.id.product_ratingBar);
        int rate = -1;

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
