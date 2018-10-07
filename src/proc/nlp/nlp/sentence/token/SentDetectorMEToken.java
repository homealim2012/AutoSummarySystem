package nlp.sentence.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import nlp.util.Sentence;
import opennlp.tools.sentdetect.*;

public class SentDetectorMEToken implements BaseSentenceToken {
	private static SentenceDetectorME dector= null;
	private static SentDetectorMEToken token= null;
	private SentDetectorMEToken() throws IOException
	{
		
		InputStream modelIn = new FileInputStream("src/main/resources/model/en-sent.bin");
		SentenceModel  model= new SentenceModel(modelIn);
		dector=new SentenceDetectorME(model);
	}
	public synchronized static SentDetectorMEToken getToken() throws IOException{
	     if(token==null)
	     {
	    	 token=new SentDetectorMEToken();
	     }
	     return token;
	}
	public List<Sentence> run(String paragraph){
       String[] sents=dector.sentDetect(paragraph);
       List<Sentence> res=new ArrayList<Sentence>();
       for(int i=0;i<sents.length;i++)
       {
    	   Sentence s=new Sentence();
    	   s.setOri_sentence(sents[i]);
    	   s.setOrder(i+1);
    	   res.add(s);
       }
       return res;	
    }
	
	public List<Sentence> run(List<String> paragraph){
		return null;
	}
	public static void main(String[] args) throws IOException{
		File a=new File("src/main/java/1.txt");
		System.out.println(a.getAbsolutePath());
		String paragraph="Hi. How are you?  This is      &3 $444 Mike.";
		List<Sentence> res=SentDetectorMEToken.getToken().run(paragraph);
		System.out.println(res.get(1).ori_sentence);
		
	}
}
