package otto.contacts.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.List;


public class HomeActivity extends Activity {

    int mSizeOfDisplayableSquare;

    /**OnCreate method for HomeActivity. It is set up to accomodate the addition of new languages in
     * future versions of this app. Buttons are rigged here to transfer to alpha sublist.
     * UI is also scaled to your phone in this activity**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        char tempChar = 'a';

        ContactIntermidiary ci = new ContactIntermidiary(getApplicationContext());

        String[] test = {
                            "+", "a", "b", "c",
                            "d", "e", "f", "g",
                            "h", "i", "j", "k",
                            "l", "m", "n", "o",
                            "p", "q", "r", "s",
                            "t", "u", "v", "w",
                            "x", "y", "z", "*"
                        };
        //\uD83C\uDF10 is our unicode for the globe with merrideans
        //\uD83D\uDD0E is our unicode for rightward looking magnifying glass


        // Returns the width of our screen divided by five. It just so happens that on windows
        //Buttons account for only a fifth of the screen width.
        mSizeOfDisplayableSquare = getWidth();
        GridView a = (GridView) findViewById(R.id.gridView);
        //setting proper margins.
        a.setLayoutParams(new RelativeLayout.LayoutParams(5*getWidth() - (int) Math.floor(getWidth()*5/9), getHeight()));


        // Creating a Homescreen adapter and passing it width, and our character array to handel button generation.
        final HomeScreenArrayAdapter b = new HomeScreenArrayAdapter(this, test,ci.getContactCharList(), getWidth());
        a.setAdapter(b);
        Log.d("HomeActivity", "Adapter Set. Same with onclick");
        Log.d("ProbeItemClicks", "This statment is displayed before setting the onclick listener");


a.setOnItemClickListener( new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Log.d("ProbeItemClicks", "This statment is displayed before setting the onclick listener");
    }
});
//        a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Log.d("ProbeItemClicks", "This statment is displayed as our Items are being selected");
//                System.out.println("==================================================");
//                System.out.println("==================================================");
//                System.out.println("==================================================");
//            }
//        });

        Log.d("ProbeItemClicks", "This statment is displayed After setting the onclick listener");
//        ;
//
//        a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("Checking if button is active", "Trying");
//
//            }
//        });

//        a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("Checking if button is active", "Trying");
//
//                if (b.isActiveAtIndex(i)) {
//
//                    Log.d("Checking if button is active", "It is");
//                    Intent j = new Intent(getApplicationContext(), AlphaSublistActivity.class);
//                    j.putExtra("schar",b.getCharacteratIndex(i));
//                    startActivity(j);
//                }
//                else
//                {
//                    Log.d("Checking if button is active", "It isn't");
//                }
//            }
//        });
//    }
        }

/** Gets width of screen in pixels.
 * @return width of screen**/
    private int getWidth()
{
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    int width = (int) Math.floor( size.x/5);
return width;}
    /**
     * Gets Height of screen in pixels.
     * @return height of screen
     */

  private int getHeight()
  {
      Display display = getWindowManager().getDefaultDisplay();
      Point size = new Point();
      display.getSize(size);
      int height = size.y;
      return height;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
