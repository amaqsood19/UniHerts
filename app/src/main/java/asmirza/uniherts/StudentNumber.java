/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package asmirza.uniherts;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Utility class for persisting account numbers to disk.
 *
 * <p>The default SharedPreferences instance is used as the backing storage. Values are cached
 * in memory for performance.
 *
 * <p>This class is thread-safe.
 */
public class StudentNumber {
    private static final String PREF_STUDENT_NUMBER = "account_number";
    private static final String DEFAULT_STUDENT_NUMBER = "11810637";
    private static final String TAG = "StudentNumber";
    private static String sStudent = null;
    private static final Object sStudentLock = new Object();

    public static void SetStudent(Context c, String s) {
        synchronized(sStudentLock) {
            Log.i(TAG, "Setting account number: " + s);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            prefs.edit().putString(PREF_STUDENT_NUMBER, s).commit();
            sStudent = s;
        }
    }

    public static String GetStudentNumber(Context c) {
        synchronized (sStudentLock) {
            if (sStudent == null) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
                String student = prefs.getString(PREF_STUDENT_NUMBER, DEFAULT_STUDENT_NUMBER);
                sStudent = student;
            }
            return sStudent;
        }
    }
}
