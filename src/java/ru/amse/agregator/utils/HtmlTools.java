package ru.amse.agregator.utils;

public class HtmlTools {
	//This method clears string from tags
	public static String clearString(String incomed){
		
		if(incomed != null && !incomed.isEmpty()){
			int a,b;
			while(incomed.indexOf('<') != (-1) && incomed.indexOf('>') != (-1)){
				a = incomed.indexOf('<');
				b = incomed.indexOf('>', a);
				if( b > a ){
					incomed = incomed.substring(0,a) + incomed.substring(b);
					incomed = incomed.replaceFirst(">", " ");
				}
				else {
					break;
				}
			}	
			incomed = incomed.replaceAll("\n", " ");
			incomed = incomed.replaceAll("\t", " ");
			incomed = incomed.replaceAll(" {2,}", " ");
			incomed = incomed.replaceAll(" {1,}[.]", ".");
			incomed = incomed.replaceAll(" {1,}[,]", ",");
			return incomed.trim();
		}
		else
			return null;
	}
}
