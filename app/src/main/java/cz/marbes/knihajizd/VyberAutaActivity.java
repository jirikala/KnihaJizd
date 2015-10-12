package cz.marbes.knihajizd;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class VyberAutaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyber_auta);
        Helper h = new Helper(this);
        SQLiteDatabase rdb = h.getReadableDatabase();
        SQLiteDatabase wdb = h.getWritableDatabase();
        h.onCreate(wdb);
        ContentValues cv = new ContentValues();
        cv.put("_id", 1);
        cv.put("jmeno", "auto1");
        wdb.insert("auta", null, cv);

        cv.clear();
        cv.put("_id", 2);
        cv.put("jmeno", "auto2");
        wdb.insert("auta", null, cv);

        Cursor c = rdb.query("auta", new String[]{"_id", "jmeno"}, null, null, null, null, null);
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.seznam_aut, c, new String[]{"_id", "jmeno"}, new int[]{R.id.id_auta, R.id.jmeno_auta});
        ListView lv = (ListView) findViewById(R.id.seznam_aut);
        lv.setAdapter(sca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), SpravaAutaActivity.class);
                i.putExtra("id_auta", Integer.valueOf((String)((TextView)(view.findViewById(R.id.id_auta))).getText()));
                startActivity(i);
            }
        });
        wdb.close();
        rdb.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vyber_auta, menu);
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
}