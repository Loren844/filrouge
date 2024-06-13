package r411.filrouge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import r411.filrouge.Product;
import r411.filrouge.ProductListAdapter;
import r411.filrouge.R;

public class ProductListFragment extends Fragment {

    private List<Product> productList;
    private OnSaleProductList onSaleProductList;

    public ProductListFragment() {
        // Constructeur vide requis
    }

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putSerializable("onSaleProductList", (Serializable) OnSaleProductList.getInstance());
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstance(List<Product> productList) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putSerializable("productList", new ArrayList<>(productList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().containsKey("onSaleProductList")) {
                onSaleProductList = (OnSaleProductList) getArguments().getSerializable("onSaleProductList");
            } else {
                productList = (List<Product>) getArguments().getSerializable("productList");
            }
        } else {
            productList = new ArrayList<>();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(productList == null) {
            recyclerView.setAdapter(new ProductListAdapter(getContext()));
        } else {
            recyclerView.setAdapter(new ProductListAdapter(getContext(), productList));
        }

        return view;
    }
}
