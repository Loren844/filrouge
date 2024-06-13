package r411.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CommentActivity extends AppCompatActivity {

    private EditText editTextComment;
    private Button buttonSendComment;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments);

        editTextComment = findViewById(R.id.editTextComment);
        buttonSendComment = findViewById(R.id.buttonSendComment);

        if (getIntent().hasExtra("comment")) {
            String comment = getIntent().getStringExtra("comment");

            if(comment!=null) {

                TextView textView = findViewById(R.id.existingComment);
                textView.setText(comment);
            }

        }
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
        findViewById(R.id.button_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCart(view);
            }
        });

        buttonSendComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String comment = editTextComment.getText().toString().trim();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("comment", comment);
                v.getContext().startActivity(intent);
            }
        });

    }
    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToLiked(View view) {
        Intent intent = new Intent(this, LikedItemsActivity.class);
        startActivity(intent);
    }
    public void goToCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}
