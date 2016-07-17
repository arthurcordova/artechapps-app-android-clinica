package br.com.artechapps.app.activity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import br.com.artechapps.app.R;
import br.com.artechapps.app.fragment.EventFragment;
import br.com.artechapps.app.fragment.MessageFragment;
import br.com.artechapps.app.fragment.MoneyFragment;
import br.com.artechapps.app.fragment.ProductFragment;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.utils.SessionManager;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTvHeaderUserName;
    private TextView mTvHeaderUserEmail;
    private SessionManager mSM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSM = new SessionManager(this);
        User user = mSM.getSessionUser();

//        mTvHeaderUserName = (TextView) findViewById(R.id.header_user_name);
//        mTvHeaderUserEmail = (TextView) findViewById(R.id.header_user_email);
//        mTvHeaderUserName.setText(user.getName());
//        mTvHeaderUserEmail.setText(user.getCpfcnpj());


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private int hot_number = 0  ;
    private TextView ui_hot = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);


        final MenuItem menu_hot = menu.findItem(R.id.action_buy);
        menu_hot.setActionView(R.layout.action_bar_notifitcation_icon);

        final View menu_hotlist = menu_hot.getActionView();

        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        updateHotCount(hot_number);
//        new MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
//            @Override
//            public void onClick(View v) {
////                onHotlistSelected();
//            }
//        };
        return super.onCreateOptionsMenu(menu);


        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//
//        int notifCount = 1;
//        View count = menu.findItem(R.id.action_buy);
//        count.setBackground(getResources().getDrawable(R.drawable.badge_circle));

//        MenuItem item = menu.findItem(R.id.action_buy);
//        LayerDrawable icon = (LayerDrawable) item.getIcon();
//
//        // Update LayerDrawable's BadgeDrawable
//        Utils2.setBadgeCount(this, icon, 2);
//        return true;
    }

    static abstract class MyMenuItemStuffListener implements View.OnClickListener, View.OnLongClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override abstract public void onClick(View v);

        @Override public boolean onLongClick(View v) {
            final int[] screenPos = new int[2];
            final Rect displayFrame = new Rect();
            view.getLocationOnScreen(screenPos);
            view.getWindowVisibleDisplayFrame(displayFrame);
            final Context context = view.getContext();
            final int width = view.getWidth();
            final int height = view.getHeight();
            final int midy = screenPos[1] + height / 2;
            final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            Toast cheatSheet = Toast.makeText(context, hint, Toast.LENGTH_SHORT);
            if (midy < displayFrame.height()) {
                cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT,
                        screenWidth - screenPos[0] - width / 2, height);
            } else {
                cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
            }
            cheatSheet.show();
            return true;
        }
    }


    public void updateHotCount(final int new_hot_number) {
        hot_number = new_hot_number;
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    ui_hot.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.action_logout:
//
//                break;
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_message :
                replaceFragment(new MessageFragment());
                break;
            case R.id.nav_event :
                replaceFragment(new EventFragment());
                break;
            case R.id.nav_products :
                replaceFragment(new ProductFragment());
                break;
            case R.id.nav_money :
                replaceFragment(new MoneyFragment());
                break;
            case R.id.nav_about :
                break;
            case R.id.nav_logout :
                SessionManager sm = new SessionManager(MainMenuActivity.this);
                sm.destroySessionLogin(SplashActivity.class);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }
}
