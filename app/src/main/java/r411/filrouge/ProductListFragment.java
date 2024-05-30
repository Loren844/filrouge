package r411.filrouge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        List<Product> productList = getProductList();
        productListAdapter = new ProductListAdapter(requireContext(), productList);
        recyclerView.setAdapter(productListAdapter);

        return view;
    }

    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Product 1212454464", "$19.99", "Description 1", R.drawable.product1));
        productList.add(new Product("Product 2", "$29.99", "Description 2", R.drawable.product2));
        productList.add(new Product("Product 3", "$39.99", "Description 3", R.drawable.product3));
        return productList;
    }
}