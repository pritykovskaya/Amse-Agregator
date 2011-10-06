package ru.amse.agregator.quality.clusterization.metric;

import ru.amse.agregator.storage.DBWrapper;

/**
 *
 * @author pavel
 *
 * Metric is an abstract class which represents metric between two objects in general
 * Class encapsulates common static functions
 *
 */
abstract public class Metric {
    
    abstract public double compute(DBWrapper obj1, DBWrapper obj2);

    //http://en.wikipedia.org/wiki/Levenshtein_distance
    static int getLevenshteinDistance(String str1, String str2) {
        // distanceMatrix is a matrix whete element (i, j) is the levenshtein distance between
        // first i characters of str1 and first j characters of str2
        int[][] distanceMatrix = new int[str1.length() + 1][str2.length() + 1];

        //init matrix
        for (int i = 0;
                i <= str1.length();
                ++i) {
            distanceMatrix[i][0] = i;
        }
        for (int j = 0;
                j <= str2.length();
                ++j) {
            distanceMatrix[0][j] = j;
        }

        //fill the matrix using recursive formula
        for (int j = 0;
                j < str2.length();
                ++j) {
            for (int i = 0;
                    i < str1.length();
                    ++i) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    distanceMatrix[i + 1][j + 1] = distanceMatrix[i][j];
                } else {
                    distanceMatrix[i + 1][j + 1] = Math.min(
                            distanceMatrix[i][j + 1] + 1, //deletion
                            Math.min(distanceMatrix[i + 1][j] + 1, //insertion
                            distanceMatrix[i][j] + 1));   //substitution
                }
            }
        }

        return distanceMatrix[str1.length()][str2.length()];
    }

    //returns the distance between two strings clamped to range [0..1]
    static double getNormalizedDistance(String str1, String str2) {
        return ((double)getLevenshteinDistance(str1, str2) /
                (double)Math.max(str1.length(), str2.length()));
    }
}
