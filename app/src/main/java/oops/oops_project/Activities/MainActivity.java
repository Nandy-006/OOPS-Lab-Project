package oops.oops_project.Activities;

import oops.oops_project.AuthActivities.EmailPasswordActivity;
import oops.oops_project.AuthActivities.GoogleSignInActivity;
import oops.oops_project.AuthActivities.ManageUserActivity;
import oops.oops_project.R;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{


    static boolean loginClicked;
    // tdfsgsfdgsfgjshfljfs

    /*FirebaseAuth mAuth;

    private static final int RC_SIGN_IN = 123;


    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        createSignInIntent();
        setContentView(R.layout.activity_main);
        Intent launchSecondActiivity = new Intent(this, UserDetailsEntry.class);
        startActivity(launchSecondActiivity);



    }


     */

    private static final Class[] CLASSES = new Class[]{
            EmailPasswordActivity.class,
            GoogleSignInActivity.class,
            //FacebookLoginActivity.class,
           // AnonymousAuthActivity.class,
            //PhoneAuthActivity.class,
            ManageUserActivity.class

    };

    private static final String[] items = new String[]{"Email/Password", "Google Sign in", "Manage User"};
    private static int ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        loginClicked = intent.getBooleanExtra("LoginClicked", true);
        //Toast.makeText(getApplicationContext(), Boolean.toString(loginClicked), Toast.LENGTH_SHORT).show();

        ListView listView = findViewById(R.id.list_view);

        MyArrayAdapter adapter = new MyArrayAdapter(this, android.R.layout.simple_list_item_1, CLASSES){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                String text = tv.getText().toString();



                if(text .equals( "EmailPasswordActivity") ) {
                    tv.setText("Sign in through email");
                    //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                else if(text .equals( "GoogleSignInActivity") ){
                    tv.setText("Sign in through Google");
                    //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                else {
                    tv.setText("Edit account details");
                    //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getApplicationContext(), printed, Toast.LENGTH_SHORT).show();
                /*if(ctr++ == 0) {
                    tv.setText("Sign in through email");
                    Toast.makeText(getApplicationContext(), Integer.toString(ctr), Toast.LENGTH_SHORT).show();
                }
                else if(ctr++ == 1) {
                    tv.setText("Sign in through Google");
                    Toast.makeText(getApplicationContext(), Integer.toString(ctr), Toast.LENGTH_SHORT).show();
                }
                else {
                    tv.setText("Edit account details");
                    Toast.makeText(getApplicationContext(), Integer.toString(ctr), Toast.LENGTH_SHORT).show();
                }*/

                tv.setTextColor(Color.WHITE);
                tv.setTextSize(24);
                

                // Generate ListView Item using TextView
                return view;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clicked = CLASSES[position];
        Intent intent = new Intent(this, clicked);
        intent.putExtra("LoginClicked", loginClicked);
        startActivity(intent);
    }

    private static class MyArrayAdapter extends ArrayAdapter<Class> {
        private Context mContext;
        private Class[] mClasses;

        private MyArrayAdapter(Context context, int resource, Class[] objects) {
            super(context, resource, objects);
            mContext = context;
            mClasses = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            }
            ((TextView) view.findViewById(android.R.id.text1)).setText(mClasses[position].getSimpleName());

            return view;
        }
    }

}