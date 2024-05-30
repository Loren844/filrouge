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
import java.util.ArrayList;
import java.util.List;

import r411.filrouge.Product;
import r411.filrouge.ProductListAdapter;
import r411.filrouge.R;

public class ProductListFragment extends Fragment {

    private List<Product> productList;

    public ProductListFragment() {
        // Constructeur vide requis
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
            productList = (List<Product>) getArguments().getSerializable("productList");
        } else {
            productList = new ArrayList<>(); // Initialiser productList pour éviter NullPointerException
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductListAdapter adapter = new ProductListAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
