package otto.contacts.app;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This is our homescreen. It is a gridview that displays as many buttons as there are characters in the
 * english alphabet(Soon to accommodate others as well). These buttons display each character. The buttons are highlighted
 * if there exists an contact on our contact list that starts with that associated character, if that is the case
 * then button clicks will open our contact list and immediately navigate to a section that starts with your highlighted
 * and clicked character.  */
public class HomeScreenArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private List<Character> mContactCharacters;
    private final int width;
    private final int margin;
    private List<Integer> validPositions;
    private int mColor;
    /** onCreate for grid-view adapter Takes an alphabet, as well as Contact list characters, and displays
     * contact list elements as highlighted elements, and leaves everything else un-highlighted
     * @param context Context is needed for most ArrayAdapters. This one is no exception.
     * @param values In this version. This is an array containing characters of the english alphabet A-Z. In future versions it will accept other languages as well.
     * @param contactCharacters A list of all characters that deserve highlighting. Highlighted
     *                          buttons are shortcuts to different parts of our contact list.
     * @param width The width of our parent activity. This is used to properly scale our UI.
     * ***/
    public HomeScreenArrayAdapter(Context context, String[] values, List<Character> contactCharacters , int width) {
        super(context, R.layout.home_screen_buttons_layout, values);
        this.mColor = context.getResources().getColor(R.color.red);
        this.context = context;
        this.values = values;
        this.width = width;
        this.margin = (int) Math.floor(width/9);
        this.mContactCharacters = contactCharacters;
        this.validPositions = new ArrayList<Integer>();

        // Looks through character array.  Looks through contact array. If in characters array. Adds to validpositions.
        for(int i = 0; i < values.length; i++) {
            Character s = values[i].toCharArray()[0];
                if(contactCharacters.contains(s))
                {
                    validPositions.add(i);
                }
        }
        displayLists();
    }
    /** Auxilary method used to display debbuging info in log-cat**/
    public void displayLists()
    {
        Log.d("Displaying Contents of HomeScreenArrayAdapter", mContactCharacters.toString());
        for(Integer i: validPositions)
        {
            Log.d("Displaying Contents of HomeScreenArrayAdapter", i.toString());
        }
        Log.d("Displaying Contents of HomeScreenArrayAdapter", String.valueOf(mContactCharacters.size()) + "||| "+ String.valueOf(validPositions.size()));


    }

    /**This method iterates through every character in our alphabet, adjusts the size of the corresponding button
     * to scale with your phone, inflates the view, then highlights the button if any contacts on your contact list
     * start with this character. This also rigs onClick Listeners.**/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.home_screen_buttons_layout, parent, false);
                Button button = (Button) rowView.findViewById(R.id.button);
                button.setTextSize(width / 5);
                button.setText(values[position]);
        button.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        button.setPadding(width/8,0,0,0);




        if(values[position].equals("+"))
        {
            RelativeLayout l = (RelativeLayout) rowView.findViewById(R.id.buttback);
//            button.setBackground(context.getResources().getDrawable(R.drawable.add));
            l.setBackgroundColor(context.getResources().getColor(R.color.red));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), CreateContactActivity.class);
                    getContext().startActivity(i);

                }
            });



        }else if (values[position].equals("*"))
        {
            RelativeLayout l = (RelativeLayout) rowView.findViewById(R.id.buttback);
         //   button.setBackground(context.getResources().getDrawable(R.drawable.sicon));


//            RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) button.getLayoutParams();
//            par.width= width/3;
//            par.height = width/3;
            //button.setLayoutParams(par);

            l.setBackgroundColor(context.getResources().getColor(R.color.red));

        }
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/segoeuil.ttf");
        if(validPositions.contains((Integer)position))
        {
            button.setBackgroundColor(mColor);
            button.setTextColor( Color.parseColor("#EFEFEF"));

            // This is where I link to our alphabetical sublist!
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent i = new Intent(context, AlphaSublistActivity.class );
                   // i.putExtra takes the value associated with a highlighted button, then essentailly prepares
                   // to transport that value to our alpha-sublist activity by associating it with a "char" hash.
                   i.putExtra("button_char", (Character) values[position].toCharArray()[0]);
                    context.startActivity(i);
                }
            });
        }

        GridView.LayoutParams params= (GridView.LayoutParams) rowView.getLayoutParams();
        params.width= width + margin;
        params.height = width + margin;
        rowView.setLayoutParams(params);




        return rowView;
    }

    /**
     * This feature is not yet implemented
     * **/
    public void updateColor(Color r)
    {

    }
    /**
     * Deprecated
     * **/
    public List<Integer> getValidPositions()
    {
        return this.validPositions;
    }
    // WARNING Some nasty-ass coupling going on here. Try not to repeat this.

    /**Deprecated**/
    public Boolean isActiveAtIndex(int i )
    {
        return validPositions.contains((Integer) i);
    }

   /**Deprecated**/
    public Character getCharacteratIndex(int i)
    {
        return mContactCharacters.get(i);
    }

}