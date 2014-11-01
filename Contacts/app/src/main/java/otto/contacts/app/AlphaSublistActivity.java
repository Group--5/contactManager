package otto.contacts.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by study on 10/6/14.
 */
public class AlphaSublistActivity extends Activity {
@Override public void onCreate(Bundle b)
{
    super.onCreate(b);
    setContentView(R.layout.alpha_sublist_layout);
    Intent i = getIntent();
    Character c = (Character) i.getSerializableExtra("char");

}

    public void setStylizedCharacterWidth(int width)
    {
        TextView t = (TextView) findViewById(R.id.stylizedChar);
        int someMargin = 0;
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) t.getLayoutParams();
        params.width= width + someMargin;
        params.height = width +someMargin;
        t.setLayoutParams(params);
        //Width is something we can store on the phone later to reduce screen searching. Unless. Its already store dlocally onthe machine
    }


}
