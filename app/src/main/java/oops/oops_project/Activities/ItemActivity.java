package oops.oops_project.Activities;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import oops.oops_project.FirestoreDatabase.Item;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.db;

public class ItemActivity extends AppCompatActivity {
    public static final String PATH = "path";
    String doc;

    ImageView itemImage;
    TextView itemName, itemDescription, itemQuantity;
    FloatingActionButton updateQuantityFab, deleteItemFab, editItemFab, shareItemFab, menuFab;
    LinearLayout updateQuantityLayout, deleteItemLayout, editItemLayout, shareItemLayout;
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
        shareItemFab = findViewById(R.id.share_item_fab);
        menuFab = findViewById(R.id.category_fab);

        updateQuantityLayout = findViewById(R.id.update_quantiy_Layout);
        deleteItemLayout = findViewById(R.id.delete_item_Layout);
        editItemLayout = findViewById(R.id.edit_item_Layout);
        shareItemLayout = findViewById(R.id.share_item_fabLayout);
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
        shareItemLayout.animate().translationY(0);
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
                    shareItemLayout.setVisibility(View.GONE);
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
        shareItemLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        menuFab.animate().rotationBy(180);
        deleteItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        editItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        updateQuantityLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        shareItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_190));
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

    public void shareItem(View view)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        StringBuilder message = new StringBuilder("");
        message.append("Hey! Here's an item of my inventory : \n\n");
        message.append("Item Name: " + cur_title + "\nItem Description: " + cur_desc + "\nQuantity :" + cur_quantity);
        message.append("\n\nSent via Taskete :D");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message.toString());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

       /* db().collection(doc).orderBy("quantity", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots)
                        {
                            Item item = documentSnapshot.toObject(Item.class);
                            String title = item.getTitle();
                            int quantity = item.getQuantity();
                            message.append("-> " + title + " --- " + quantity + " units\n");

                        }
                        message.append("\n\nSent via Taskete :D");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message.toString());
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Could not retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
*/


    }
}