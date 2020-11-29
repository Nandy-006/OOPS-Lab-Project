package oops.oops_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import oops.oops_project.FirestoreDatabase.Category;
import oops.oops_project.FirestoreDatabase.Item;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.StRef;
import static oops.oops_project.Activities.DashboardActivity.Storage;
import static oops.oops_project.Activities.DashboardActivity.db;

public class ItemActivity extends AppCompatActivity {
    public static final String PATH = "path";
    String doc;

    ImageView itemImage;
    TextView itemName, itemDescription, itemQuantity;
    FloatingActionButton updateQuantityFab, deleteItemFab, editItemFab, menuFab;
    LinearLayout updateQuantityLayout, deleteItemLayout, editItemLayout;
    View fabBGLayout;
    String cur_title, cur_desc, cur_image;
    int cur_quantity;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        doc = getIntent().getStringExtra(PATH);

        itemImage = findViewById(R.id.item_image);
        itemName = findViewById(R.id.item_title);
        itemDescription = findViewById(R.id.item_description);
        itemQuantity = findViewById(R.id.quantity_value);

        updateQuantityFab = findViewById(R.id.update_quantiy_fab);
        deleteItemFab = findViewById(R.id.delete_item_fab);
        editItemFab = findViewById(R.id.edit_item_fab);
        menuFab = findViewById(R.id.category_fab);

        updateQuantityLayout = findViewById(R.id.update_quantiy_Layout);
        deleteItemLayout = findViewById(R.id.delete_item_Layout);
        editItemLayout = findViewById(R.id.edit_item_Layout);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        db().document(doc).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Item item = documentSnapshot.toObject(Item.class);
                        String title = item.getTitle();
                        String desc = item.getDescription();
                        int quantity = item.getQuantity();

                        itemName.setText(title);
                        itemDescription.setText(desc);
                        if(item.getImage() != null)
                            Picasso.get().load(item.getImage()).fit().centerCrop().into(itemImage);
                        else
                            itemImage.setImageResource(R.drawable.item);
                        itemQuantity.setText(String.valueOf(quantity));

                        cur_title = title;
                        cur_desc = desc;
                        cur_quantity = quantity;
                        cur_image = item.getImage();
                    }
                });
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        menuFab.animate().rotation(0);
        updateQuantityLayout.animate().translationY(0);
        editItemLayout.animate().translationY(0);
        deleteItemLayout.animate().translationY(0);
        deleteItemLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    updateQuantityLayout.setVisibility(View.GONE);
                    editItemLayout.setVisibility(View.GONE);
                    deleteItemLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void showFABMenu() {
        isFABOpen = true;
        updateQuantityLayout.setVisibility(View.VISIBLE);
        editItemLayout.setVisibility(View.VISIBLE);
        deleteItemLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        menuFab.animate().rotationBy(180);
        deleteItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        editItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        updateQuantityLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    public void updateQuantity(View view) {
        Log.d("UPDATE", "ENTERED");
        View input = LayoutInflater.from(this).inflate(R.layout.update_quantity_layout, null);
        ((EditText) input.findViewById(R.id.update_quantiy_value)).setText(String.valueOf(cur_quantity));

        MaterialAlertDialogBuilder updateDialog = new MaterialAlertDialogBuilder(this);
        updateDialog.setTitle("Enter new quantity");
        updateDialog.setView(input);
        updateDialog.setNeutralButton("CANCEL", null);
        updateDialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int quantity = Integer.parseInt(((EditText) input.findViewById(R.id.update_quantiy_value)).getText().toString());

                    itemQuantity.setText(String.valueOf(quantity));
                    cur_quantity = quantity;

                    db().document(doc).update("quantity", quantity)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Quantity updated!", Toast.LENGTH_SHORT).show();
                                    closeFABMenu();
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Enter valid quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateDialog.show();
    }

    public void editItem(View view) {
        View input = LayoutInflater.from(this).inflate(R.layout.edit_item_layout, null);
        ((EditText) input.findViewById(R.id.add_item_title)).setText(cur_title);
        ((EditText) input.findViewById(R.id.add_item_description)).setText(cur_desc);

        MaterialAlertDialogBuilder editDialog = new MaterialAlertDialogBuilder(this);
        editDialog.setTitle("Edit Item");
        editDialog.setView(input);
        editDialog.setNeutralButton("CANCEL", null);
        editDialog.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText) input.findViewById(R.id.add_item_title)).getText().toString();
                String desc = ((EditText) input.findViewById(R.id.add_item_description)).getText().toString();

                if (title.equals(""))
                    Toast.makeText(getApplicationContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
                else {
                    itemName.setText(title);
                    itemDescription.setText(desc);
                    cur_title = title;
                    cur_desc = desc;

                    db().document(doc).update("title", title, "description", desc)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Item edited!", Toast.LENGTH_SHORT).show();
                                    closeFABMenu();
                                }
                            });
                }
            }
        });
        editDialog.show();
    }

    public void deleteItem(View view) {
        MaterialAlertDialogBuilder deleteDialog = new MaterialAlertDialogBuilder(this);
        deleteDialog.setTitle("Are you sure you want to delete this item ?");
        deleteDialog.setNeutralButton("CANCEL", null);
        deleteDialog.setNegativeButton("NO", null);
        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    db().document(doc).delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Item deleted!", Toast.LENGTH_SHORT).show();
                                    closeFABMenu();
                                    finish();
                                }
                            });
            }
        });
        deleteDialog.show();
    }

    public void onClickMenuFab(View view) {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }
}