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
	
	private Set<String> WordSet=new HashSet<String>(); 
	
	private List<WordSim> WordSimList=new ArrayList<>();
	
	private Result result=new Result();
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
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
	public static class Result{
		
		public String[] res_sentences=new String[0];
		
		public String[] wordlist=new String[0];
		
		public double[] sim_word_topic= new double[0];
		
		public double[] mean_topic=new double[0];
		
		public double[] var_topic=new double[0];
		
		public double[] mean_var_topic=new double[0];
		
		public String[] getRes_sentences() {
			return res_sentences;
		}

		public void setRes_sentences(String[] res_sentences) {
			this.res_sentences = res_sentences;
		}

		public String[] getWordlist() {
			return wordlist;
		}

		public void setWordlist(String[] wordlist) {
			this.wordlist = wordlist;
		}

		public double[] getSim_word_topic() {
			return sim_word_topic;
		}

		public void setSim_word_topic(double[] sim_word_topic) {
			this.sim_word_topic = sim_word_topic;
		}

		public double[] getMean_topic() {
			return mean_topic;
		}

		public void setMean_topic(double[] mean_topic) {
			this.mean_topic = mean_topic;
		}

		public double[] getVar_topic() {
			return var_topic;
		}

		public void setVar_topic(double[] var_topic) {
			this.var_topic = var_topic;
		}

		public double[] getMean_var_topic() {
			return mean_var_topic;
		}

		public void setMean_var_topic(double[] mean_var_topic) {
			this.mean_var_topic = mean_var_topic;
		}	
	}
	public List<String> getDstSentences(){
		List<String> res=new ArrayList<String>();
		for(Sentence s:Sentences){
			res.add(s.dst_sentence);
		}
		return res;
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
				if(sim_word_val>=0.2)
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
    	MWStructArray sim_word_list=Convert.getMatlabStructArray(this.WordSimList, WordSim.class);
    	TextMiner tm=new TextMiner();
    	Object[] res_obj=tm.TextMining(1, mw_sentence,run_type,sim_word_list);
    	List<Result> result=Convert.getJavaObjFromMatlabStructArray(res_obj[0],Result.class);	
    	List<String> SummarySentences=new ArrayList<>();
    	if(result!=null && result.size()>0)
    	{
    		Result res=result.get(0);
    		this.result=res;
    		for(int i=0;i<res.res_sentences.length;i++)
    		{
    			SummarySentences.add(res.res_sentences[i]);
    			System.out.println(res.res_sentences[i].toString());
    		}
    	}
    	return SummarySentences;
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
    	for(int i=1;i<list.size();i++)
    	{
    		int order=list.get(i).getOrder();
    		if(order==1)
    		{
    			res.add(list.get(i).getOri_sentence());
    			if(res.size()>=25)
    				break;
    		}
    	}
    	return res;
    }
}
