package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by DEV4LIFE on 1/16/16.
 */
public class MyAddressWebView extends EditText {
    public MyAddressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
//        if (keyCode==KeyEvent.KEYCODE_ENTER)
//        {
//            // Just ignore the [Enter] key
//            return true;
//        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }


}
