package otto.contacts.app;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by study on 9/30/14.
 */
public class HomeScreenArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private List<Character> contactCharacters;
    private final int width;
    private final int margin;
    private List<Integer> validPositions;
    private int mColor;

    public HomeScreenArrayAdapter(Context context, String[] values, List<Character> contactCharacters , int width) {
        super(context, R.layout.home_screen_buttons_layout, values);
        this.mColor = context.getResources().getColor(R.color.red);
        this.context = context;
        this.values = values;
        this.width = width;
        this.margin = (int) Math.floor(width/9);
        this.contactCharacters = contactCharacters;
        this.validPositions = new ArrayList<Integer>();

        for(int i = 0; i < values.length; i++) {
            Character s = values[i].toCharArray()[0];
            for (Character c : contactCharacters) {
                if(contactCharacters.contains(s))
                {
                    validPositions.add(i);
                }

            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.home_screen_buttons_layout, parent, false);
                Button button = (Button) rowView.findViewById(R.id.button);
                button.setTextSize(width / 5);
                button.setText(values[position]);
        button.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        button.setPadding(width/8,0,0,0);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/segoeuil.ttf");

        if(validPositions.contains((Integer)position))
        {
            button.setBackgroundColor(mColor);
            button.setTextColor( Color.parseColor("#EFEFEF"));
        }

        GridView.LayoutParams params= (GridView.LayoutParams) rowView.getLayoutParams();
        params.width= width + margin;
        params.height = width + margin;
        rowView.setLayoutParams(params);

        Log.d("HomeScreenArrayAdapter", "4");

        return rowView;
    }


    boolean isPrime(long n) {
        if(n < 2) return false;
        if(n == 2 || n == 3) return true;
        if(n%2 == 0 || n%3 == 0) return false;
        long sqrtN = (long)Math.sqrt(n)+1;
        for(long i = 6L; i <= sqrtN; i += 6) {
            if(n%(i-1) == 0 || n%(i+1) == 0) return false;
        }
        return true;
    }
    public void updateColor(Color r)
    {

    }
    public List<Integer> getValidPositions()
    {
        return this.validPositions;
    }
    // WARNING Some nasty-ass coupling going on here. Try not to repeat this.

    public Boolean isActiveAtIndex(int i )
    {
        return validPositions.contains((Integer) i);
    }
    public Character getCharacteratIndex(int i)
    {
        return contactCharacters.get(i);
    }


}