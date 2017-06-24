package com.example.syed.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by syed on 2017-06-12.
 */

public final class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DESC = "desc";
        public final static String COLUMN_REPETITION = "repetition";
        public final static String COLUMN_STATUS = "status";

        public static final int REPETITION_DAILY = 0;
        public static final int REPETITION_WEEKLY = 1;
        public static final int REPETITION_MONTLY = 2;
        public static final int REPETITION_YEARLY = 3;

        public final static int STATUS_COMPLETED = 1;
        public final static int STATUS_NOT_COMPLETED = 0;
    }
}
