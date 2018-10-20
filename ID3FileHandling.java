package ID3Algo;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.*;

public class ID3FileHandling {
	
	String fileNameMonks;
	String fileNameTesting;
	String fileNameLearning;
	int i;
	int k;
	
	public ID3FileHandling(String fileNameLearning, String fileNameTesting, String fileNameMonks, int k, int i)
	{
		this.fileNameLearning = fileNameLearning;
		this.fileNameTesting = fileNameTesting;
		this.fileNameMonks = fileNameMonks;
		this.k = k;
		this.i = i;
	}
	public void fileShuffle(String fileTrim)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(fileTrim));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
			
			ArrayList<String> s = new ArrayList<String>();
			
			while((line=br.readLine())!=null)
			{
			        s.add(line);	
				linenumber++;
			}
			shuffleList(s);
			for(int i = 0;i<s.size();i++)
			{
				sb.append((String)s.get(i)+"\n");
			}
			
			
			br.close();
 
			FileWriter fw=new FileWriter(new File(fileTrim));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
		    
	}
	
		  public static void shuffleList(ArrayList<String> a) {
		    int n = a.size();
		    Random random = new Random();
		    random.nextInt();
		    for (int i = 0; i < n; i++) {
		      int change = i + random.nextInt(n - i);
		      swap(a, i, change);
		    }
		  }

		  private static void swap(ArrayList a, int i, int change) {
		    String helper = (String)a.get(i);
		    a.set(i, (String)a.get(change));
		    a.set(change, helper);
		  }
	public void fileCopy(String fileNamedest, String fileNamesrc)
	{
	      BufferedReader br = null;
	      PrintWriter pw = null; 

	      try {
	          br = new BufferedReader(new FileReader( fileNamesrc ));
	    	  pw =  new PrintWriter(new FileWriter( fileNamedest ));

	          String line;
	          while ((line = br.readLine()) != null) {
	              pw.println(line);
	          }

	          br.close();
	          pw.close();
	      }catch (Exception e) {
		  e.printStackTrace();
	      }
	}
	public void fileTrim(String fileTrim)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(fileTrim));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
			
			String s1="";
			
			while((line=br.readLine())!=null)
			{
			        s1=line.substring(0,14);
					sb.append(s1+"\n");
					
				linenumber++;
			}
			
			br.close();
 
			FileWriter fw=new FileWriter(new File(fileTrim));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
		    
	}
	
	public void fileShift(String fileTrim)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(fileTrim));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
			
			String s1="";
			String s2="";
			int length=0;
			String s3;
			 
		    Scanner in = new Scanner(System.in);
		    System.out.println("Enter the string in this format:");
		    s3 = in.nextLine();
			sb.append(s3+"\n");
			while((line=br.readLine())!=null)
			{
					length=line.length();
				    s1=line.substring(0,2);
			        s2=line.substring(2,length);
					sb.append(s2+s1+"\n");
					linenumber++;
			}
			
			br.close();
 
			FileWriter fw=new FileWriter(new File(fileTrim));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
		    
	}
	void deleteinner(String filename, int startline, int numlines)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(filename));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
			
			line=br.readLine();
			sb.append(line+"\n");
 
			while((line=br.readLine())!=null)
			{
				//Store each valid line in the string buffer
				if(linenumber<startline||linenumber>=startline+numlines)
					sb.append(line+"\n");
				linenumber++;
			}
			if(startline+numlines>linenumber)
				System.out.println("End of file reached.");
			br.close();
 
			FileWriter fw=new FileWriter(new File(filename));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
	}
	void deleteouter(String filename, int startline, int numlines)
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(filename));
 
			//String buffer to store contents of the file
			StringBuffer sb=new StringBuffer("");
 
			//Keep track of the line number
			int linenumber=1;
			String line;
			
			line=br.readLine();
			sb.append(line+"\n");
 
			while((line=br.readLine())!=null)
			{
				//Store each valid line in the string buffer
				if(linenumber>=startline&&linenumber<startline+numlines)
					sb.append(line+"\n");
				linenumber++;
			}
			if(startline+numlines>linenumber)
				System.out.println("End of file reached.");
			br.close();
 
			FileWriter fw=new FileWriter(new File(filename));
			//Write entire string buffer into the file
			fw.write(sb.toString());
			fw.close();
		}
		catch (Exception e)
		{
			System.out.println("Something went horribly wrong: "+e.getMessage());
		}
	}
}



