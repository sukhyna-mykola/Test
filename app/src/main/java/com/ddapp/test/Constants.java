package com.ddapp.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mykola on 20.02.17.
 */

public class Constants {

    public static final String TAG = "Tag";

    public static final String DATA_URL = "https://ddapp-sfa-api-dev.azurewebsites.net/api/test/students";

    public static final String STUDENTS_TAG = "ArrayOfTestController.Student";
    public static final String STUDENT_TAG = "TestController.Student";
    public static final String COURSE_TAG = "TestController.Course";

    public static final String BIRTHDAY_TAG = "birthday";
    public static final String COURSES_TAG = "courses";
    public static final String MARK_TAG = "mark";
    public static final String NAME_TAG = "name";
    public static final String FIRST_NAME_TAG = "firstName";
    public static final String LAST_NAME_TAG = "lastName";
    public static final String ID_TAG = "id";

    public static final String ID_KEY = "com.ddapp.test.ID_KEY";

    public static final String FILTER_DIALOG_TAG = "com.ddapp.test.FILTER_DIALOG";
    public static final String INFO_DIALOG_TAG = "com.ddapp.test.INFO_DIALOG";
    public static final String LOGIN_KEY = "com.ddapp.test.LOGIN_KEY";
    public static final String SHARED_PREFERENCES = "com.ddapp.test.preferences";


    public static final String DB_NAME = "db_students";
    public static final String TABLE_STUDENTS = "students";
    public static final String TABLE_COURSES = "courses";

    public static final String BIRTHDAY_FIELD = "birthday";
    public static final String FIRST_NAME_FIELD = "first_name";
    public static final String COURSE_NAME_FIELD = "course_name";
    public static final String LAST_NAME_FIELD = "last_name";
    public static final String ID_STUDENT_FIELD = "id_student";
    public static final String ID_COURSE_FIELD = "id_course";

    public static final String COURSE_NULL_FIELD = "coursenull";
    public static final String COURSE_FIRST_FIELD = "coursefirst";
    public static final String COURSE_SECOND_FIELD = "coursesecond";
    public static final String COURSE_THIRD_FIELD = "coursethird";

    public static final String [] COURSES= {"Course-0","Course-1","Course-2","Course-3"};

    public static final Map<String, String> coursesMap;
        static {
        Map<String, String> aMap =new HashMap<>();
        aMap.put(COURSES[0], COURSE_NULL_FIELD);
        aMap.put(COURSES[1], COURSE_FIRST_FIELD);
        aMap.put(COURSES[2], COURSE_SECOND_FIELD);
        aMap.put(COURSES[3], COURSE_THIRD_FIELD);
        coursesMap = Collections.unmodifiableMap(aMap);
    }

    public static final String INTEGER = "integer";
    public static final String TEXT = "text";

}
