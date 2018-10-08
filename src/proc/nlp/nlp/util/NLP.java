package nlp.util;

import java.util.ArrayList;
import java.util.List;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWStructArray;
import TextMining.TextMiner;
import nlp.sentence.token.BaseSentenceToken;
import nlp.sentence.token.BreakIteratorToken;
import nlp.word.stopword.StopWords;
import nlp.word.token.BaseWordToken;
import nlp.word.token.JieBaToken;

public class NLP {
    public static List<String> run(List<String> sourceList) throws MWException{
    	BaseSentenceToken SentToken=new BreakIteratorToken();
    	List<Sentence> Sentences=SentToken.run(sourceList);
    	BaseWordToken WordToken=JieBaToken.getToken();
    	Sentences=WordToken.run(Sentences);
    	StopWords.removeStopWords(Sentences);
    	removeLitteSentence(Sentences);
    	Convert.setDstSources(Sentences);
    	Convert.setOrder(Sentences);
    	Sentence topicSentence=new Sentence();
    	Sentences.add(0,topicSentence);
    	MWStructArray mw_sentence=Convert.getMatlabStructArray(Sentences, Sentence.class);
    	TextMiner tm=new TextMiner();
    	Object[] obj=tm.TextMining(1, mw_sentence);
    	System.out.println(obj[0].getClass());
    	List<String> SummarySentences=new ArrayList<>();
    	return SummarySentences;
    }
    public static void removeLitteSentence(List<Sentence> list)
    {
    	for(int i=0;i<list.size();)
    	{
    		if(list.get(i).getWords()==null &&
    		   list.get(i).getWords().size()<=3)
    			continue;
    		i++;
    	}
    }
}
