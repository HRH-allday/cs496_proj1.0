package com.example.q.myapplication;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static com.example.q.myapplication.R.drawable.c;

/**
 * Created by yk 12/26/2016
 */

//Our class extending fragment
public class Tab2 extends Fragment {


    private GridView mGridView = null;
    private GridAdapter mGridAdapter = null;
    private int mImageList[] = {
            R.drawable.a,R.drawable.b,R.drawable.j, c, c,R.drawable.e,R.drawable.j,R.drawable.f, c,R.drawable.e,R.drawable.j,R.drawable.h, c,R.drawable.e,R.drawable.j,R.drawable.i, c,R.drawable.e,R.drawable.j,R.drawable.b, c,R.drawable.e,R.drawable.j,R.drawable.i,
            c,R.drawable.e,R.drawable.j,R.drawable.d, c,R.drawable.e,R.drawable.j,R.drawable.f, c,R.drawable.e,R.drawable.j,R.drawable.h, c,R.drawable.e,R.drawable.j,R.drawable.i, c,R.drawable.e,R.drawable.j,R.drawable.b, c,R.drawable.e,R.drawable.j,R.drawable.i,
    };

    private class GridAdapter extends BaseAdapter {
        private Context mContext = null;

        public GridAdapter(Context mContext){
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mImageList.length;
        }

        @Override
        public Object getItem(int position) {
            return mImageList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row, null);
            }


            ImageView img = (ImageView) convertView.findViewById(R.id.imageView1);
            int mData = mImageList[position];

            img.setImageResource(mData);

            return convertView;
        }
    }
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View view = inflater.inflate(R.layout.tab2, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridView1);
        mGridAdapter = new GridAdapter(getActivity());

        mGridView.setAdapter(mGridAdapter);

        final ImageView imgzoom = (ImageView) view.findViewById(R.id.imageZoom);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                imgzoom.setImageResource(mImageList[position]);
                imgzoom.setVisibility(View.VISIBLE);
                imgzoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgzoom.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });

        return view;
    }
}