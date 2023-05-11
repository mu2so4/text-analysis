package ru.nsu.ccfit.muratov.tooi.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Text {
	private final ArrayList<String> container = new ArrayList<String>();
	private final long[] lettersFrequency = new long[32];
	private final long[] wordsLengthFrequency = new long[21];
	private final long[] sentencesLengthFrequency = new long[81];
	public Text(String fileName){
		File file = new File(fileName);
		if(file.exists() && file.canRead())
		{
			Scanner in;
			try{
				in = new Scanner(file);
			}
			catch (FileNotFoundException err){
				System.err.println("File not found");
				return;
			}
			
			while (in.hasNextLine()) container.add(in.nextLine());
			in.close();
			setLetters();
			setWords();
			setSentences();
		}
	}
	
	public long[] getLettersFrequency(){return lettersFrequency;}

	public long[] getWordsLengthFrequency(){return wordsLengthFrequency;}

	public long[] getSentencesLengthFrequency(){return sentencesLengthFrequency;}

	private void setLetters(){
		for(String str: container)
			for(char a: str.toCharArray()){
				switch (a){
				case 'А':
				case 'а':
					lettersFrequency[0]++;
					break;
				case 'Б':
				case 'б':
					lettersFrequency[1]++;
					break;
				case 'В':
				case 'в':
					lettersFrequency[2]++;
					break;
				case 'Г':
				case 'г':
					lettersFrequency[3]++;
					break;
				case 'Д':
				case 'д':
					lettersFrequency[4]++;
					break;
				case 'Ё':
				case 'ё':
				case 'Е':
				case 'е':
					lettersFrequency[5]++;
					break;
				case 'Ж':
				case 'ж':
					lettersFrequency[6]++;
					break;
				case 'З':
				case 'з':
					lettersFrequency[7]++;
					break;
				case 'И':
				case 'и':
					lettersFrequency[8]++;
					break;
				case 'Й':
				case 'й':
					lettersFrequency[9]++;
					break;
				case 'К':
				case 'к':
					lettersFrequency[10]++;
					break;
				case 'Л':
				case 'л':
					lettersFrequency[11]++;
					break;
				case 'М':
				case 'м':
					lettersFrequency[12]++;
					break;
				case 'Н':
				case 'н':
					lettersFrequency[13]++;
					break;
				case 'О':
				case 'о':
					lettersFrequency[14]++;
					break;
				case 'П':
				case 'п':
					lettersFrequency[15]++;
					break;
				case 'Р':
				case 'р':
					lettersFrequency[16]++;
					break;
				case 'С':
				case 'с':
					lettersFrequency[17]++;
					break;
				case 'Т':
				case 'т':
					lettersFrequency[18]++;
					break;
				case 'У':
				case 'у':
					lettersFrequency[19]++;
					break;
				case 'Ф':
				case 'ф':
					lettersFrequency[20]++;
					break;
				case 'Х':
				case 'х':
					lettersFrequency[21]++;
					break;
				case 'Ц':
				case 'ц':
					lettersFrequency[22]++;
					break;
				case 'Ч':
				case 'ч':
					lettersFrequency[23]++;
					break;
				case 'Ш':
				case 'ш':
					lettersFrequency[24]++;
					break;
				case 'Щ':
				case 'щ':
					lettersFrequency[25]++;
					break;
				case 'Ъ':
				case 'ъ':
					lettersFrequency[26]++;
					break;
				case 'Ы':
				case 'ы':
					lettersFrequency[27]++;
					break;
				case 'Ь':
				case 'ь':
					lettersFrequency[28]++;
					break;
				case 'Э':
				case 'э':
					lettersFrequency[29]++;
					break;
				case 'Ю':
				case 'ю':
					lettersFrequency[30]++;
					break;
				case 'Я':
				case 'я':
					lettersFrequency[31]++;
					break;
				}
			}
	}

	private void setWords(){
		char chr;
		String[] arr;
		for (String str: container){
			String str1="";
			for(int i=0; i<str.length(); i++){
				chr=str.charAt(i);
				if(
						32==chr ||
						45==chr ||
						1025==chr ||
						(1040<=chr &&
								1103>=chr
						) ||
						1105==chr
					) str1 += chr;
			}
			str="";
			arr = str1.split(" ");
			for(String i: arr){
				if(!(
						i.equals("-й"  ) ||
						i.equals("-ый" ) ||
						i.equals("-ий" ) ||
						i.equals("-ой" ) ||
						i.equals("-ого") ||
						i.equals("-его") ||
						i.equals("-го" ) ||
						i.equals("-ому") ||
						i.equals("-ему") ||
						i.equals("-у"  ) ||
						i.equals("-им" ) ||
						i.equals("-м"  ) ||
						i.equals("-ем" ) ||
						i.equals("-ом" ) ||
						i.equals("-ым" ) ||
						i.equals("-е"  ) ||
						i.equals("-ое" ) ||
						i.equals("-ее" ) ||
						i.equals("-ая" ) ||
						i.equals("-яя" ) ||
						i.equals("-я"  ) ||
						i.equals("-ой" ) ||
						i.equals("-ей" ) ||
						i.equals("-ые" ) ||
						i.equals("-ие" ) ||
						i.equals("-ых" ) ||
						i.equals("-их" ) ||
						i.equals("-ыми") ||
						i.equals("-ими") ||
						i.equals("-и"  ) ||
						i.equals("-их" ) ||
						i.equals("-ых" ) ||
						i.equals("-х"  )
						)) str += i+" ";
			}
			str1="";
			for(int i=0; i<str.length(); i++){
				chr=str.charAt(i);
				if(chr!='-') str1+=chr;
			}
			str="";
			try{
				if(!str1.startsWith(" ")) str+=str1.charAt(0);
			}
			catch (StringIndexOutOfBoundsException e){
				continue;
			}
			for(int i=1; i<str1.length(); i++){
				chr=str1.charAt(i);
				if(' '!=chr ||
						' '!=str1.charAt(i-1)) str+=chr;
			}
			arr=str.split(" ");
			for(String i: arr){
				if(0!=i.length())
					if(i.length() <= 20) wordsLengthFrequency[i.length()-1]++;
					else wordsLengthFrequency[20]++;
			}
		}
	}

	private void setSentences(){
		String str1, arr[];
		int number;
		ArrayList<Integer> num = new ArrayList<Integer>();
		for(String str: container){
			str1="";
			num.clear();
			if(!(
					str.contains("А") ||
					str.contains("Б") ||
					str.contains("В") ||
					str.contains("Г") ||
					str.contains("Д") ||
					str.contains("Е") ||
					str.contains("Ё") ||
					str.contains("Ж") ||
					str.contains("З") ||
					str.contains("И") ||
					str.contains("Й") ||
					str.contains("К") ||
					str.contains("Л") ||
					str.contains("М") ||
					str.contains("Н") ||
					str.contains("О") ||
					str.contains("П") ||
					str.contains("Р") ||
					str.contains("С") ||
					str.contains("Т") ||
					str.contains("У") ||
					str.contains("Ф") ||
					str.contains("Х") ||
					str.contains("Ц") ||
					str.contains("Ч") ||
					str.contains("Ъ") ||
					str.contains("Ы") ||
					str.contains("Ь") ||
					str.contains("Э") ||
					str.contains("Ю") ||
					str.contains("Я") ||
					str.contains("а") ||
					str.contains("б") ||
					str.contains("в") ||
					str.contains("г") ||
					str.contains("д") ||
					str.contains("е") ||
					str.contains("ё") ||
					str.contains("ж") ||
					str.contains("з") ||
					str.contains("и") ||
					str.contains("й") ||
					str.contains("к") ||
					str.contains("л") ||
					str.contains("м") ||
					str.contains("н") ||
					str.contains("о") ||
					str.contains("п") ||
					str.contains("р") ||
					str.contains("с") ||
					str.contains("т") ||
					str.contains("у") ||
					str.contains("ф") ||
					str.contains("х") ||
					str.contains("ц") ||
					str.contains("ч") ||
					str.contains("ъ") ||
					str.contains("ы") ||
					str.contains("ь") ||
					str.contains("э") ||
					str.contains("ю") ||
					str.contains("я"))) continue;
			for(char chr: str.toCharArray()){
				if(!
						(34<=chr && 45>=chr ||
							47<=chr && 62>=chr ||
							64==chr ||
							'—'==chr ||
							91<=chr && 126>=chr ||
							161<=chr && 191>=chr)
					) str1+=chr;
			}
			str="";
			
			try{
				if(!str1.startsWith(" ")) str+=str1.charAt(0);
			}
			catch (IndexOutOfBoundsException e){
				continue;
			}
			
			for(int i=1; i<str1.length(); i++)
				if(' '!=str1.charAt(i) || ' '!=str1.charAt(i-1)) str+=str1.charAt(i);
			arr=str.split(" ");
			for(int i=0; i<arr.length; i++){
				str=arr[i];
				try{
					str1=arr[i+1];
				}
				catch (IndexOutOfBoundsException e){
					if(str.endsWith(".") ||
							str.endsWith("?") ||
							str.endsWith("!") ||
							str.endsWith("…")) num.add(i);
					break;
				}
				number=str1.charAt(0);
				if(
					(str.endsWith(".") ||
						str.endsWith("?") ||
						str.endsWith("!") ||
						str.endsWith("…")) &&
					4<=str.length() &&
					(65<=number && 90>=number ||
						1025==number ||
						1040<=number && 1071>=number)) num.add(i);
			}
			try{
				if(79<num.get(0)) sentencesLengthFrequency[80]++;
				else sentencesLengthFrequency[num.get(0)]++;
			}

			catch (IndexOutOfBoundsException e){
				continue;
			}
			for(int i=1; i<num.size(); i++){
				number=num.get(i)-num.get(i-1);
				if(80<number) sentencesLengthFrequency[80]++;
				else sentencesLengthFrequency[number-1]++;
			}
		}
	}
}