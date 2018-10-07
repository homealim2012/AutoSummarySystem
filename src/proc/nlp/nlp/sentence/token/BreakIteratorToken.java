package nlp.sentence.token;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nlp.util.Sentence;

public class BreakIteratorToken implements BaseSentenceToken {
	
	public static void main(String[] args){
		String text=
		        "你是谁。How are you?" +  
		        "我是张三、李四...";
		List<Sentence> res=new BreakIteratorToken().run(text);
		System.out.println(res.get(2).ori_sentence);
	}
	public List<Sentence> run(String paragraph){
		Locale locale = Locale.CHINA;  
		BreakIterator breakIterator =  
		        BreakIterator.getSentenceInstance(locale);
		breakIterator.setText(paragraph);  
	    List<Sentence> res=new ArrayList<Sentence>();
		int startIndex = breakIterator.first();
		int order=1;
		while(startIndex != BreakIterator.DONE) {  
		    int endIndex = breakIterator.next();
		    Sentence s=new Sentence();
		    if(endIndex != BreakIterator.DONE)
		    	s.setOri_sentence(paragraph.substring(startIndex,endIndex));
		    else
		    	s.setOri_sentence(paragraph.substring(startIndex));
		    startIndex=endIndex;
	    	s.setOrder(order++);
	    	res.add(s);    
		} 
	    return res;	
	}
}
