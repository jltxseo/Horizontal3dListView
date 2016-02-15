package com.xq.jltx.horizontal3dlistview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jltxseo
 *         Created by junlintianxia on 2016年02月15日.
 */

public class MainActivity extends AppCompatListActivity {

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        List<String> activities = new ArrayList<String>();
        activities.add( "Simple List" );
        activities.add( "Expandable List" );


        setListAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, activities ) );
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch( position ) {
                    case 0:
                        startActivity( new Intent( MainActivity.this, SimpleHListActivity.class ) );
                        break;
                    case 1:
                        startActivity( new Intent( MainActivity.this, ExpandableListActivity.class ) );
                        break;
                }
            }
        });
    }

/*    @Override
    protected void onListItemClick(final ListView l, final View v, final int position, final long id ) {

        switch( position ) {
            case 0:
                startActivity( new Intent( this, SimpleHListActivity.class ) );
                break;
            case 1:
                startActivity( new Intent( this, ExpandableListActivity.class ) );
                break;
        }

        super.onListItemClick( l, v, position, id );
    }*/
}
