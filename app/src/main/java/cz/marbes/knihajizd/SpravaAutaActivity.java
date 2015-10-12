package cz.marbes.knihajizd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SpravaAutaActivity extends FragmentActivity implements NovaJizdaFragment.OnFragmentInteractionListener {

    ViewPager vp;
    boolean probihaJizda;
    TabLayout tabLayout;
    int id_auta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        probihaJizda = false;
        setContentView(R.layout.activity_sprava_auta);
        vp = (ViewPager) findViewById(R.id.vpPager);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), probihaJizda));
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(vp);
        id_auta = getIntent().getIntExtra("id_auta", 100);
        Log.v("id_auta", String.valueOf(id_auta));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sprava_auta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepniNaVyberAuta(View v)
    {
        Intent i = new Intent(this, VyberAutaActivity.class);
        startActivity(i);
    }

    public void setProbihaJizda(boolean b)
    {
        probihaJizda = b;
        MyPagerAdapter mpa = new MyPagerAdapter(getSupportFragmentManager(), b);
        vp.setAdapter(mpa);
        tabLayout.setupWithViewPager(vp);
        mpa.getItem(0);
        mpa.notifyDataSetChanged();
    }

    public void novaJizda(View v)
    {
        setProbihaJizda(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private boolean probihaJizda;

        public MyPagerAdapter(FragmentManager fragmentManager, boolean _probihaJizda) {
            super(fragmentManager);
            this.probihaJizda = _probihaJizda;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return 3;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(probihaJizda)
                        return ProbihajiciJizdaFragment.newInstance();
                    else
                        return NovaJizdaFragment.newInstance();
                case 1:
                    //TODO přístup do databáze
                    return HistorieJizdFragment.newInstance(0);
                case 2:
                    return PomocneFotkyFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    notifyDataSetChanged();
                    return probihaJizda?"Probíhající jízda":"Nová jízda";
                case 1:
                    return "Historie jízd";
                case 2:
                    return "Pomocné fotky";
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object)
        {
            if (object instanceof ProbihajiciJizdaFragment && !probihaJizda || object instanceof NovaJizdaFragment && probihaJizda)
                return POSITION_NONE;
            else
                return POSITION_UNCHANGED;
        }
    }
}
