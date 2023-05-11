package ru.nsu.ccfit.muratov.tooi.text;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Experiment {

	public static void main(String[] args) throws IOException
	{
		long start=System.currentTimeMillis(), end;
		Text a;
		try
		{
			a = new Text("D:/Максим/Проект по русскому языку/in.txt");
		}
		catch (Exception err)
		{
			System.err.println("FATAL ERROR");
			end=System.currentTimeMillis();
			System.err.println("Failed in " +((end-start)/1000.0)+" s");
			err.printStackTrace();
			return;
		}
		end=System.currentTimeMillis();
		File letters = new File("D:/Максим/Проект по русскому языку/out/letters.txt"),
				words = new File("D:/Максим/Проект по русскому языку/out/words.txt"),
				sentences = new File("D:/Максим/Проект по русскому языку/out/sentences.txt");
		PrintWriter writer;
		
		long[] arr;
		letters.createNewFile();
		words.createNewFile();
		sentences.createNewFile();
		if(letters.canWrite())
		{
			arr=a.getLettersFrequency();
			writer=new PrintWriter(letters);
			for(long i: arr) writer.println(i);
			writer.close();
			System.out.println("Letters: SUCCESS");
		}
		
		else System.err.println("Letters: FAIL");
		if(words.canWrite())
		{
			arr=a.getWordsLengthFrequency();
			writer=new PrintWriter(words);
			for(long i: arr) writer.println(i);
			writer.close();
			System.out.println("Words: SUCCESS");
		}
		else System.err.println("Words: FAIL");
		
		if(sentences.canWrite())
		{
			arr=a.getSentencesLengthFrequency();
			writer=new PrintWriter(sentences);
			for(long i: arr) writer.println(i);
			writer.close();
			System.out.println("Sentences: SUCCESS");
		}
		else System.err.println("Sentences: FAIL");
		System.out.println("Finished in " +((end-start)/1000.0)+" s");
	}
}
