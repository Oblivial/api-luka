package de.luka.api.nummern;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Nummer {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String NumberInput;

	private String NumberOutput = "";
	
	public String getNumberOutput() {
		return NumberOutput;
	}
	
	public String getNumberInput() {
		return NumberInput;
	}
	
	public void setNumberInput(String numberinput) {
		this.NumberInput = numberinput;
		setNumberOutput(numberinput);
	}
	
	public void setNumberOutput(String numberinput) {
		ArrayList<Integer> splitLocation = new ArrayList<Integer>();
		int counter = 0;
		char[] cary = numberinput.toCharArray();
		int index = 0;
		int diff = 0;

		int matches = 0;
		int subIndex = 0;
		int endIndex = 0;

		for(char c : cary){  
			
			if (index>0) {
				subIndex = splitLocation.get(index-1);
				diff = counter - subIndex;
			} else {
				diff = counter;
			}
			endIndex =  subIndex + 5;
			if(endIndex>numberinput.length()) {
				endIndex = numberinput.length();
			}
			
			matches = new StringTokenizer(numberinput.substring(subIndex, endIndex), "0").countTokens()-1;
			if((matches < 3 && diff >= 2 && diff < 4 && (cary.length>counter+1) && !(c=='0')) || (diff > 3)) {
				splitLocation.add(counter);
				this.NumberOutput = this.NumberOutput.concat(" ");
				index++;
			}
			counter++;
			this.NumberOutput = this.NumberOutput.concat(String.valueOf(c));
		}
	}



}