package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.q.myapplication.R.layout.tab2;

/**
 * Created by yk 12/26/2016
 */

public class Tab2 extends Fragment {

    private GridView mGridView = null;
    private MyAdapter mGridAdapter = null;
    GridView gd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(tab2, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridView1);
        mGridAdapter = new MyAdapter((getActivity()));
        final ImageView imgzoom = (ImageView) view.findViewById(R.id.imageZoom);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = (int) mGridAdapter.getItemId(position);
                imgzoom.setImageResource(a);
                imgzoom.setVisibility(View.VISIBLE);
                imgzoom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgzoom.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });




        mGridView.setAdapter(mGridAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gd=(GridView)getView().findViewById(R.id.gridView1);
        gd.setAdapter(new MyAdapter(getActivity()));

    }


    public class MyAdapter extends BaseAdapter
    {

        private List<Item> items=new ArrayList<Item>();
        private LayoutInflater inflator;

        public MyAdapter(Context context) {
            // TODO Auto-generated constructor stub
            inflator=LayoutInflater.from(context);
            items.add(new Item("미나",R.drawable.a));
            items.add(new Item("채영",R.drawable.b));
            items.add(new Item("다현",R.drawable.c));
            items.add(new Item("정연",R.drawable.d));
            items.add(new Item("지효",R.drawable.e));
            items.add(new Item("모모", R.drawable.f));
            items.add(new Item("나연", R.drawable.g));
            items.add(new Item("사나", R.drawable.h));
            items.add(new Item("쯔위", R.drawable.i));
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return items.get(position).drawableId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // TODO Auto-generated method stub
            View v=convertView;
            ImageView img1;


            TextView txt1;
            if(v==null)
            {
                v=inflator.inflate(R.layout.grid_item,parent,false);
                v.setTag(R.id.picture,v.findViewById(R.id.picture));


                v.setTag(R.id.text,v.findViewById(R.id.text));
            }

            img1=(ImageView)v.findViewById(R.id.picture);


            txt1=(TextView)v.findViewById(R.id.text);


            Item item=(Item)getItem(position);

            img1.setImageResource(item.drawableId);


            txt1.setText(item.name);



            return v;
        }

    }

    private class Item
    {
        final String name;
        final int drawableId;
        int chosen;

        Item(String name,int drawableId)
        {
            this.name=name;
            this.drawableId=drawableId;
        }
    }


}
