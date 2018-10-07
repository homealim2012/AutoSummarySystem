package nlp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.mockito.internal.util.reflection.Fields;

import com.mathworks.toolbox.javabuilder.MWStructArray;

public class Convert {
     public static <T> MWStructArray getMatlabStructArray(List<T> list,Class<T> clz) 
     {
    	 
    	 String[] sfields= getFieldsStr(clz);
    	 MWStructArray mwsa=new MWStructArray(1, list.size(), sfields);
    	 for(int i=0;i<list.size();i++)
    	 {
    		 T ele=list.get(i);
    		 Field[] fields=ele.getClass().getFields();
    		 for(Field f:fields)
        	 {
    			f.setAccessible(true);
        		Object obj=null;
				try {
					obj = f.get(ele);
				} catch (IllegalArgumentException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
        		 mwsa.set(f.getName(), i+1, obj);
        	 }
    	 }
    	 return mwsa;
     }
     public static <T> String[] getFieldsStr(Class<T> clz)
     {
    	 List<String> fieldsList = new ArrayList<>();
    	 Field[] fields=clz.getFields();
    	 for(Field f:fields)
    	 {
    		 fieldsList.add(f.getName());
    	 }
    	 String[] sfield=new String[fieldsList.size()];
    	 return fieldsList.toArray(sfield);
     }
     
     public static void setDstSources(List<Sentence> list)
     {
    	 for(Sentence s:list)
    	 {
    		 List<String> Words=s.getWords();
    		 if(Words!=null)
    		 {	
    		    s.setDst_sentence(StringUtils.join(Words, ' '));
    		 }
    	 }
     }
     public static void setOrder(List<Sentence> list)
     {
    	 int order=1;
    	 int index=0;
    	 for(Sentence s:list)
    	 {
            int tempIndex=s.order;
            if(s.order!=index)
            	order=1;
            s.order=order++;
            index=tempIndex;
    	 }
     }
     public static void main(String[] args){
    	 List<Sentence> list=new ArrayList<>();
    	 list.add(new Sentence());
    	 list.add(new Sentence());
    	 list.get(1).dst_sentence="222 332";
    	 list.get(1).order=200;
    	 MWStructArray mwsa=getMatlabStructArray(list,Sentence.class);
    	 System.out.println(mwsa.getField("order", 2));
     }
     
}
