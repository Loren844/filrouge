package r411.filrouge;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public static ProductFragment newInstance(Product product) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("name", product.getName());
        args.putString("price", product.getPrice());
        args.putString("description", product.getDescription());
        args.putInt("imageResourceId", product.getImageResourceId());
        fragment.setArguments(args);
        return fragment;
    }
}

