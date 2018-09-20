package edu.as.sys.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dell on 2016/12/20.
 */
public class ModelResult {
    //    private HashMap<String, Integer> W2I = new HashMap<>();
    private HashMap<Integer, String> I2W = new HashMap<>();

//    private int[][] D2W;
//    private int[][] ASSIGN;
//    private int[][] D2W;

    private double[][] W2T_Vec;
    private double[][] D2T;
    private HashMap[] D2T_Map;
    //    private HashMap<Integer, Integer>[] T2D;
    private double[][] T2W;
    private HashMap[] T2W_Map;
    private List<String> photoList;

    //    private HashMap<String, Float>[] TOPIC_WORD;
    //    private HashMap<Integer, Integer>[] W2T;
    private int topic_num = 0;

    private List<List<String>> Docs;

    private HashMap<Integer, Integer> W2T = new HashMap<>();


    public int[] wordCount;
    public int[] wordIDF;

    private double[] Coherence_5;
    private double[] Coherence_10;

    private double[] Rate;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ModelResult(int topic_num, HashMap<Integer, String> I2W) {
        this.topic_num = topic_num;
        this.I2W = I2W;
    }
    public HashMap<Integer, String> getI2W() {
        return I2W;
    }

    public void setI2W(HashMap<Integer, String> i2W) {
        I2W = i2W;
    }

//    public int[][] getD2W() {
//        return D2W;
//    }
//
//    public void setD2W(int[][] d2W) {
//        D2W = d2W;
//    }

    public double[][] getD2T() {
        return D2T;
    }

    public void setD2T(double[][] d2T) {
        D2T = d2T;
        this.D2T_Map = List2Map(D2T);
    }

    public void setD2T(int[][] d2T) {
        D2T = intList2DoubleList(d2T);
    }

    private static HashMap[] List2Map(int[][] list) {
        HashMap[] store = new HashMap[list.length];
        for (int i = 0; i < list.length; i++) {
            store[i] = new HashMap();
            for (int k = 0; k < list[i].length; k++) {
                if (list[i][k] > 0) {
                    store[i].put(k, list[i][k]);
                }
            }
        }
        return store;

    }

    private static HashMap[] List2Map(double[][] list) {
        HashMap[] store = new HashMap[list.length];
        for (int i = 0; i < list.length; i++) {
            store[i] = new HashMap();
            for (int k = 0; k < list[i].length; k++) {
                if (list[i][k] > 0) {
                    store[i].put(k, list[i][k]);
                }
            }
        }
        return store;

    }

    public static double[][] intList2DoubleList(int[][] data) {

        double[][] store = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            store[i] = new double[data[i].length];
            int sum = 0;
            for (int k = 0; k < data[i].length; k++) {
                sum += data[i][k];
            }
            for (int k = 0; k < data[i].length; k++) {
                if (sum > 0 && data[i][k] > 0) {
                    store[i][k] = (float) data[i][k] / sum;
                }
            }
        }
        return store;
    }

    public double[][] getT2W() {
        return T2W;
    }

//    public void setT2W(double[][] t2W) {
//        T2W = t2W;
//    }

    public void setT2W(int[][] t2W) {
        T2W = intList2DoubleList(t2W);
        this.T2W_Map = List2Map(t2W);
        setRate();
    }

    public double[] getCoherence_5() {
        return Coherence_5;
    }

    public void setCoherence_5(double[] coherence_5) {
        Coherence_5 = coherence_5;
    }

    public double[] getCoherence_10() {
        return Coherence_10;
    }

    public void setCoherence_10(double[] coherence_10) {
        Coherence_10 = coherence_10;
    }

    public int getTopic_num() {
        return topic_num;
    }

    public void setTopic_num(int topic_num) {
        this.topic_num = topic_num;
    }

    public HashMap<Integer, Integer> getW2T() {
        return W2T;
    }

    public void setW2T(HashMap<Integer, Integer> w2T) {
        W2T = w2T;
    }

    public void setW2T(int[][] w2t) {
        for (int i = 0; i < w2t.length; i++) {
            int topic_id = -1, max = 0, second_max = 0;
            for (int k = 0; k < w2t[i].length; k++) {
                if (w2t[i][k] > max) {
                    second_max = max;
                    max = w2t[i][k];
                    topic_id = k;
                }
            }
            if (!(max > second_max)) {
                topic_id = -1;
            }
            this.W2T.put(i, topic_id);
        }
    }

    public void setDocs(List<List<String>> docs) {
        this.Docs = docs;
    }

    public List<List<String>> getDocs() {
        return this.Docs;
    }

    public HashMap[] getD2T_Map() {
        return D2T_Map;
    }

//    public void setD2T_Map(HashMap[] d2T_Map) {
//        D2T_Map = d2T_Map;
//    }

    public HashMap[] getT2W_Map() {
        return T2W_Map;
    }

//    public void setT2W_Map(HashMap[] t2W_Map) {
//        T2W_Map = t2W_Map;
//    }

    public int[] getWordCount() {
        return wordCount;
    }

    public void setWordCount(int[] wordCount) {
        this.wordCount = wordCount;
    }

    public int[] getWordIDF() {
        return wordIDF;
    }

    public void setWordIDF(int[] wordIDF) {
        this.wordIDF = wordIDF;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    public double[][] getW2T_Vec() {
        return W2T_Vec;
    }

    public void setW2T_Vec(int[][] w2T_Vec) {
        W2T_Vec = intList2DoubleList(w2T_Vec);
    }

    public double[] getRate() {
        return Rate;
    }

    public void setRate() {
        int[] flags = new int[this.I2W.size()];

        int pcount = 0, ncount = 0;
        for (int i = 0; i < flags.length; i++) {
            if (Sentiment_word.POSITIVE.contains(this.I2W.get(i))) {
                flags[i] = 1;
                pcount++;
            } else if (Sentiment_word.NEGATIVE.contains(this.I2W.get(i))) {
                flags[i] = -1;
                ncount++;
            }
        }
        this.Rate = new double[topic_num];
        for (int k = 0; k < topic_num; k++) {
            double all = 0;
            for (int i = 0; i < flags.length; i++) {
                this.Rate[k] += this.T2W[k][i] * flags[i];
                if (flags[i] != 0) {
                    all += this.T2W[k][i];
                }
            }
            if (all != 0) {
                this.Rate[k] /= all;
            } else {
                this.Rate[k] = 0;
            }
        }

    }
}
