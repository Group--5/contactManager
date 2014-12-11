package otto.contacts.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * This is a standard group Array Adapter, used to display groups the same way one displays contacts */
public class GroupSublistActivity extends ArrayAdapter<ListHeaderInterface> {
    private Context mContext;
    private List<ListHeaderInterface> mList;
    private LayoutInflater mInflater;
    private Typeface tf;
    private int mWidth;
/**Constructor for a group specific list adapter.
 * @param context This context is necessary for the list adapter
 * @param list A list of groups
 * @param width Width of screen for UI adjustments**/

     public GroupSublistActivity(Context context, List<ListHeaderInterface> list, int width) {
        super(context, 0, list);
        this.mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/segoeuil.ttf");
        this.mList = list;
        this.mWidth = 8*5*width/57;
    }

    /**
    /**Iterates through group list and extracts information to be displayed in list format.
     UI formatting for individual list elements also happens here.**/
    @Override
    public View getView(final int position,View v, ViewGroup parent)
    {
        ListHeaderInterface subject = mList.get(position);

        final View rowView = mInflater.inflate(R.layout.contact_list_template, parent, false);
        TextView nameText = (TextView) rowView.findViewById(R.id.contactName);
        Button button = (Button) rowView.findViewById(R.id.contactListButton);

        button.setTextSize(mWidth/5);


        button.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mWidth));
        button.setPadding(mWidth/8,0,0,mWidth/16);


        nameText.setPadding(mWidth/2,0,0,0);
        rowView.setPadding(0,0,0,mWidth/8);

        if(subject.isGroup())
        {
            Group s = subject.getGroup();
            nameText.setText(s.name);
            button.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            SectionItem itemSubject = subject.getSectionHeader();
        }else {
            Contact contactSubject = subject.getContact();
            nameText.setText( contactSubject.displayName);
        }
        return rowView;
    }
}
