package epitopeParse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class COBEparse {
    
	private File file;
	private BufferedReader br;
	private int maxSize;
	private int[] score;
	private String[] aa;
	private String pattern;
	private Pattern regx;
	private static String[] array1s;
	private static int[] array2s;
	
	public COBEparse (String fName) {
		file = new File(fName);
		br = null;
		maxSize = 1500;
		aa = new String[maxSize];
		score = new int[maxSize];
		pattern = "(^\\w)";
		regx = Pattern.compile(pattern);
	}

	public Object[] COBEparser() {
		
		//Read the sequences
		try {
			br = new BufferedReader(new FileReader(file));
			String inline = null;
			br.readLine(); // Skip 1st line - header
			
			while((inline = br.readLine()) !=null){		
				String indat = inline.substring(7);
				Matcher matchregx = regx.matcher(indat);
				
				// Look for line containing amino acids - see regex (pattern)
				if(matchregx.find()){
					
					// Loop along length of line
					for(int i=0; i < indat.length(); i++){
						
						// Extract amino acid sequence
						aa[i] = indat.substring(i, i+1);
					}
					
				} else {
					// Loop along length of line
					for(int i=0; i < indat.length(); i++){
						
						// Count plusses or minuses to produce score
						if(indat.substring(i, i+1).equals("+")){
							score[i]++;
						}
						if(indat.substring(i, i+1).equals("-")){
							score[i]--;
						}
					}
				}
			}
		}
		
		catch (IOException e) {
            e.printStackTrace();
		} 
		
		finally {
			try {
				if (br != null) {
					br.close();
					}
				}
			catch (IOException e) {
				e.printStackTrace();
				}
		}
		
		return new Object[]{aa, score};
    }
	
	public static void main (String[] args){
		Object[] arrayObjects = new COBEparse("C:/Users/Kate/Dropbox/COBEProResults/TcdA_630_Cterm_COBEpro.txt").COBEparser();
		array1s = (String[]) arrayObjects[0];
		array2s = (int[])arrayObjects[1];
		
		for(int i=0;i<576;i++){
			System.out.println(array1s[i] + "\t" + array2s[i]);
		}
	}
	
}


