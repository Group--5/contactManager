package otto.contacts.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
* This activity is a search contact activity. As input is typed in our input view, the size of the
 * list shrinks to only those contacts that contain the inputed substring.
 *
 * */
public class SearchActivity extends Activity {

    private List<Contact> mContacts;


    /** Standard onCreate method. This creates our UI, and populates our list with contacts.**/
    @Override
    public void onCreate(Bundle b)
    {

        super.onCreate(b);
        setContentView(R.layout.search_layout);



         ImageButton back = (ImageButton) findViewById(R.id.searchBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                finish();

            }
        });

        EditText searchEdit = (EditText) findViewById(R.id.searchEditText);
        ContactListVessel clVessel = (ContactListVessel) getIntent().getSerializableExtra("contactlist");
        mContacts = clVessel.getContacts();


        final ListView a = (ListView) findViewById(R.id.searchListView);
        a.setAdapter(new SearchAdapter(this, mContacts,getWidth()));




        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                // Create new Adapter.
                Log.d("OnTextChanged", "Called");

                String s = String.valueOf(charSequence);

                Log.d("OnTextChanged", "Converted to:" + s);
                SearchAdapter adapter = new SearchAdapter(getApplicationContext(),filterContacts(mContacts,s),getWidth());

                Log.d("OnTextChanged", "Adapter Created");
                a.setAdapter(adapter);

                Log.d("===", String.valueOf(filterContacts(mContacts, s).size()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

    }
    /**This is used to get the width of our screen. To be used to properly scale ui elements
     * @return The width of the screen**/
    public int getWidth()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (int) Math.floor( size.x/5);
        return width;}


    /**
     * This method takes a list of  contacts, a substring and returns a list of
     * contacts that contain that string..
     * @param contacts The contacts you're filtering through,
     * @param s The string that we're matching.
     * @return A shorter list of contacts containing our substring.
     * **/
    public List<Contact> filterContacts(List<Contact> contacts, String s)
    {  List<Contact> returnContacts = new ArrayList<Contact>();
        for (Contact subject : contacts)
        {
            if (subject.displayName.toLowerCase().contains(s.toLowerCase()))
            {
                returnContacts.add(subject);
            }
        }
        return returnContacts;
    }

    /**
     * This is our array adapter for our search function.
     * **/
    public class SearchAdapter extends ArrayAdapter<Contact>
    {
        private Context mContext;
        private List<Contact> mContacts;
        private int mWidth;
        private LayoutInflater mInflator;

        /**Standard Adapter constructor, nothing new here...**/
        public SearchAdapter(Context context, List<Contact> contacts,int resource) {
            super(context, resource, contacts);
            this.mContext = context;
            this.mContacts = contacts;
            this.mWidth = resource;
            mInflator = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            Log.d("(((", "Created");
        }

        /**Standard getView method, nothing interesting here.**/
        @Override
        public View getView(final int position, View ourView, ViewGroup parent)
        {
            final Contact subject = mContacts.get(position);
            final View rowView = mInflator.inflate(R.layout.contact_list_template, parent, false);


            TextView nameText = (TextView) rowView.findViewById(R.id.contactName);
            Button button = (Button) rowView.findViewById(R.id.contactListButton);
            button.setTextSize(mWidth/5);
            button.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mWidth));
            button.setPadding(mWidth/8,0,0,mWidth/16);
            TextView elem = (TextView) rowView.findViewById(R.id.elempad1);


            nameText.setPadding(mWidth/2,0,0,0);

            rowView.setPadding(0,0,0,mWidth/8);


            nameText.setText(subject.displayName);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, ContactProfileActivity.class);
                    i.putExtra("contact",subject);
                    i.putExtra("isnew", false);
                }
            });


            return rowView;
        }
    }

}
