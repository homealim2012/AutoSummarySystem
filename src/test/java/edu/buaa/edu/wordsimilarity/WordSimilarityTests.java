/*
 * Copyright (C) 2008 SKLSDE(State Key Laboratory of Software Development and Environment, Beihang University)., All Rights Reserved.
 */
package edu.buaa.edu.wordsimilarity;

import junit.framework.TestCase;


/**
 * DOCUMENT ME!
 *
 * @author Yingqiang Wu
 * @version 1.0
  */
public class WordSimilarityTests extends TestCase {
    public void test_loadGlossary(){
        WordSimilarity.loadGlossary();
    }
    /**
     * test the method {@link WordSimilarity#disPrimitive(String, String)}.
     */
    public void test_disPrimitive(){
        int dis = WordSimilarity.disPrimitive("����", "����");
        System.out.println("���� and ���� dis : "+ dis);
    }
    
    public void test_simPrimitive(){
        double simP = WordSimilarity.simPrimitive("����", "����");
        System.out.println("���� and ���� sim : "+ simP);
    }
    public void test_simWord(){
        String word1 = "ţ";
        String word2 = "��";
        double sim = WordSimilarity.simWord(word2, word1);
        System.out.println(sim);
    }
    
}
