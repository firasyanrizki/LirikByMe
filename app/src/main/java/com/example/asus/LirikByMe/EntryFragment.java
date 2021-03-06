package com.example.asus.LirikByMe;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class EntryFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    public SQLiteAdapter sqLiteAdapter;

    public EntryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EntryFragment newInstance(int sectionNumber) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        Intent intent = new Intent(getContext(), LyricActivity.class);
        intent.putExtra("title", list.getItemAtPosition(position).toString());
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        sqLiteAdapter = new SQLiteAdapter(getActivity());
        sqLiteAdapter.openToRead();
        Cursor cursor = sqLiteAdapter.queueAll();
        ArrayList<String> entries = new ArrayList<>();
        while (cursor.moveToNext()){
            entries.add(cursor.getString(0));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, entries);
        setListAdapter(adapter);
        sqLiteAdapter.close();

        return rootView;
    }
}
