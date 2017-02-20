package com.ddapp.test;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class XmlParser {
    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, "UTF-8");
            parser.nextTag();
            return readStudents(parser);
        } finally {
            in.close();
        }
    }

    private List readStudents(XmlPullParser parser) throws XmlPullParserException, IOException {
        List students = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, Constants.STUDENTS_TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(Constants.STUDENT_TAG)) {
                students.add(readStudent(parser));
            } else {
                skip(parser);
            }
        }
        return students;
    }

    private Student readStudent(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.STUDENT_TAG);

        String firstName = null;
        String lastName = null;
        String id = null;
        List<Course> courses = null;
        String birstday = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(Constants.FIRST_NAME_TAG)) {
                firstName = readTag(parser, Constants.FIRST_NAME_TAG);
            } else if (name.equals(Constants.LAST_NAME_TAG)) {
                lastName = readTag(parser, Constants.LAST_NAME_TAG);
            } else if (name.equals(Constants.BIRTHDAY_TAG)) {
                birstday = readTag(parser, Constants.BIRTHDAY_TAG);
            } else if (name.equals(Constants.ID_TAG)) {
                id = readTag(parser, Constants.ID_TAG);
            } else if (name.equals(Constants.COURSES_TAG)) {
                courses = readTagArray(parser, Constants.COURSES_TAG);
            } else {
                skip(parser);
            }
        }

        return new Student(id, firstName, lastName, Long.parseLong(birstday), courses);

    }

    private Course readCourse(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.COURSE_TAG);

        String name = null;
        String mark = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nameTag = parser.getName();
            if (nameTag.equals(Constants.NAME_TAG)) {
               name = readTag(parser, Constants.NAME_TAG);
            } else if (nameTag.equals(Constants.MARK_TAG)) {
                mark = readTag(parser, Constants.MARK_TAG);
            } else {
                skip(parser);
            }
        }

        return new Course(name, Byte.parseByte(mark));

    }

    // Processes title tags in the feed.
    private String readTag(XmlPullParser parser, String TAG) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, TAG);
        String data = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, TAG);
        return data;
    }


    // Processes link tags in the feed.
    private List<Course> readTagArray(XmlPullParser parser, String TAG) throws IOException, XmlPullParserException {
        List<Course> courses = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, TAG);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(Constants.COURSE_TAG)) {
                courses.add(readCourse(parser));
            } else {
                skip(parser);
            }
        }

        return courses;
    }

    // For the tags title and summary, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
