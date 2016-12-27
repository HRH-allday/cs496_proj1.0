package com.example.q.myapplication;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by q on 2016-12-27.
 */

public class ListData {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */

    public String mName;

    public String mNumber;

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.mName, mListDate_2.mName);
        }
    };
}