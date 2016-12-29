package com.example.q.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static android.os.Build.VERSION_CODES.M;
import static com.example.q.myapplication.R.id.mName;


/**
 * Created by yk 12/26/2016
 */

//Our class extending fragment
public class Tab1 extends Fragment {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;

    private View view;

    ContentResolver contentResolver;

    private class ViewHolder {
        public TextView mName;

        public TextView mNumber;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(ListData addInfo){
            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item, null);

                holder.mName = (TextView) convertView.findViewById(mName);
                holder.mNumber = (TextView) convertView.findViewById(R.id.mNumber);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);


            holder.mName.setText(mData.mName);
            holder.mNumber.setText(mData.mNumber);

            return convertView;
        }
    }
    public void addcontact(){
        contentResolver = getActivity().getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection    = new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor cursor1 = contentResolver.query(uri, projection, null, null, null);

        int indexName = cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if(cursor1.moveToFirst()) {
            do {
                ListData contact = new ListData();
                String name   = cursor1.getString(indexName);
                String number = cursor1.getString(indexNumber);

                contact.mName = name;
                contact.mNumber = number;

                int br = 0;

                for(int i=0 ; i<mAdapter.getCount() ; i++ ){
                    ListData l = (ListData) mAdapter.getItem(i);
                    if(l.mName.equals(name)){
                       br = 1;
                        break;
                    }

                }
                if(br == 0)
                    mAdapter.addItem(contact);

            } while (cursor1.moveToNext());
        }
        cursor1.close();
        mAdapter.notifyDataSetChanged();
    }
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        view = inflater.inflate(R.layout.tab1, container, false);


        mListView = (ListView) view.findViewById(R.id.mList);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);




        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListData who = (ListData) parent.getAdapter().getItem(position);
                final String wname = who.mName;
                final String wnum = who.mNumber;

                AlertDialog.Builder dlg1 = new AlertDialog.Builder(getActivity());
                dlg1.setMessage("Call / Text").setCancelable(true).setPositiveButton("Call",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + wnum));
                                startActivity(intent);
                            }
                        }).setNegativeButton("Text",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setData(Uri.parse("smsto:" + wnum));
                                startActivity(intent);
                            }
                        });


                dlg1.setTitle(wname);
                dlg1.show();
            }
        });



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }else{
            addcontact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addcontact();
            } else {
                Toast.makeText(getActivity(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}