package br.com.artechapps.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.artechapps.app.BuildConfig;
import br.com.artechapps.app.R;
import br.com.artechapps.app.database.PersistenceShop;
import br.com.artechapps.app.fragment.AboutFragment;
import br.com.artechapps.app.fragment.BudgetFragment;
import br.com.artechapps.app.fragment.DashboardFragment;
import br.com.artechapps.app.fragment.ExecutionFragment;
import br.com.artechapps.app.fragment.MessageFragment;
import br.com.artechapps.app.fragment.ProductFragment;
import br.com.artechapps.app.fragment.ScheduleFragment;
import br.com.artechapps.app.model.User;
import br.com.artechapps.app.task.AsyncTaskConfirmCartProducts;
import br.com.artechapps.app.task.AsyncTaskRefreshToken;
import br.com.artechapps.app.utils.SessionManager;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTvHeaderUserName;
    private TextView mTvHeaderUserEmail;
    private SessionManager mSM;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private MenuItem mSearchItem;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

//        mDrawer.openDrawer(Gravity.LEFT);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header =
                navigationView.getHeaderView(0);

        mTvHeaderUserName = (TextView) header.findViewById(R.id.header_user_name);
        mTvHeaderUserEmail = (TextView) header.findViewById(R.id.header_user_email);

        mSM = new SessionManager(this);
        User user = mSM.getSessionUser();

        mTvHeaderUserName.setText(user.getName());

        PersistenceShop pers = new PersistenceShop(this);
        counter = pers.count();
        pers.close();

        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.mNavigation = navigationView;
        replaceFragment(dashboardFragment);


        try {
            String origin = getIntent().getExtras().getString("origin");
            if (origin.equals(NewScheduleFinalActivity.class.getName())) {
                replaceFragment(new ScheduleFragment());
            } else if (origin.equals(AsyncTaskConfirmCartProducts.class.getName())) {
                replaceFragment(new BudgetFragment());
            }
        } catch (Exception e) {
            Log.e("ERROR", "Replace fragment parameter");
        }

        if (FirebaseInstanceId.getInstance() != null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d("FCM-TOKEN", token);
            new AsyncTaskRefreshToken("", this, false)
                    .execute(String.valueOf(user.getCode()), token);
        }

//        runThread();

    }

//    private void runThread() {
//        runOnUiThread(new Thread(new Runnable() {
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                    mDrawer.closeDrawer(Gravity.LEFT);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }));
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Click novamente em voltar para fechar o app.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    private int counter = 0;
    private TextView ui_hot = null;

    public void setCounter(int counter) {
        this.counter += counter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);


        final MenuItem menu_hot = menu.findItem(R.id.action_cart);
        mSearchItem = menu.findItem(R.id.action_search);
        mSearchItem.setVisible(false);
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
                startActivity(new Intent(MainMenuActivity.this, CartActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(MainMenuActivity.this).toBundle());
            }
        });

        updateShopCart();


        try {
            String origin = getIntent().getExtras().getString("origin");
            if (origin.equals(BudgetFragment.class.getName())) {

                mToolbar.setTitle("Novo Orçamento");
                mSearchItem.setVisible(true);
                ProductFragment frag = new ProductFragment();
                frag.setSearchView((SearchView) mSearchItem.getActionView());
                replaceFragment(frag);

            }
        } catch (Exception e) {
            Log.e("ERROR", "Replace fragment parameter");
        }

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
                    if (counter > 9) {
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
        switch (item.getItemId()) {
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
            case R.id.nav_dashboard:
                mToolbar.setTitle("Beauty Clinic");
                mSearchItem.setVisible(false);
                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.mNavigation = navigationView;
                replaceFragment(dashboardFragment);
                break;
            case R.id.nav_message:
                mToolbar.setTitle("Mensagens");
                mSearchItem.setVisible(false);
                replaceFragment(new MessageFragment());
                break;
            case R.id.nav_event:
                mToolbar.setTitle("Agendamentos");
                mSearchItem.setVisible(false);
                replaceFragment(new ScheduleFragment());
                break;
            case R.id.nav_exec:
                mToolbar.setTitle("Execuções");
                mSearchItem.setVisible(false);
                replaceFragment(new ExecutionFragment());
                break;
            case R.id.nav_products:
                mToolbar.setTitle("Produtos");
                mSearchItem.setVisible(true);
                ProductFragment frag = new ProductFragment();
                frag.setSearchView((SearchView) mSearchItem.getActionView());
                replaceFragment(frag);
                break;
            case R.id.nav_money:
                mToolbar.setTitle("Orçamentos");
                mSearchItem.setVisible(false);
                replaceFragment(new BudgetFragment());
                break;
            case R.id.nav_about:
                mSearchItem.setVisible(false);
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
