package ru.amse.agregator.searcher;

import java.util.ArrayList;

/*
 * Author: Bondarev Timofey
 * Date: Oct 24, 2010
 * Time: 2:32:27 PM
 */

public class UserQuery {
    private String query;
    private ArrayList<String> labels;

    public UserQuery(String queryExpression) {
        query = queryExpression;
        labels = new ArrayList<String>();
    }

    public UserQuery(String queryExpression, ArrayList<String> labels) {
        query = queryExpression;
        this.labels = new ArrayList<String>(labels);
    }

    public void setQueryExpression(String queryExpression) {
        query = queryExpression;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public void addLabel(String label) {
        labels.add(label);
    }

    public String getQueryExpression() {
        return query;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }
}