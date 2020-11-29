package oops.oops_project.Activities;

import android.animation.Animator;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import oops.oops_project.Adapters.FirebaseItemAdapter;
import oops.oops_project.FirestoreDatabase.Category;
import oops.oops_project.FirestoreDatabase.Item;
import oops.oops_project.R;

import static oops.oops_project.Activities.DashboardActivity.StRef;
import static oops.oops_project.Activities.DashboardActivity.db;

public class CategoryActivity extends AppCompatActivity {
    public static final String PATH = "path";
    private static final int PICK_IMAGE_REQUEST = 1;
    private String ITEMS_PATH;

    MaterialTextView titleText, descText;
    RecyclerView recycler;
    FloatingActionButton addItemFab, deleteCategoryFab, editCategoryFab, menuFab;
    LinearLayout addItemLayout, deleteCategoryLayout, editCategoryLayout;
    View fabBGLayout;
    String doc;
    boolean isFABOpen = false;
    String cur_title, cur_desc;

    private Uri imageUri;

    public FirebaseItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        doc = getIntent().getStringExtra(PATH);
        ITEMS_PATH = doc + "/Items";

        titleText = findViewById(R.id.category_title);
        descText = findViewById(R.id.category_description);
        recycler = findViewById(R.id.item_recycler);

        addItemFab = findViewById(R.id.add_item_fab);
        deleteCategoryFab = findViewById(R.id.delete_category_fab);
        editCategoryFab = findViewById(R.id.edit_category_fab);
        menuFab = findViewById(R.id.category_fab);

        addItemLayout = findViewById(R.id.add_item_fabLayout);
        deleteCategoryLayout = findViewById(R.id.delete_category_fabLayout);
        editCategoryLayout = findViewById(R.id.edit_category_fabLayout);
        fabBGLayout = findViewById(R.id.fabBGLayout);

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        db().document(doc)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Category category = documentSnapshot.toObject(Category.class);
                        String title = category.getTitle();
                        String desc = category.getDesc();

                        titleText.setText(title);
                        descText.setText(desc);
                        cur_title = title;
                        cur_desc = desc;
                    }
                });

        setUpActivity();
    }

    private void setUpActivity() {
        Query query = db().collection(ITEMS_PATH).orderBy("quantity", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new FirebaseItemAdapter(options);

        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setAdapter(adapter);

        adapter.setListener(new FirebaseItemAdapter.ItemListener() {
            @Override
            public void onClick(DocumentSnapshot documentSnapshot, int position) {
                Intent intent = new Intent(getApplicationContext(), ItemActivity.class);
                String doc = adapter.getDoc(position).getPath();
                intent.putExtra(PATH, doc);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void openFileChoser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    String imageUrl = null;

    public void uploadItemWithImage(String title, String desc, int quantity) {
        StorageReference fileReference = StRef().child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        fileReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        Item item = new Item(title, desc, imageUrl, quantity);
                        db().collection(ITEMS_PATH)
                                .add(item)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
                                        closeFABMenu();
                                    }
                                });
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }

    public void newItem(View view) {
        View input = LayoutInflater.from(this).inflate(R.layout.full_screen_dialog_add_item, null);
        CircleImageView imageView = input.findViewById(R.id.add_item_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChoser();
            }
        });

        MaterialAlertDialogBuilder newItemDialog = new MaterialAlertDialogBuilder(this);
        newItemDialog.setTitle("New Item");
        newItemDialog.setView(input);
        newItemDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        newItemDialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = ((EditText) input.findViewById(R.id.add_item_title)).getText().toString();

                        if (title.equals(""))
                            Toast.makeText(getApplicationContext(), "Title is empty!", Toast.LENGTH_SHORT).show();
                        else {

                            String desc = ((EditText) input.findViewById(R.id.add_item_description)).getText().toString();
                            int quantity;
                            try {
                                quantity = Integer.parseInt(((EditText) input.findViewById(R.id.item_quantity_text_view)).getText().toString());
                                if (imageUri != null)
                                    uploadItemWithImage(title, desc, quantity);
                                else {
                                    Item item = new Item(title, desc, null, quantity);

                                    db().collection(ITEMS_PATH)
                                            .add(item)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Toast.makeText(getApplicationContext(), "Item added!", Toast.LENGTH_SHORT).show();
                                                    closeFABMenu();
                                                }
                                            });
                                }
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Enter integer quantity", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        newItemDialog.show();
    }

    public void deleteCategory(View view) {
        MaterialAlertDialogBuilder deleteCategoryDialog = new MaterialAlertDialogBuilder((this));
        deleteCategoryDialog.setTitle("Are you sure you want to delete this category?");
        deleteCategoryDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteCategoryDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteCategoryDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db().document(doc).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Category deleted!", Toast.LENGTH_SHORT).show();
                                closeFABMenu();
                                finish();
                            }
                        });
            }
        });

        deleteCategoryDialog.show();
    }

    public void editCategory(View view) {
        View input = LayoutInflater.from(this).inflate(R.layout.full_screen_dialog_add_category, null);
        ((EditText) input.findViewById(R.id.add_category_title)).setText(cur_title);
        ((EditText) input.findViewById(R.id.add_category_description)).setText(cur_desc);

        MaterialAlertDialogBuilder editCategoryDialog = new MaterialAlertDialogBuilder(this);
        editCategoryDialog.setTitle("Edit Category");
        editCategoryDialog.setView(input);
        editCategoryDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        editCategoryDialog.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = ((EditText) input.findViewById(R.id.add_category_title)).getText().toString();
                if (title.equals(""))
                    Toast.makeText(getApplicationContext(), "Title is empty!", Toast.LENGTH_SHORT).show();
                else {
                    String desc = ((EditText) input.findViewById(R.id.add_category_description)).getText().toString();

                    titleText.setText(title);
                    descText.setText(desc);
                    cur_title = title;
                    cur_desc = desc;

                    db().document(doc).set(new Category(title, desc, new Date()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Category updated!", Toast.LENGTH_SHORT).show();
                                    closeFABMenu();
                                }
                            });
                }
            }
        });
        editCategoryDialog.show();
    }

    public void onClickMenuFab(View view) {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        addItemLayout.setVisibility(View.VISIBLE);
        editCategoryLayout.setVisibility(View.VISIBLE);
        deleteCategoryLayout.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        menuFab.animate().rotationBy(180);
        deleteCategoryLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        editCategoryLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        addItemLayout.animate().translationY(-getResources().getDimension(R.dimen.standard_145));

    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        menuFab.animate().rotation(0);
        addItemLayout.animate().translationY(0);
        editCategoryLayout.animate().translationY(0);
        deleteCategoryLayout.animate().translationY(0);
        deleteCategoryLayout.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    addItemLayout.setVisibility(View.GONE);
                    editCategoryLayout.setVisibility(View.GONE);
                    deleteCategoryLayout.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }
}