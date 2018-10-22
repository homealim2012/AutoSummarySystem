package nlp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.buf.StringUtils;
import org.mockito.internal.util.reflection.Fields;

import com.mathworks.toolbox.javabuilder.MWArray;
import com.mathworks.toolbox.javabuilder.MWCellArray;
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
     public static <T> List<T> getJavaObjFromMatlabStructArray(Object obj,Class<T> clz){
    	 if(obj.getClass()!=MWStructArray.class)
    		 return null;
    	 try {
    		List<T> res=new ArrayList<T>();
    		MWStructArray mws=(MWStructArray)obj;
    		for(int i=0;i<mws.numberOfElements();i++)
    		{			
    			T t=clz.newInstance();
    			Field[] fs=clz.getFields();
    			for(Field f:fs){
    			   if(f.getType()==String[].class)
    			   {
    			      f.set(t, getJavaStringArray(mws.getField(f.getName(), i+1)));
    			   }
    			   else if(f.getType()==double[].class){
    				  f.set(t, getJavaDoubleArray(mws.getField(f.getName(), i+1)));
    			   }
    			}
    			res.add(t);
    		}
    		return res;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	return null;
     }
     
     public static String[] getJavaStringArray(Object obj){
     	MWCellArray mwss=(MWCellArray)obj;
     	String[] res=new String[mwss.numberOfElements()];
     	for(int i=1;i<=mwss.numberOfElements();i++)
     	{
     	   res[i-1]=mwss.getCell(i).toString();	
     	}
     	return res;
     }
     public static double[] getJavaDoubleArray(Object obj){
     	MWArray mwa=(MWArray)obj;
     	double[] res=new double[mwa.numberOfElements()];
     	for(int i=1;i<=mwa.numberOfElements();i++)
     	{
     		res[i-1]=(Double)mwa.get(i);
     	}
     	return res;
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
         double[] a=new double[4];
    	 System.out.println(a.getClass()==double[].class);
     }
     
}
