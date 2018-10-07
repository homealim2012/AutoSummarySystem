package nlp.word.token;

import java.util.*;

import nlp.util.Sentence;

public interface BaseWordToken {
	
     public List<Sentence> run(List<Sentence> sentences);
     
}
