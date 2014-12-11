package otto.contacts.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
*@deprecated
 */
public class AlphaSublistArrayAdapter extends ArrayAdapter<Contact> {

    private List<Contact> mContacts;
    private Context mContext;

    /**
     * This method is necessary to create a gridview adapter.
     * @param context Necessary to create list adapter.
     * @param contacts List of contacts to iterate through,
     * @param resource Necessary for creation of list adapter
     * **/
    public AlphaSublistArrayAdapter(Context context, List<Contact> contacts,int resource) {
        super(context, resource, contacts);
        this.mContacts = contacts;
        this.mContext = context;
    }



    /**
     * Used to edit User Interface and set up click-listeners.
     * **/
    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.contact_list_template, parent, false);
        TextView nameText = (TextView) rowView.findViewById(R.id.contactName);
        Button b = (Button) rowView.findViewById(R.id.contactListButton);

        nameText.setText(mContacts.get(position).displayName);

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/segoeuil.ttf");
        nameText.setText(mContacts.get(position).displayName);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mContext.getApplicationContext(),ContactProfileActivity.class );
                mContext.startActivity(i);
                //TODO: Potentially allow end activty to be a thing.




            }
        });

        return rowView;}

/** DEPRECATED METHOD:: Gets position of first element starting with character a
 * @param a Character we're looking for.
 * @return Position of first occurence of character a.
 *
 * **/
    public int getPositionOfFirst(char a )
    {
        int position = 0;
        for(Contact x: mContacts)
        {
            if(String.valueOf(x.displayName.toCharArray()[0]).toUpperCase().equals(String.valueOf(a).toUpperCase()))
            {
                return position;
            }
            else
            {
                position ++;
            }
        }
        return -999;
    }

}
