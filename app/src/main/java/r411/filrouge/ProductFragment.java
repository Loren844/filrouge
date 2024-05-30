package r411.filrouge;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProductFragment extends Fragment {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    private TextView productDescriptionTextView;

    public ProductFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Initialize views
        productImageView = view.findViewById(R.id.product_image);
        productNameTextView = view.findViewById(R.id.product_name);
        productPriceTextView = view.findViewById(R.id.product_price);
        productDescriptionTextView = view.findViewById(R.id.product_description);
        RatingBar rb = (RatingBar) view.findViewById(R.id.ratingBar);

        // Retrieve product data from arguments
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            String price = args.getString("price");
            String description = args.getString("description");
            int imageResourceId = args.getInt("imageResourceId");

            // Populate views with product data
            productImageView.setImageResource(imageResourceId);
            productNameTextView.setText(name);
            productPriceTextView.setText(price);
            productDescriptionTextView.setText(description);
        }

        return view;
    }
}