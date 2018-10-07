package nlp.sentence.token;

import java.util.List;

import nlp.util.Sentence;

public interface BaseSentenceToken {
	public List<Sentence> run(String paragraph);
}
