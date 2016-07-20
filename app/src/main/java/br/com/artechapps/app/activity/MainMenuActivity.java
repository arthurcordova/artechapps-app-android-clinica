package br.com.artechapps.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.artechapps.app.R;
import br.com.artechapps.app.fragment.AboutFragment;
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

        View header =
                navigationView.getHeaderView(0);
        mTvHeaderUserName = (TextView) header.findViewById(R.id.header_user_name);
        mTvHeaderUserEmail = (TextView) header.findViewById(R.id.header_user_email);

        mSM = new SessionManager(this);
        User user = mSM.getSessionUser();

        mTvHeaderUserName.setText(user.getName());

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

    private int counter = 0;
    private TextView ui_hot = null;

    public void setCounter(int counter) {
        this.counter += counter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);


        final MenuItem menu_hot = menu.findItem(R.id.action_cart);
        menu_hot.setActionView(R.layout.action_bar_notifitcation_icon);
        menu_hot.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainMenuActivity.this, CartActivity.class));
                return true;
            }
        });


        final View menu_hotlist = menu_hot.getActionView();

        ui_hot = (TextView) menu_hotlist.findViewById(R.id.hotlist_hot);
        RelativeLayout cart_content = (RelativeLayout) menu_hotlist.findViewById(R.id.cart_content);
        cart_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, CartActivity.class));
            }
        });

        updateShopCart();

        return super.onCreateOptionsMenu(menu);


    }

    public void updateShopCart() {
        if (ui_hot == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (counter == 0)
                    ui_hot.setVisibility(View.INVISIBLE);
                else {
                    ui_hot.setVisibility(View.VISIBLE);
                    if (counter > 9){
                        ui_hot.setText("+9");
                    } else {
                        ui_hot.setText(Integer.toString(counter));
                    }

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                replaceFragment(new MessageFragment());
                break;
            case R.id.nav_event:
                replaceFragment(new EventFragment());
                break;
            case R.id.nav_products:
                replaceFragment(new ProductFragment());
                break;
            case R.id.nav_money:
                replaceFragment(new MoneyFragment());
                break;
            case R.id.nav_about:
                replaceFragment(new AboutFragment());
                break;
            case R.id.nav_logout:
                SessionManager sm = new SessionManager(MainMenuActivity.this);
                sm.destroySessionLogin(SplashActivity.class);
                break;

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}
