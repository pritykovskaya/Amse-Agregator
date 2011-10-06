package ru.amse.agregator.test;

import ru.amse.agregator.searcher.UserQuery;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;


/*
 * Author: Bondarev Timofey
 * Date: Nov 16, 2010
 * Time: 3:19:06 PM
 */
public class UserQueryTest {
    UserQuery query;

    @Test
    public void testFirstConstructor() {
        query = new UserQuery("Test");
        assertEquals(query.getQueryExpression(), "Test");
    }

    @Test
    public void testSecondConstructor() {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        query = new UserQuery("Test", labels);
        assertEquals(query.getQueryExpression(), "Test");
        assertEquals(query.getLabels().get(0), "0");
        assertEquals(query.getLabels().get(1), "1");
        assertEquals(query.getLabels().get(2), "2");
    }

    @Test
    public void testSetQueryExpression() {
        query = new UserQuery("Test");
        query.setQueryExpression("Test1");
        assertEquals(query.getQueryExpression(), "Test1");
    }

    @Test
    public void testSetLabelsToEmptyQuery() {
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        query = new UserQuery("Test");
        query.setLabels(labels);
        assertEquals(query.getQueryExpression(), "Test");
        assertEquals(query.getLabels().get(0), "0");
        assertEquals(query.getLabels().get(1), "1");
        assertEquals(query.getLabels().get(2), "2");
    }

    @Test
    public void testSetLabelsToNotEmptyQuery() {
    	ArrayList<String> labels = new ArrayList<String>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        query = new UserQuery("Test", labels);
        labels = new ArrayList<String>();
        labels.add("3");
        labels.add("4");
        labels.add("5");
        query.setLabels(labels);
        assertEquals(query.getQueryExpression(), "Test");
        assertEquals(query.getLabels().get(0), "3");
        assertEquals(query.getLabels().get(1), "4");
        assertEquals(query.getLabels().get(2), "5");
    }

    @Test
    public void testAddLabelToEmptyQuery() {
        query = new UserQuery("Test");
        query.addLabel("0");
        query.addLabel("1");
        assertEquals(query.getQueryExpression(), "Test");
        assertEquals(query.getLabels().get(0), "0");
        assertEquals(query.getLabels().get(1), "1");
    }

    @Test
    public void testAddLabelToNotEmpryQuery() {
    	ArrayList<String> labels = new ArrayList<String>();
        labels.add("0");
        labels.add("1");
        labels.add("2");
        query = new UserQuery("Test", labels);
        query.addLabel("3");
        assertEquals(query.getQueryExpression(), "Test");
        assertEquals(query.getLabels().get(0), "0");
        assertEquals(query.getLabels().get(1), "1");
        assertEquals(query.getLabels().get(2), "2");
        assertEquals(query.getLabels().get(3), "3");
    }
}

