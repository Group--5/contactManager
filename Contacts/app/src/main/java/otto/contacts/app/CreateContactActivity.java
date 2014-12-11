package otto.contacts.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * This activity is used to create new contacts. */
public class CreateContactActivity extends Activity {

    ImageButton b1;
    ImageButton b2;

    @Override

    /**Sets up blank create contact Activity. Also handles saving of new contact
     * @param b Standard bundle.
     * **/
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.profile_layout);

        final EditText mobilePhoneEditText = (EditText) findViewById(R.id.mobilePhoneEditText);
        final EditText homePhoneEditText = (EditText) findViewById(R.id.homePhoneEditText);
        final EditText workPhoneEditText = (EditText) findViewById(R.id.workPhoneEditText);
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);

        mobilePhoneEditText.setTextColor(getResources().getColor(R.color.red));
        homePhoneEditText.setTextColor(getResources().getColor(R.color.red));
        workPhoneEditText.setTextColor(getResources().getColor(R.color.red ));
        emailEditText.setTextColor(getResources().getColor(R.color.red));
        nameEditText.setTextColor(getResources().getColor(R.color.red));

        b1 = (ImageButton) findViewById(R.id.b1);
        b2 = (ImageButton) findViewById(R.id.b2);


        b1.setBackground(getResources().getDrawable(R.drawable.save));
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactIntermidiary inter = new ContactIntermidiary(getApplicationContext());
                inter.createContact(nameEditText.getText().toString(), mobilePhoneEditText.getText().toString(), homePhoneEditText.getText().toString(), workPhoneEditText.getText().toString(),emailEditText.getText().toString() );
                Toast toats = Toast.makeText(getApplicationContext(), "Contact created!", Toast.LENGTH_LONG);
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    /**Waits for Activity to start(generally done after onCreate). Adjusts UI after it has been
     *  rendered.(This is done to scale several buttons to fit other screen sizes
     *  **/
    @Override
    public void onStart()
    {
        super.onStart();
        b1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // Ensure you call it only once :
                b1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                LinearLayout.LayoutParams addButtonParams = (LinearLayout.LayoutParams) b1.getLayoutParams();
                LinearLayout.LayoutParams searchButtonParams = (LinearLayout.LayoutParams) b2.getLayoutParams();

                int height = b2.getMeasuredHeight();
                Log.d("Actaul Height", "IS:" + String.valueOf(height));
                addButtonParams.height = height;
                addButtonParams.width = height;
                searchButtonParams.height = height;
                searchButtonParams.width = height;

                b1.setLayoutParams(searchButtonParams);
                b2.setLayoutParams(addButtonParams);
                b2.setVisibility(View.GONE);
            }
        });



    }



}
