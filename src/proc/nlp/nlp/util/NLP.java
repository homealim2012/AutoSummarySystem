package nlp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWCellArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWStructArray;
import TextMining.TextMiner;
import edu.buaa.edu.wordsimilarity.WordSimilarity;
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
	
	private List<Sentence> Sentences=new ArrayList<>();
	
	private List<String> ManiSentences=new ArrayList<>();
	
	private List<String> OriSentences=new ArrayList<>();
	
	private List<String> LeadSentences=new ArrayList<>();
	
	private List<String> RandomSentences=new ArrayList<>();
	
	private String[] WordList=new String[0];
	
	private double[] MeanTopic=new double[0];
	
	private double[] VarTopic=new double[0];
	
	private double[] MeanVarTopic=new double[0];
	
	private Set<String> WordSet=new HashSet<String>(); 
	
	private List<WordSim> WordSimList=new ArrayList<>();
	
    public String[] getWordList() {
		return WordList;
	}
	public double[] getMeanTopic() {
		return MeanTopic;
	}
	public double[] getVarTopic() {
		return VarTopic;
	}
	public double[] getMeanVarTopic() {
		return MeanVarTopic;
	}
	public List<String> getManiSentences() {
		return ManiSentences;
	}
	public List<String> getOriSentences() {
		return OriSentences;
	}
	public List<String> getLeadSentences() {
		return LeadSentences;
	}
	public List<String> getRandomSentences() {
		return RandomSentences;
	}
	
	public class WordSim{
		public String ori;
		public String dst;
	    public double sim;
	}
	
	public void run(List<String> sourceList,String keyword) throws MWException{
    	BaseSentenceToken SentToken=new BreakIteratorToken();
    	Sentences=SentToken.run(sourceList);
    	Sentence topicSentence=new Sentence();
    	topicSentence.ori_sentence=keyword;
    	Sentences.add(0,topicSentence);
    	BaseWordToken WordToken=JieBaToken.getToken();
    	Sentences=WordToken.run(Sentences);
    	StopWords.removeStopWords(Sentences);
    	removeLitteSentence(Sentences);
    	Convert.setDstSources(Sentences);
    	Convert.setOrder(Sentences);
    	calWordSet();
    	calWordSim();
    	OriSentences=getMatlabSentence(Sentences,type_ori);
    	ManiSentences=getMatlabSentence(Sentences,type_mani);
    	LeadSentences=getLeadSentence(Sentences);
    	RandomSentences=getRandomSentence(Sentences);	
    }
	
	public void calWordSet(){
		for(Sentence s:Sentences){
			List<String> Words=s.getWords();
			for(String Word:Words){
				WordSet.add(Word);
			}
		}
	}
	
	public void calWordSim(){
		Sentence topicSentence=Sentences.get(0);
		List<String> topicWords=topicSentence.getWords();
		for(String topicWord:topicWords){
			for(String word:WordSet)
			{
				double sim_word_val=WordSimilarity.simWord(topicWord,word);
				if(sim_word_val>=0.1)
				{
					WordSim sim=new WordSim();
					WordSimList.add(sim);
					sim.ori=topicWord;
					sim.dst=word;
					sim.sim=sim_word_val;
				}
			}
		}
	}
	
    public void removeLitteSentence(List<Sentence> list)
    {
    	for(int i=1;i<list.size();)//第一句是中心句
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
    public List<String> getFilterSentence(){
        List<String> filterSentence=new ArrayList<String>();
        for(Sentence s:Sentences)
        {
        	filterSentence.add(s.ori_sentence);
        }
        return filterSentence;
    }
    public List<String> getMatlabSentence(List<Sentence> Sentences,
    		final int run_type)throws MWException{
    	MWStructArray mw_sentence=Convert.getMatlabStructArray(Sentences, Sentence.class);
    	TextMiner tm=new TextMiner();
    	Object[] res_obj=tm.TextMining(5, mw_sentence,run_type);
    	MWCellArray mwss=(MWCellArray)res_obj[0];	
    	List<String> SummarySentences=new ArrayList<>();
    	for(int i=1;i<=mwss.numberOfElements();i++)
    	{
    		SummarySentences.add(mwss.getCell(i).toString());
    		System.out.println(mwss.getCell(i).toString());
    	}
    	setWordList(res_obj[1]);
    	setMeanTopic(res_obj[2]);
    	setVarTopic(res_obj[3]);
    	setMeanVarTopic(res_obj[4]);
    	return SummarySentences;
    }
    
    public void setWordList(Object obj){
    	MWCellArray mwss=(MWCellArray)obj;	
    	this.WordList=new String[mwss.numberOfElements()];
    	for(int i=1;i<=mwss.numberOfElements();i++)
    	{
    	   WordList[i-1]=mwss.getCell(i).toString();	
    	}
    }
    public void setMeanTopic(Object obj){
    	MWArray mwa=(MWArray)obj;
    	this.MeanTopic=new double[mwa.numberOfElements()];
    	for(int i=1;i<=mwa.numberOfElements();i++)
    	{
    		MeanTopic[i-1]=(Double)mwa.get(i);
    	}
    }
    public void setVarTopic(Object obj){
    	MWArray mwa=(MWArray)obj;
    	this.VarTopic=new double[mwa.numberOfElements()];
    	for(int i=1;i<=mwa.numberOfElements();i++)
    	{
    		VarTopic[i-1]=(Double)mwa.get(i);
    	}
    }
    public void setMeanVarTopic(Object obj){
    	MWArray mwa=(MWArray)obj;
    	this.MeanVarTopic=new double[mwa.numberOfElements()];
    	for(int i=1;i<=mwa.numberOfElements();i++)
    	{
    		MeanVarTopic[i-1]=(Double)mwa.get(i);
    	}
    }
    public List<String> getRandomSentence(List<Sentence> list){
    	int[] index=MyRandom.getRandoms(1, list.size()-1,25);
    	List<String> res=new ArrayList<String>();
    	for(int i:index)
    	{
    		res.add(list.get(i).ori_sentence);
    	}
    	return res;
    }
    public List<String> getLeadSentence(List<Sentence> list){
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
