package com.example.waitlist;

import android.provider.BaseColumns;

public class WaitListContract {

    public WaitListContract() {
    }

    public final class WaitListEntry implements BaseColumns{

        public static final String Table_Name="waitList";
        public static final String Column_Name="name";
        public static final String Column_Amount="amount";
        public static final String Column_TIMESTAMP="timestamp";

    }
}
