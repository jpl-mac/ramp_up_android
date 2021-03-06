package com.macadamian;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.support.v4.widget.SimpleCursorAdapter;

public class CursorAdapterActivity extends ListActivity
    implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter _adapter;

    static final String[] PROJECTION = new String[] {
        ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME
    };

    static final String SELECTION = "(("
            + ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND ("
            + ContactsContract.Data.DISPLAY_NAME + " != '' ))";
            

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        create_progress_bar();
        create_adapter();

        getLoaderManager().initLoader(0, null, this);
    }

    private void create_progress_bar() {
        ProgressBar progress = new ProgressBar(this);
        progress.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progress.setIndeterminate(true);
        getListView().setEmptyView(progress);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progress);
    }

    private void create_adapter() {
        String fromColumns[] = { ContactsContract.Data.DISPLAY_NAME };
        int[] toViews = { android.R.id.text1 };

        _adapter = new SimpleCursorAdapter(
            this, android.R.layout.simple_list_item_1, null,
            fromColumns, toViews, 0);
        setListAdapter(_adapter);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI, PROJECTION, SELECTION, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        _adapter.swapCursor(c);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.swapCursor(null);
    }
}
