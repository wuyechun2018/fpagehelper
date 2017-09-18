package com.wuyechun.itool.fpagehelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
         //String folderPath="D:\\test";
    	 //String folderPath="D:\\abc";
         String folderPath = System.getProperty("user.dir");
         System.out.println("当前路径："+folderPath);
		 File file = new File(folderPath);
		
		 int totalPageCount=0;
		
		 List<String> list=new ArrayList<String>();
		 List<String> result= ergodic(new File(folderPath),list);
		 for (int i = 0; i < result.size(); i++) {
			String filePath=result.get(i);
			if(filePath.contains("docx")||filePath.contains("doc")){
				 int singlePageCount=getWordPageCount(filePath);
				 System.out.println("文件["+i+":"+ filePath+"]页数:"+singlePageCount);
				 totalPageCount=totalPageCount+singlePageCount;
			 }
		}
        System.out.println("总页数:"+totalPageCount);
        
    }
    
    
    
    public static List<String> ergodic(File file,List<String> resultFileName){
        File[] files = file.listFiles();
        if(files==null)return resultFileName;
        for (File f : files) {
            if(f.isDirectory()){
                resultFileName.add(f.getPath());
                ergodic(f,resultFileName);
            }else
                resultFileName.add(f.getPath());
        }
        return resultFileName;
    }
    
    
   
    
    
    
    
    
    /**
     * 
     * 获取Word文件页数
     * @author: wyc
     * @createTime: 2017年9月14日 上午10:44:57
     * @history:
     * @param filePath
     * @return int
     */
    public static int getWordPageCount(String filePath){
    	int page=0;
		try {
			FileInputStream docx = new FileInputStream(filePath);
			if(filePath.contains("docx")){
				
				XWPFDocument xwpfDoc = new XWPFDocument(docx);
				page = xwpfDoc.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();
			}else{
				WordExtractor extractor = new WordExtractor(docx);  
				page=extractor.getSummaryInformation().getPageCount();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERROR:"+filePath);
		}
		return page;
    }
}
