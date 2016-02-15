package com.xq.jltx.horizontal3dlistview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.sephiroth.android.library.util.XqLog;
import it.sephiroth.android.library.widget.AbsHListView;
import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

/**
 * @author jltxseo
 *         Created by junlintianxia on 2016年02月15日.
 */

public class SimpleHListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String LOG_TAG = "SimpleHListActivity";
    H3DListView listView;
    Button mButton1;
    Button mButton2;
    Button mButton3;
    TestAdapter mAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        List<String> items = new ArrayList<String>();
        for( int i = 0; i < 50; i++ ) {
            items.add( String.valueOf( i ) );
        }
        mAdapter = new TestAdapter( this, R.layout.test_item_1, android.R.id.text1, items );
        listView.setHeaderDividersEnabled(false);
        listView.setFooterDividersEnabled(false);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setBackgroundColor(0x00000000);
        listView.setSelector(new ColorDrawable(0x00000000));
        listView.setHorizontalScrollBarEnabled(false);
        View headView = new View(this);
        HListView.LayoutParams absParams = new AbsHListView.LayoutParams(getResources().getDimensionPixelSize( R.dimen.item_size_4), ViewGroup.LayoutParams.MATCH_PARENT);
        headView.setLayoutParams(absParams);
        View footView = new View(this);
        absParams = new AbsHListView.LayoutParams(getResources().getDimensionPixelSize( R.dimen.item_size_4), ViewGroup.LayoutParams.MATCH_PARENT);
        footView.setLayoutParams(absParams);
        listView.addHeaderView(headView,null,false);
        listView.addFooterView(footView,null,false);
        listView.setOnScrollListener(new AbsHListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsHListView view, int scrollState) {
                if (scrollState == AbsHListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    XqLog.d("YYYY", "空闲，不滑动OnScrollListener.SCROLL_STATE_IDLE");
                } else if (scrollState == AbsHListView.OnScrollListener.SCROLL_STATE_FLING) {
                    XqLog.d("YYYY", "飞速滑动OnScrollListener.SCROLL_STATE_FLING");
                } else {
                    XqLog.d("YYYY", "手指还触摸的滑动OnScrollListener.SCROLL_STATE_TOUCH_SCROLL");
                }
            }

            @Override
            public void onScroll(AbsHListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                XqLog.d("YYYY", "onScroll.firstVisibleItem=>" + firstVisibleItem);
                XqLog.d("YYYY", "onScroll.visibleItemCount=>" + visibleItemCount);
                XqLog.d("YYYY", "onScroll.totalItemCount=>" + totalItemCount);
                XqLog.d("YYYY", "正中央=>" + (firstVisibleItem + visibleItemCount / 2 ));
            }
        });
//		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				listView.smoothScrollToPosition(20);
//			}
//		},100);

