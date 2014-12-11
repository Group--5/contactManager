package otto.contacts.app;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * This Is our main contact list activty. Contacts are displayed in alphabetical order, and are separated
 * by red-headers every-time the the character a section our contacts start with changes. The ui is
 * scaled to fit any android screen size perfectly.  A composite-like UI structure is used in our list.
 *  */
public class AlphaSublistActivity extends Activity {
    private enum SearchState{ PEOPLE,GROUPS};
    private Character mChar;
    private List<Contact> mContacts;
    private SearchState currentState = SearchState.PEOPLE;
    private ImageButton mAddButton;
    private ImageButton mSearchButton;
    private Context mContext;
    public boolean state = true;

    /**
     * Used to get the width of the screen for UI operations
     *
     * @param a The activity in question.
     * @return  The width of activity a!!
     * **/
    public int getWidth(Activity a)
    {
        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) Math.floor( size.x/5);
        return width;}
    /**
     * This method instantiates our UI and is designed to accept future modifications to language(characters used)
     * It continues to load Contacts and sends them to a gridview adapter.
     * **/
    @Override
    public void onCreate(Bundle b) {
        //Business as usual...

        super.onCreate(b);
        mContext = getApplicationContext();
        setContentView(R.layout.alpha_sublist_layout);

        String[] test = {
                "+", "a", "b", "c",
                "d", "e", "f", "g",
                "h", "i", "j", "k",
                "l", "m", "n", "o",
                "p", "q", "r", "s",
                "t", "u", "v", "w",
                "x", "y", "z", "*"
        };

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/segoeuil.ttf");
        final TextView title = (TextView) findViewById(R.id.people);
        final TextView second = (TextView) findViewById(R.id.gourpsTextButton);
        TextView secondaryButton =(TextView) findViewById(R.id.gourpsTextButton);
        secondaryButton.setTypeface(tf);
        title.setTypeface(tf);
        // Add and Search button manipulations
        mAddButton = (ImageButton) findViewById(R.id.imageButton);
        mSearchButton = (ImageButton) findViewById(R.id.imageButton2);


        // We're taking a Character object from the intent that helped start this activity.
        // The value of the char is the value associated to the highlighted button that you pressed to get to this activity.
        Log.d("AlphaSublistActivity", "onCreateInitialized");
        Intent i = getIntent();
        Character buttonChar = (Character) i.getSerializableExtra("button_char");
        Log.d("AlphaSublistActivity", String.valueOf(buttonChar));
        this.mChar = buttonChar;
        int width = getWidth();




        // Next action is retrieving a list groups and contacts implementing a searchable interface.
        ContactIntermidiary c = new ContactIntermidiary(AlphaSublistActivity.this);


        List<Contact> a = c.getListOfContacts();
        mContacts = a;
        List<Group> g = c.getListofGroups();

        final ListView contactList = (ListView) findViewById(R.id.alphaContactListView);
        ArrayList arrayList = new ArrayList();
        for (String x : test) {
            arrayList.add(new SectionItem(x));
            for(Contact y : a)
            {
                if(y.displayName.toLowerCase().toCharArray()[0] == x.toCharArray()[0])
                {
                    arrayList.add(y);
                     }
            }
        }
int log = 1;
Log.d("A", String.valueOf(log++));
        ArrayList arrayListGroups = new ArrayList();

        for (Group x : g) {

            Log.d("A", String.valueOf(log++));
           arrayListGroups.add(x);

            List<String> groupIDS = new ArrayList<String>();

            Log.d("A", String.valueOf(log++));
            Uri groupURI = ContactsContract.Data.CONTENT_URI;

            Log.d("A", String.valueOf(log++));


            Log.d("A", String.valueOf(log++));

//            for(Contact y : a)
//            {
//
//                    List<String> aaa = c.fetchGroupMembers(x.ID);
//
//                for(String z : aaa)
//                    {
//                        if (z.equals(y.getDisplayId()))
//                        {arrayListGroups.add(y);}
//
//                }
//            }
        }
        final SecondaryAlphaSublistArrayAdapter sAdapter = new SecondaryAlphaSublistArrayAdapter(this.getApplicationContext(), arrayList, getWidth());
        final GroupSublistActivity gAdapter = new GroupSublistActivity(this.getApplicationContext(),arrayListGroups,getWidth());
        contactList.setAdapter(sAdapter);
           contactList.setSelection(sAdapter.getPositionOfChar(String.valueOf(mChar).toLowerCase()));

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state) {
                    contactList.setAdapter(gAdapter);
                    title.setText("groups");
                    second.setText("people");
                    state  = false;
                }
                else
                {
                    contactList.setAdapter(sAdapter);
                    title.setText("people");
                    second.setText("groups");
                    state = true;
                }
            }
        });
    }




@Override
public void onStart()
{
    super.onStart();


    for (Contact contact: mContacts)
    {
        try {
            Log.d("=============", contact.displayName);
            Log.d("=============", contact.cellPhoneNumber);
            Log.d("=============", contact.workPhoneNumber);
            Log.d("=============", contact.homePhoneNumber);
        }catch (Exception e)
        {}
    }



    mAddButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // Ensure you call it only once :
            mAddButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            LinearLayout.LayoutParams addButtonParams = (LinearLayout.LayoutParams) mAddButton.getLayoutParams();
            LinearLayout.LayoutParams searchButtonParams = (LinearLayout.LayoutParams) mSearchButton.getLayoutParams();

            int height = mAddButton.getMeasuredHeight();
            Log.d("Actaul Height", "IS:" + String.valueOf(height));
            addButtonParams.height = height;
            addButtonParams.width = height;
            searchButtonParams.height = height;
            searchButtonParams.width = height;

            mSearchButton.setLayoutParams(searchButtonParams);
            mAddButton.setLayoutParams(addButtonParams);

            // Here you can get the size :)
        }
    });

mAddButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(mContext,CreateContactActivity.class);
        startActivity(i);
        finish();

    }
});
    mSearchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(mContext, SearchActivity.class);
            i.putExtra("contactlist",new ContactListVessel(mContacts));
            Log.d("OnSearchClick", String.valueOf(mContacts.size()));
            startActivity(i);
            finish();
        }
    });

//        LinearLayout buttonBar = (LinearLayout) findViewById(R.layout.Bottom);



}

    /**
     * A method to get the width of our Activity
     * **/
    public int getWidth()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) Math.floor( size.x/5);
        return width;}

    /**
     * A method to get the height of our Activity
     * **/
    public int getHeight()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;}

}
