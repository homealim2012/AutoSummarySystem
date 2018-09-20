package edu.as.sys.common;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Created by jianying.lin@foxmail.com on 2016/10/24.
 */
public class FileIO {

	public static ArrayList<String> fileRead(String filePath, String encoding) { //read encoding
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, encoding);
			reader = new BufferedReader(isr);
			String tempString = null;
			ArrayList result = new ArrayList<String>();
			int line = 0;
			while ((tempString = reader.readLine()) != null) {
				result.add(tempString);
				line++;
			}
			reader.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error!!! readWordsFile error!");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return null;
	}

	public static boolean fileWrite(String filePath, ArrayList info, String encoding) {
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			OutputStreamWriter out = new OutputStreamWriter(fos, encoding);
			for (int i=0; i<info.size(); i++) {
				out.write(info.get(i).toString()+"\r\n");
				out.flush();
			}
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public static boolean fileWrite(String filePath, TreeSet words) {
		try {			
	        File writename = new File(filePath); 
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768); 
	        for(Iterator it = words.iterator(); it.hasNext();) {
	            String now = it.next().toString();
	            out.write(now+"\r\n");
	            out.flush();
	        }
	        out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

    public static boolean fileWrite(String filePath, String info, String encoding) {
        ArrayList<String> infoList = new ArrayList<String>();
        infoList.add(info);
        return fileWrite(filePath, infoList, encoding);
    }
	
	public static boolean fileWrite(String filePath, ArrayList info) {
		try {			
	        File writename = new File(filePath); 
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);
	        for (int i=0; i<info.size(); i++) {
	        	out.write(info.get(i).toString()+"\n");
	        	out.flush();
	        }
	        out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public static void fileWriteTfidfSparse(String filePath, String outputFilePath, ArrayList tfidf, HashMap wordsHashMap) { //稀疏表示
		File file = new File(filePath);
        BufferedReader reader = null;
		try {
			File writename = new File(outputFilePath);
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);

	        reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	ArrayList tfidfInEachDoc = (ArrayList<Double>)tfidf.get(line);
            	String[] tempWord = tempString.split(" ");
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			out.write(wordsHashMap.get(tempWord[i]).toString()+":"+tfidfInEachDoc.get(i).toString()+" ");
            			out.flush();
            		}
            	}
	        	out.write("\n");
		        out.flush();
                line++;
            }
            reader.close();
	        out.flush();
			out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	public static void fileWriteTfidfNotSparse(String filePath, String outputFilePath, ArrayList tfidf, HashMap wordsHashMap, TreeSet words) { //输出term*doc的非稀疏表示矩阵
		ArrayList<String> fileInfo = fileRead(filePath);
		double[][] tfidfArray = new double[words.size()][fileInfo.size()];		
		for (int i=0; i<words.size(); i++) {
			for (int j=0; j<fileInfo.size(); j++) {
				tfidfArray[i][j] = 0.0; 
			}
		}
		for (int i=0; i<fileInfo.size(); i++) {
			String[] tempWord = fileInfo.get(i).split(" ");
			ArrayList tfidfInEachDoc = (ArrayList<Double>)tfidf.get(i);
			for (int j=0; j<tempWord.length; j++) {
        		if (!tempWord[j].equals("")) {
        			tfidfArray[Integer.parseInt(wordsHashMap.get(tempWord[j]).toString())-1][i] = Double.parseDouble(tfidfInEachDoc.get(j).toString());
        		}
        	}
		}
		ArrayList<String> tfidfStr = new ArrayList<String>();
		for (int i=0; i<words.size(); i++) {
			StringBuffer info = new StringBuffer("");
			for (int j=0; j<fileInfo.size(); j++) {
				info.append(String.format("%.9f", tfidfArray[i][j]));
				info.append(" ");
			}
			tfidfStr.add(info.toString());
		}
		fileWrite(outputFilePath, tfidfStr);
	}
	
	public static void fileWrite(String filePath, String filterFilePath, ArrayList idf, double idfMin, double idfMax) {
		int docNum = readLinesNumFile(filePath);
		File file = new File(filePath);
        BufferedReader reader = null;
		try {
			File writename = new File(filterFilePath); 
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);

	        reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            //out.write(docNum+"\n");
            while ((tempString = reader.readLine()) != null) {
            	ArrayList idfInEachDoc = (ArrayList<Double>)idf.get(line);
            	String[] tempWord = tempString.split(" ");
            	boolean firstWord = true;
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			double idfValue = Double.parseDouble(idfInEachDoc.get(i).toString());
            			if (idfValue >= idfMin && idfValue <= idfMax) {
            				if (firstWord) {
            					firstWord = false;
            				}
            				else {
            					out.write(" ");
            				}
            				out.write(tempWord[i]);
            				out.flush();
            			}
            		}
            	}
	        	out.write("\n");
		        out.flush();
                line++;
            }
            reader.close();
	        out.flush();
			out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	public static boolean checkFileExist(String filePath) {
		File writename = new File(filePath); 
		return writename.exists();
	}

	public static TreeSet readWordsFile(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            TreeSet words = new TreeSet();
            words.clear();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	words.add(tempString);
                line++;
            }
            reader.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static TreeSet readResultFile(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            TreeSet words = new TreeSet();
            words.clear();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	String[] tempWord = tempString.split(" ");
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			words.add(tempWord[i]);
            		}
            	}
                line++;
            }
            reader.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static TreeSet readResultFileWithIntegerTerms(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            TreeSet<Integer> words = new TreeSet<Integer>();
            words.clear();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	String[] tempWord = tempString.split(" ");
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			words.add(Integer.parseInt(tempWord[i]));
            		}
            	}
                line++;
            }
            reader.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static ArrayList<HashSet<String>> readWordSetEachDocument(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            ArrayList words = new ArrayList<HashSet<String>>();
            while ((tempString = reader.readLine()) != null) {
            	String[] tempWord = tempString.split(" ");
            	HashSet wordSet = new HashSet<String>();
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			wordSet.add(tempWord[i]);
            		}
            	}
            	words.add(wordSet);
                line++;
            }
            reader.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static int readLinesNumFile(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            reader.close();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsNumFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return 0;
	}
	
	public static ArrayList<String> fileRead(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            ArrayList result = new ArrayList<String>();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	result.add(tempString);
                line++;
            }
            reader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static HashMap<String, Integer> fileReadToStringIndexMap(String filePath) { //Index start from 0
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            HashMap<String, Integer> result = new HashMap<String, Integer>();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	result.put(tempString, line);
                line++;
            }
            reader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}

	public static void fileWriteSparseMatrix(String filePath, double[][] X) {
		try {
	        File writename = new File(filePath); 
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);
	        for (int i=0; i<X.length; i++) {
	        	for (int j=0; j<X[i].length; j++) {
	        		if (X[i][j] > 0.0) {
	        			out.write((j+1)+":"+X[i][j]+" ");
	        		}
	        	}
	        	out.write("\n");
		        out.flush();
	        }
	        out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void fileWriteFullMatrix(String filePath, double[][] X) {
		try {
	        File writename = new File(filePath); 
	        writename.createNewFile();
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename),32768);
	        for (int i=0; i<X.length; i++) {
	        	for (int j=0; j<X[i].length; j++) {
	        		if (j > 0) {
	        			out.write(" ");
	        		}
	        		out.write(X[i][j]+"");
	        	}
	        	out.write("\n");
		        out.flush();
	        }
	        out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMap(String filePath) {
		return	fileReadToStringSetMap(filePath, null);
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMap(String filePath, HashSet<String> wordmap) {
		return fileReadToStringSetMap(filePath, wordmap, null);
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMap(String filePath, HashSet<String> wordmap, String encoding) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
        	if (encoding==null || encoding.length()==0) {
        		reader = new BufferedReader(new FileReader(file));
        	}
        	else {
	            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encoding);
	            reader = new BufferedReader(isr);
        	}
    		HashMap<String, HashSet<Integer>> result = new HashMap<String, HashSet<Integer>>();
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	String[] info = tempString.split(" ");
            	if (info.length > 0 && ((wordmap!=null&&wordmap.contains(info[0])) || wordmap==null)) {
            		if (!result.containsKey(info[0])) {
        				result.put(info[0], new HashSet<Integer>());
        			}
	            	for (int i=1; i<info.length; i++) {
	            		if (info[i].length() > 0) {
	                		result.get(info[0]).add(Integer.parseInt(info[i])); 
	            		}
	            	}
            	}
                line++;
                if (line % 1000 == 0) {
                	System.out.println("fileReadToStringSetMap: "+line);
                }
            }
            reader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMapByOriginalCorpus(String filePath) {
		return	fileReadToStringSetMapByOriginalCorpus(filePath, null);
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMapByOriginalCorpus(String filePath, HashSet<String> wordmap) {
		return fileReadToStringSetMapByOriginalCorpus(filePath, wordmap, null);
	}
	
	public static HashMap<String, HashSet<Integer>> fileReadToStringSetMapByOriginalCorpus(String filePath, HashSet<String> wordmap, String encoding) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
        	if (encoding==null || encoding.length()==0) {
        		reader = new BufferedReader(new FileReader(file));
        	}
        	else {
	            InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encoding);
	            reader = new BufferedReader(isr);
        	}
    		HashMap<String, HashSet<Integer>> result = new HashMap<String, HashSet<Integer>>();
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	String[] info = tempString.split(" ");
            	if (info.length > 0) {
            		for (int i=0; i<info.length; i++) {
            			if ((wordmap!=null&&wordmap.contains(info[0])) || wordmap==null) {
            				if (!result.containsKey(info[i])) {
                				result.put(info[i], new HashSet<Integer>());
                			}
            				result.get(info[i]).add(line+1); //index start from 1
            			}
            		}
            	}
                line++;
                if (line % 5000 == 0) {
                	System.out.println("fileReadToStringSetMapByOriginalCorpus: "+line);
                }
            }
            reader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static HashSet<String> fileReadWords(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            HashSet<String> words = new HashSet<String>();
            while ((tempString = reader.readLine()) != null) {
            	String[] tempWord = tempString.split(" ");
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			words.add(tempWord[i]);
            		}
            	}
                line++;
            }
            reader.close();
            return words;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}
	
	public static ArrayList<HashSet<String>> readFileWords(String filePath) {
		File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            ArrayList result = new ArrayList<HashSet<String>>();
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	String[] tempWord = tempString.split(" ");
            	HashSet words = new HashSet<String>();
            	for (int i=0; i<tempWord.length; i++) {
            		if (!tempWord[i].equals("")) {
            			words.add(tempWord[i]);
            		}
            	}
            	result.add(words);
                line++;
            }
            reader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!! readWordsFile error!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;        
	}

}
