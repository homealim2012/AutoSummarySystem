package nlp.sentence.token;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import nlp.util.Sentence;

public class BreakIteratorToken implements BaseSentenceToken {
	
	public static void main(String[] args){
		String text=
		        "你是,谁。How are you?" +  
		        "我是张三、李四...";
		List<Sentence> res=new BreakIteratorToken().run(text,1);
		System.out.println(res.get(2).ori_sentence);
	}
	public List<Sentence> run(String paragraph,int order){
		Locale locale = Locale.CHINA;  
		BreakIterator breakIterator =  
		        BreakIterator.getSentenceInstance(locale);
		breakIterator.setText(paragraph);  
	    List<Sentence> res=new ArrayList<Sentence>();
		int startIndex = breakIterator.first();
		while(startIndex != BreakIterator.DONE) {  
		    int endIndex = breakIterator.next();
		    Sentence s=new Sentence();
		    if(endIndex != BreakIterator.DONE)
		    	s.setOri_sentence(paragraph.substring(startIndex,endIndex));
		    else
		    	s.setOri_sentence(paragraph.substring(startIndex));
		    startIndex=endIndex;
	    	s.setOrder(order);
	    	res.add(s);    
		} 
	    return res;	
	}
	public List<Sentence> run(List<String> paragraph){
		List<Sentence> res=new ArrayList<Sentence>();
		int order=1;
		for(String text:paragraph)
		{
			List<Sentence> temp=run(text,order++);
			res.addAll(temp);
		}
		return res;
	}
}
