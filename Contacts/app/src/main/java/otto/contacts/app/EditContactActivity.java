package otto.contacts.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
*This acts as both a view details activity, and an edit contacts activity.
 *
 *  */
public class EditContactActivity extends Activity {

    private Contact mContact;
    private boolean mIsNew;
    private boolean mIsEdit = false;
    private ImageButton b1;
    private ImageButton b2;

    /** This is the onCreate callback for the EditContactActivityActivity This adjusts UI, Loads
     * contact information from an intent, and handles saving as well.
     *
     **/
    @Override
    public void onCreate(Bundle b)
    {
       super.onCreate(b);

       setContentView(R.layout.profile_layout);
        Intent i = getIntent();

        final EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        final EditText mobilePhoneEditText = (EditText) findViewById(R.id.mobilePhoneEditText);
        final EditText homePhoneEditText = (EditText) findViewById(R.id.homePhoneEditText);
        final EditText workPhoneEditText = (EditText) findViewById(R.id.workPhoneEditText);
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);


        final ImageButton profPic = (ImageButton) findViewById(R.id.profPic);
        final TextView profPicSubText = (TextView) findViewById(R.id.addPicture);
        profPicSubText.setVisibility(View.GONE);
        profPic.setBackground(getResources().getDrawable(R.drawable.profile));

        nameEditText.setEnabled(false);
        mobilePhoneEditText.setEnabled(false);
        homePhoneEditText.setEnabled(false);
        workPhoneEditText.setEnabled(false);

        emailEditText.setEnabled(false);

         b1 = (ImageButton) findViewById(R.id.b1);
         b2 = (ImageButton) findViewById(R.id.b2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsEdit) {
                    Log.d("IS edit", "Before save");

                    //Then this behaves as a save button.
                    ContactIntermidiary intermidiary = new ContactIntermidiary(getApplicationContext());
//                    if(intermidiary.updateContact(nameEditText.toString(),mobilePhoneEditText.getText().toString(), homePhoneEditText.getText().toString(),workPhoneEditText.getText().toString(),emailEditText.getText().toString(),mContact.getDisplayId(),getApplicationContext()))
//                    {
//
//
//                        Log.d("IS edit","Saving");
//                    }
                    try {
                        intermidiary.updateContactHomePhone(mContact.contactID, homePhoneEditText.getText().toString(), getApplicationContext());

                        intermidiary.updateContactCellPhone(mContact.contactID, mobilePhoneEditText.getText().toString(), getParent());
                    } catch (Exception e) {
                        Log.e("OOH LAWDY", "", e);
                    }
//                   if(intermidiary.saveAContact(mContact))
//                   {}
//                    else
//                    {Log.d("Well fuck man... Its 3 in the morning", "I really don't need to be seeing these errors homie.");}
//                    Log.d("IS edit","After save");
                }
                else
                {

                    Log.d("IS edit","Phone");


                    //Then this behaves as a phone call button
                   Intent callIntent = new Intent(Intent.ACTION_CALL);
                   callIntent.setData(Uri.parse("tel:" + mContact.cellPhoneNumber));
                   startActivity(callIntent);
                }
                }
            });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsEdit)
                {
                    profPicSubText.setVisibility(View.GONE);
                    profPic.setBackground(getResources().getDrawable(R.drawable.profile));


                    b1.setBackground(getResources().getDrawable(R.drawable.callwhite3));
                    b2.setBackground(getResources().getDrawable(R.drawable.edit));
                    nameEditText.setEnabled(false);
                    mobilePhoneEditText.setEnabled(false);
                    homePhoneEditText.setEnabled(false);
                    workPhoneEditText.setEnabled(false);
                    emailEditText.setEnabled(false);


                    nameEditText.setBackgroundColor(Color.TRANSPARENT);
                    mobilePhoneEditText.setBackgroundColor(Color.TRANSPARENT);
                    homePhoneEditText.setBackgroundColor(Color.TRANSPARENT);
                    workPhoneEditText.setBackgroundColor(Color.TRANSPARENT);
                    emailEditText.setBackgroundColor(Color.TRANSPARENT);



                    mIsEdit = false;
          Log.d("~~~~~~~~~~~", "is");
                }
                else
                {
                    profPicSubText.setVisibility(View.VISIBLE);
                    profPic.setBackground(getResources().getDrawable(R.drawable.camera));


                    b2.setBackground(getResources().getDrawable(R.drawable.close));
                    b1.setBackground(getResources().getDrawable(R.drawable.save));
                    nameEditText.setEnabled(true);
                    mobilePhoneEditText.setEnabled(true);
                    homePhoneEditText.setEnabled(true);
                    workPhoneEditText.setEnabled(true);
                    emailEditText.setEnabled(true);

                    nameEditText.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    mobilePhoneEditText.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    homePhoneEditText.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    workPhoneEditText.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    emailEditText.setBackgroundColor(Color.parseColor("#EFEFEF"));


                    mIsEdit = true;

                    Log.d("~~~~~~~~~~~", "is not");
                }
            }
        });


        mIsNew = i.getBooleanExtra("isnew", false);

        mContact = (Contact) i.getSerializableExtra("contact");


        try {
            nameEditText.setText(mContact.displayName);
            mobilePhoneEditText.setText(mContact.cellPhoneNumber);
            homePhoneEditText.setText(mContact.homePhoneNumber);
            workPhoneEditText.setText(mContact.workPhoneNumber);
            emailEditText.setText(mContact.primaryEmail);
        }
        catch (Exception e)
        {}

//
//
////
//InputStream photoInputStream= b1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//    @Override
//    public void onGlobalLayout() {
//        // Ensure you call it only once :
//        b1.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//        LinearLayout.LayoutParams addButtonParams = (LinearLayout.LayoutParams) b1.getLayoutParams();
//        LinearLayout.LayoutParams searchButtonParams = (LinearLayout.LayoutParams) b2.getLayoutParams();
//
//        int height = b2.getMeasuredHeight();
//        Log.d("Actaul Height", "IS:" + String.valueOf(height));
//        addButtonParams.height = height;
//        addButtonParams.width = height;
//        searchButtonParams.height = height;
//        searchButtonParams.width = height;
//
//        b1.setLayoutParams(searchButtonParams);
//        b2.setLayoutParams(addButtonParams);
//    }
//});

   // ContactsContract.Contacts.openContactPhotoInputStream(this.getContentResolver(), Uri);
//        if(photoInputStream==null)
//            return framePhoto(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_contact_list_picture));
        try {
            Uri conUri=Uri.parse(mContact.getPhoto().photoURI);
            Log.d("()(())()", mContact.getPhoto().photoURI);

            Bitmap mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mContact.getPhoto().photoURI)); // MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mContact.getPhoto().photoURI));
            ImageView mImageView = (ImageView) findViewById(R.id.actualPic);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageBitmap(mBitmap);

        }catch (Exception e){}
//        Bitmap photo = framePhoto(getPhoto(this.getContentResolver(), conUri));


    }
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
            }
        });

    }
}
