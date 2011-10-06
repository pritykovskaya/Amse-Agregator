/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.amse.agregator.quality.clusterization.textquality;

import java.io.File;

/**
 *
 * @author pavel
 */
public class TextQualityTester {

    public static void main(String[] args) throws Exception {
        File myDir = new File("");
        System.out.println(myDir.getAbsolutePath());

        TextQuality t = new TextQuality();
        t.readGoodVocabularyFromFile("myTestFile.txt");
        t.prepareForSearch();
        System.out.println(t.rateQuality("huihui test test testest file file filtes"));
    }

}
