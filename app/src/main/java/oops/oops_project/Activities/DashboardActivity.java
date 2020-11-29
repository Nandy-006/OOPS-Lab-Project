package oops.oops_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import oops.oops_project.FirestoreDatabase.Data;
import oops.oops_project.FirestoreDatabase.User;
import oops.oops_project.R;

public class DashboardActivity extends AppCompatActivity
{
    private int id;
    private User user;
    private final boolean register = false;

    public static FirebaseFirestore db() { return FirebaseFirestore.getInstance();}
    public static StorageReference StRef() { return FirebaseStorage.getInstance().getReference("uploads").child(getUID());}
    public static FirebaseStorage Storage() { return  FirebaseStorage.getInstance();}
    public static String getUID() { return FirebaseAuth.getInstance().getCurrentUser().getUid();}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        if(register)
        {
            addUser();
            db().collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), ("User added"), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "User not added", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void addUser()
    {
        user = new User();
        user.setName(Data.name);
        user.setEmail(Data.email);
        user.setPhone(Data.phone);
        user.setProfession(Data.profession);
    }

    private void sendIntent()
    {
        Intent intent = new Intent(this, NavigatingDashboardActivity.class);
        intent.putExtra(NavigatingDashboardActivity.CHOICE, id);
        startActivity(intent);
    }

    public void choseInventory(View view) { id = R.id.bnav_inventory; sendIntent();}
    public void choseTasks(View view) { id = R.id.bnav_tasks; sendIntent();}
    public void choseNotes(View view) { id = R.id.bnav_notes; sendIntent();}
    public void choseDiary(View view) { id = R.id.bnav_diary; sendIntent();}
}