//		listView.setMultiChoiceModeListener( new MultiChoiceModeListener() {
//
//			@Override
//			public boolean onPrepareActionMode( ActionMode mode, Menu menu ) {
//				return true;
//			}
//
//			@Override
//			public void onDestroyActionMode( ActionMode mode ) {
//			}
//
//			@Override
//			public boolean onCreateActionMode( ActionMode mode, Menu menu ) {
//				menu.add( 0, 0, 0, "Delete" );
//				return true;
//			}
//
//			@Override
//			public boolean onActionItemClicked( ActionMode mode, MenuItem item ) {
//				Log.d( LOG_TAG, "onActionItemClicked: " + item.getItemId() );
//
//				final int itemId = item.getItemId();
//				if( itemId == 0 ) {
//					deleteSelectedItems();
//				}
//
//				mode.finish();
//				return false;
//			}
//
//			@Override
//			public void onItemCheckedStateChanged( ActionMode mode, int position, long id, boolean checked ) {
//				mode.setTitle( "What the fuck!" );
//				mode.setSubtitle( "Selected items: " + listView.getCheckedItemCount() );
//			}
//		} );

        if(listView.getChoiceMode() == ListView.CHOICE_MODE_NONE){
            XqLog.d("ListView.CHOICE_MODE_NONE");
        }else if(listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE){
            XqLog.d("ListView.CHOICE_MODE_MULTIPLE");
        }else if(listView.getChoiceMode() == ListView.CHOICE_MODE_MULTIPLE_MODAL){
            XqLog.d("ListView.CHOICE_MODE_MULTIPLE_MODAL");
        }else if(listView.getChoiceMode() == ListView.CHOICE_MODE_SINGLE){
            XqLog.d("ListView.CHOICE_MODE_SINGLE");
        }

        listView.setOnItemClickListener(this);

        listView.setAdapter(mAdapter);
        mButton1.setOnClickListener( this );
        mButton2.setOnClickListener( this );
        mButton3.setOnClickListener( this );

        Log.i( LOG_TAG, "choice mode: " + listView.getChoiceMode() );
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        listView = (H3DListView) findViewById( R.id.hListView1 );
        mButton1 = (Button) findViewById( R.id.button1 );
        mButton2 = (Button) findViewById( R.id.button2 );
        mButton3 = (Button) findViewById( R.id.button3 );

    }


    @Override
    public void onClick( View v ) {
        final int id = v.getId();

        if( id == mButton1.getId() ) {
            addElements();
        } else if( id == mButton2.getId() ) {
            removeElements();
        } else if( id == mButton3.getId() ) {
            scrollList();
        }
    }

    private void scrollList() {
        listView.smoothScrollBy( 1500, 300 );
    }

    private void addElements() {
        for( int i = 0; i < 5; i++ ) {
            mAdapter.mItems.add( Math.min( mAdapter.mItems.size(), 2), String.valueOf( mAdapter.mItems.size() ) );
        }
        mAdapter.notifyDataSetChanged();
    }

    private void removeElements() {
        for( int i = 0; i < 5; i++ ) {
            if( mAdapter.mItems.size() > 0 ) {
                mAdapter.mItems.remove( Math.min( mAdapter.mItems.size()-1, 2 ) );
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void deleteSelectedItems() {
        SparseArrayCompat<Boolean> checkedItems = listView.getCheckedItemPositions();
        ArrayList<Integer> sorted = new ArrayList<Integer>( checkedItems.size() );

        Log.i( LOG_TAG, "deleting: " + checkedItems.size() );

        for( int i = 0; i < checkedItems.size(); i++ ) {
            if( checkedItems.valueAt( i ) ) {
                sorted.add( checkedItems.keyAt( i ) );
            }
        }

        Collections.sort( sorted );

        for( int i = sorted.size()-1; i >= 0; i-- ) {
            int position = sorted.get( i );
            Log.d( LOG_TAG, "Deleting item at: " + position );
            mAdapter.mItems.remove( position );
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
        Toast.makeText(this,"选中第"+position+"个",Toast.LENGTH_SHORT).show();
    }

    class TestAdapter extends ArrayAdapter<String> {

        List<String> mItems;
        LayoutInflater mInflater;
        int mResource;
        int mTextResId;
        Context context;
        public TestAdapter( Context context, int resourceId, int textViewResourceId, List<String> objects ) {
            super( context, resourceId, textViewResourceId, objects );
            mInflater = LayoutInflater.from( context );
            mResource = resourceId;
            mTextResId = textViewResourceId;
            mItems = objects;
            this.context = context;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public long getItemId( int position ) {
            return getItem( position ).hashCode();
        }

//		@Override
//		public int getViewTypeCount() {
//			return 3;
//		}
//
//		@Override
//		public int getItemViewType( int position ) {
//			return position%3;
//		}

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {

            if( null == convertView ) {
                convertView = mInflater.inflate( mResource, parent, false );
            }

            TextView textView = (TextView) convertView.findViewById( mTextResId );
            textView.setText( getItem( position ) );

            int type = getItemViewType( position );

            ViewGroup.LayoutParams params = convertView.getLayoutParams();
//			if( type == 0 ) {
//				params.width = getResources().getDimensionPixelSize( R.dimen.item_size_1 );
//			} else if( type == 1 ) {
//				params.width = getResources().getDimensionPixelSize( R.dimen.item_size_2 );
//			} else {
            params.width = getResources().getDimensionPixelSize( R.dimen.item_size_3 );
//			}
/*			convertView.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					Toast.makeText(context,"响应子View",Toast.LENGTH_SHORT).show();
				}
			});*/
            return convertView;
        }
    }
}
