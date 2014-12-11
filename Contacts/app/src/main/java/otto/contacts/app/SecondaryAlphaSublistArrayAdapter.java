package otto.contacts.app;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An alternative Array Adapter for our main contacts list. This is what is currently used in our project
 */
public class SecondaryAlphaSublistArrayAdapter extends ArrayAdapter<ListHeaderInterface> {
    private Context mContext;
    private ArrayList<ListHeaderInterface> mArrayList;
    private LayoutInflater mInflater;
    private int mWidth;
    Typeface tf;
    /**Constructor for array adapter.
     * @param context This context is needed for any list adapter. This one is no exception
     * @param a This is a list of UI elements. The getView method will extract type to determine
     *          what exactley will be displayed
     * @param resource this is the width of the application. To be used by our getView() method to
     *                 scale to other phone's screen sizes**/
    public SecondaryAlphaSublistArrayAdapter(Context context, ArrayList<ListHeaderInterface> a, int resource) {
        super(context,0, a);
        this.mContext = context;
        this.mArrayList = a;
        this.mInflater =(LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/segoeuil.ttf");
        this.mWidth = 8*5*resource/57;
    }
    /**To jump between different parts of a list, one must first figure out what index to jump to. This method looks for
     * the first contact name that starts with the string <b>s</b> and returns it's index
     * @param s This denotes the character that you want to find the index of
     * @return return the position of the first section header that starts with String <b>s</b>**/
    public int getPositionOfChar(String s)
    {
        int counter = 0;
        for(ListHeaderInterface l : mArrayList)
        {
            if(l.isSectionHeader())
            {
                if(l.getSectionHeader().sectionNameString.equals(s))
                {
                    return counter;
                }
            }
            counter++;
        }
       return 0;
    }


    /**This is a standard getView callback for arrayAdapters. This method makes decisions about how
     * our list is populated. IT looks through a list of UI elements. It checks it's type. If the element
     * is a section header type, then it is highlighted bright red(other colors in future releases).
     * Else, we assume it is a standard contact. In this case we set an onclick listener as well as
     * load the contact name to be displayed in that list element.
     * **/
    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
         final ListHeaderInterface subject = mArrayList.get(position);
        final View rowView = mInflater.inflate(R.layout.contact_list_template, parent, false);
        TextView nameText = (TextView) rowView.findViewById(R.id.contactName);
        Button button = (Button) rowView.findViewById(R.id.contactListButton);

        button.setTextSize(mWidth/5);


        button.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mWidth));
        button.setPadding(mWidth/8,0,0,mWidth/16);

        TextView elem = (TextView) rowView.findViewById(R.id.elempad1);

        boolean toggle = true;

        if(toggle)
        {}

        nameText.setPadding(mWidth/2,0,0,0);

        rowView.setPadding(0,0,0,mWidth/8);


        if(subject.isSectionHeader())
        {
            nameText.setVisibility(View.INVISIBLE);
            button.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            SectionItem itemSubject = subject.getSectionHeader();
            button.setText(itemSubject.sectionNameString);

        }else {
            Contact contactSubject = subject.getContact();
            nameText.setText( contactSubject.displayName); }
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext,EditContactActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



                    mContext.startActivity(i);
                }
            });



        return rowView;
    }
}
