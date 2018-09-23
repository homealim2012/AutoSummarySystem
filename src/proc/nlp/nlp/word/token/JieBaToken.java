package nlp.word.token;

import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;

public class JieBaToken implements BaseWordToken{

	JiebaSegmenter segmenter;
	
	public JieBaToken(){
		segmenter= new JiebaSegmenter();
	}
	
	@Override
	public List<List<String>> run(String[] sentences) {

		List<List<String>> resSet=new ArrayList<List<String>>();
		for (String sentence : sentences) {
		    List<String> res=new ArrayList<String>();
		    resSet.add(res);
		    List<SegToken> tokens=segmenter.process(sentence, SegMode.SEARCH);
		    for(SegToken token:tokens)
		    {
		       res.add(token.word);
		    }
		}
		return resSet;
	}
    public static void main(String[] args){
    	 String[] sentences =
 	            new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", 
 	            			"我不喜欢日本和服。", "雷猴回归人间。","Let's go",
 	                          "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", 
 	                          "结果婚的和尚未结过婚的"};
 	    System.out.println(new JieBaToken().run(sentences));
    }
}
