package com.example.q.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.q.myapplication.R.id.mList;

/**
 * Created by yk 12/26/2016
 */

//Our class extending fragment
public class Tab3 extends Fragment {

    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    private String day;
    Calendar dateTime = GregorianCalendar.getInstance();
    private TextView text;
    private Button btn_date;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");

    private DBHelper dbHelper;

    private class ViewHolder {
        public TextView mWord;

    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData2> mListData = new ArrayList<ListData2>();

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

        public void resetList(){
            mListData.clear();
        }

        public void addItem(ListData2 addInfo){
            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
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
                convertView = inflater.inflate(R.layout.item2, null);

                holder.mWord = (TextView) convertView.findViewById(R.id.mWord);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData2 mData = mListData.get(position);


            holder.mWord.setText(mData.word);

            return convertView;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.tab3, container, false);

        mListView = (ListView) view.findViewById(mList);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        dbHelper = new DBHelper(getActivity().getApplicationContext(), "WorkList.db", null, 1);

        btn_date = (Button) view.findViewById(R.id.btn_datePicker);

        btn_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateDate();
            }
        });

        //c1.set(mDate.get(0), mDate.get(1), mDate.get(2));

        showWorks();

        return view;


    }
    public void showWorks(){
        ArrayList<String> mWorks = dbHelper.getResult(day);
        for(String w : mWorks){
            ListData2 n = new ListData2();
            n.word = w;
            mAdapter.addItem(n);
        }

    }
    private void updateDate(){
        new DatePickerDialog(getActivity(), d, dateTime.get(Calendar.YEAR), dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            Log.i("month",""+monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            day = ""+ year + "-" + monthOfYear + "-" + dayOfMonth;
            Log.i("date",day);
            btn_date.setText(day);
            mAdapter.resetList();
            showWorks();
        }
    };


    public void sendMessage(View view) {
        EditText editText = (EditText) view.findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        ListData2 newword = new ListData2();

        newword.word = message;

        dbHelper.insert(message, day);

        mAdapter.addItem(newword);

        mAdapter.notifyDataSetChanged();

    }
}