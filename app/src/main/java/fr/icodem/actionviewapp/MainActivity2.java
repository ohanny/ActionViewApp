package fr.icodem.actionviewapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * ActionLayout in action bar implemented with ToolBar (appcompat v7 / 21)
 */
public class MainActivity2 extends AppCompatActivity implements TextView.OnEditorActionListener {

    private MenuItem searchActionMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar actionBar = (Toolbar)findViewById(R.id.action_bar);
        setSupportActionBar(actionBar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchActionMenuItem = menuItem;

        final EditText searchEditText = (EditText) menuItem.getActionView().findViewById(R.id.search_src_text);
        searchEditText.setOnEditorActionListener(this);

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.d("MainActivity2", "Selected menu item => " + "OnActionExpandListener.onMenuItemActionExpand()");

                searchEditText.requestFocus();
                searchEditText.setText("");
                searchEditText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }, 200);

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                Log.d("MainActivity2", "Selected menu item => " + "OnActionExpandListener.onMenuItemActionCollapse()");

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);

                return true;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MainActivity2", "Selected menu item => " + item.getTitle());

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_search:
                Log.d("MainActivity2", "RETURNED FALSE");
                return false;// if true is returned, the action view is not expanded
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event != null) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                CharSequence textInput = v.getText();

                Toast.makeText(this, textInput, Toast.LENGTH_SHORT).show();
                MenuItemCompat.collapseActionView(searchActionMenuItem);
            }
        }
        return false;
    }
}
