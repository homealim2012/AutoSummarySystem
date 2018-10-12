package nlp.util;

import java.util.ArrayList;
import java.util.List;

import com.mathworks.toolbox.javabuilder.MWCellArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWStructArray;
import TextMining.TextMiner;
import nlp.sentence.token.BaseSentenceToken;
import nlp.sentence.token.BreakIteratorToken;
import nlp.word.stopword.StopWords;
import nlp.word.token.BaseWordToken;
import nlp.word.token.JieBaToken;

public class NLP {
	public static final int type_ori=1;
	
	public static final int type_mani=2;
	
	public static final int type_lead=3;
	
	public static final int type_random=4;
	
    public static List<String> run(List<String> sourceList,
    		final int run_type) throws MWException{
    	BaseSentenceToken SentToken=new BreakIteratorToken();
    	List<Sentence> Sentences=SentToken.run(sourceList);
    	BaseWordToken WordToken=JieBaToken.getToken();
    	Sentences=WordToken.run(Sentences);
    	StopWords.removeStopWords(Sentences);
    	if(type_mani==run_type ||type_ori==run_type)
    		removeLitteSentence(Sentences);
    	Convert.setDstSources(Sentences);
    	Convert.setOrder(Sentences);
    	Sentence topicSentence=new Sentence();
    	Sentences.add(0,topicSentence);
    	List<String> SummarySentences=null;
    	switch(run_type)
    	{
    		case type_mani:
    			SummarySentences=getMatlabSentence(Sentences,run_type);
    	    break;
    		case type_ori:
    			SummarySentences=getMatlabSentence(Sentences,run_type);
    	    break;
    		case type_lead:
    			SummarySentences=getLeadSentence(Sentences);
    	    break;
    		case type_random:
    			SummarySentences=getRandomSentence(Sentences);
    	    break;
    	}	
    	return SummarySentences;
    }
    public static void removeLitteSentence(List<Sentence> list)
    {
    	for(int i=0;i<list.size();)
    	{
    		if(list.get(i).getWords()==null ||
    		   list.get(i).getWords().size()<=5)
    		{
    			list.remove(i);
    			continue;
    		}
    		i++;
    	}
    }
    
    public static List<String> filterSentence(List<String> sourceList){
    	BaseSentenceToken SentToken=new BreakIteratorToken();
    	List<Sentence> Sentences=SentToken.run(sourceList);
    	BaseWordToken WordToken=JieBaToken.getToken();
    	Sentences=WordToken.run(Sentences);
    	StopWords.removeStopWords(Sentences);
        removeLitteSentence(Sentences);
        List<String> filterSentence=new ArrayList<String>();
        for(Sentence s:Sentences)
        {
        	filterSentence.add(s.ori_sentence);
        }
        return filterSentence;
    }
    public static List<String> getMatlabSentence(List<Sentence> Sentences,
    		final int run_type)throws MWException{
    	MWStructArray mw_sentence=Convert.getMatlabStructArray(Sentences, Sentence.class);
    	TextMiner tm=new TextMiner();
    	Object[] res_obj=tm.TextMining(1, mw_sentence,run_type);
    	MWCellArray mwss=(MWCellArray)res_obj[0];	
    	List<String> SummarySentences=new ArrayList<>();
    	for(int i=1;i<=mwss.numberOfElements();i++)
    	{
    		SummarySentences.add(mwss.getCell(i).toString());
    		System.out.println(mwss.getCell(i).toString());
    	}
    	return SummarySentences;
    }
    public static  List<String> getRandomSentence(List<Sentence> list){
    	int[] index=MyRandom.getRandoms(1, list.size()-1,25);
    	List<String> res=new ArrayList<String>();
    	for(int i:index)
    	{
    		res.add(list.get(i).ori_sentence);
    	}
    	return res;
    }
    public static List<String> getLeadSentence(List<Sentence> list){
    	List<String> res=new ArrayList<String>();
    	if(list.size()<=0)
    		return res;
    	int order=list.get(0).getOrder();
    	for(int i=1;i<list.size();i++)
    		if(order!=list.get(i).getOrder())
    		{
    			res.add(list.get(i).getOri_sentence());
    			order=list.get(i).getOrder();
    			if(res.size()>=25)
    				break;
    		}
    	return res;
    }
}
