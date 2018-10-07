package nlp.util;

import java.util.List;

public class Sentence {
	
	public String ori_sentence;

	public String dst_sentence;

	public int order;
	
	private List<String> words;

	public String getOri_sentence() {
		return ori_sentence;
	}

	public void setOri_sentence(String ori_sentence) {
		this.ori_sentence = ori_sentence;
	}

	public String getDst_sentence() {
		return dst_sentence;
	}

	public void setDst_sentence(String dst_sentence) {
		this.dst_sentence = dst_sentence;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
