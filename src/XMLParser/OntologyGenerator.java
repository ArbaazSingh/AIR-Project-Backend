package XMLParser;
import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OntologyGenerator {
	public void generateOntology(List<TaggerObject> tagList, String disease){  


		List<TaggerObject> sentence = new ArrayList<TaggerObject>();
		Map<String, Integer> excessNutrientMap = new HashMap<String, Integer>();
		Map<String, Integer> needNutrientMap = new HashMap<String, Integer>();
		Map<String, List<String>> taggerMap = new HashMap<String, List<String>>();

		for (TaggerObject taggerObject : tagList) {
			//System.out.println(sentence);
			if(!taggerObject.getTag().equals("stops")){	
				//System.out.println("sentence in if : " + sentence);
				sentence.add(taggerObject);
			}else{
				System.out.println(sentence.toString());
				for (int i = 0; i < sentence.size(); i++) {
					String word = sentence.get(i).getWord();
					if(sentence.get(i).getTag().equals("nutrients")){
						//Rule1 : nutrient | excessNutrient | quantity
						if(rule1(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+2).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+2).getWord());
							}
							//System.out.println("tagger mapper rule 1 : " + taggerMap.toString());
							continue;
						}

						//Rule2 : nutrient | excessNutrient | quantity
						if(rule2(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+2).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+2).getWord());
							}
							//System.out.println("tagger mapper rule 2 : " + taggerMap.toString());
							continue;
						}

						//Rule3 : nutrient | quantity | needNutrient
						if(rule3(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+1).getWord());
							}
							//System.out.println("tagger mapper rule 3 : " + taggerMap.toString());
							continue;
						}

						//Rule4 : nutrient | quantity | excessNutrient
						if(rule4(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+1).getWord());
							}
							//System.out.println("tagger mapper rule 4 : " + taggerMap.toString());
							continue;
						}

						//Rule5 : quantity | nutrient | needNutrient
						if(rule5(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-1).getWord());
							}
							//System.out.println("tagger mapper rule 5 : " + taggerMap.toString());
							continue;
						}

						//Rule6 : quantity | nutrient  | excessNutrient
						if(rule6(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-1).getWord());
							}
							//System.out.println("tagger mapper rule 6 : " + taggerMap.toString());
							continue;
						}

						//Rule7 : needNutrient | nutrient  | quantity
						if(rule7(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+1).getWord());
							}
							//System.out.println("tagger mapper rule 7 : " + taggerMap.toString());
							continue;
						}

						//Rule8 : excessNutrient | nutrient  | quantity
						if(rule8(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+1).getWord());
							}
							//System.out.println("tagger mapper rule 8 : " + taggerMap.toString());
							continue;
						}

						//Rule9 : quantity | needNutrient | nutrient  
						if(rule9(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-2).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-2).getWord());
							}
							//System.out.println("tagger mapper rule 9 : " + taggerMap.toString());
							continue;
						}

						//Rule10 : quantity | excessNutrient | nutrient 
						if(rule10(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-2).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-2).getWord());
							}
							//System.out.println("tagger mapper rule 10 : " + taggerMap.toString());
							continue;
						}

						//Rule11 : needNutrition | quantity | nutrient 
						if(rule11(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-1).getWord());
							}
							//System.out.println("tagger mapper rule 11 : " + taggerMap.toString());
							continue;
						}

						//Rule12 : excessNutrient | quantity | nutrient 
						if(rule12(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-1).getWord());
							}
							//System.out.println("tagger mapper rule 12 : " + taggerMap.toString());
							continue;
						}

						//Rule13 : nutrient | needNutrient 
						if(rule13(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}							
							//System.out.println("tagger mapper rule 13 : " + taggerMap.toString());
							continue;
						}

						//Rule14 : nutrient | excessNutrient  
						if(rule14(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}							
							//System.out.println("tagger mapper rule 14 : " + taggerMap.toString());
							continue;
						}

						//Rule15 : needNutrient | nutrient  
						if(rule15(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}							
							//System.out.println("tagger mapper rule 15 : " + taggerMap.toString());
							continue;
						}

						//Rule16 : excessNutrient | nutrient   
						if(rule16(sentence,i)){
							if(needNutrientMap.containsKey(word)){
								needNutrientMap.put(word, needNutrientMap.get(word) + 1);
							}else{
								needNutrientMap.put(word, 1);
							}							
							//System.out.println("tagger mapper rule 16 : " + taggerMap.toString());
							continue;
						}

						//Rule17 : nutrient | quantity   
						if(rule17(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}	
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i+1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i+1).getWord());
							}
							//System.out.println("tagger mapper rule 17 : " + taggerMap.toString());
							continue;
						}

						//Rule18 : quantity | nutrient   
						if(rule18(sentence,i)){
							if(excessNutrientMap.containsKey(word)){
								excessNutrientMap.put(word, excessNutrientMap.get(word) + 1);
							}else{
								excessNutrientMap.put(word, 1);
							}	
							if(!taggerMap.containsKey(word)){
								List<String> list = new ArrayList<String>();
								list.add(sentence.get(i-1).getWord());
								taggerMap.put(word, list);
							}else{
								taggerMap.get(word).add(sentence.get(i-1).getWord());
							}
							//System.out.println("tagger mapper rule 18 : " + taggerMap.toString());
							continue;
						}

					}
				}
				sentence.clear();
			}
		}
		System.out.println("tagger mapper : " + taggerMap.toString());
		System.out.println("excessNutrientMap : " + excessNutrientMap);
		System.out.println("needNutrientMap : " + needNutrientMap);

		Map<String,Integer> excessNutritionXMLMap = new HashMap<String,Integer>();
		Map<String,Integer> needNutritionXMLMap = new HashMap<String,Integer>();
int temp=0;
		for (Map.Entry<String,List<String>> mapObj : taggerMap.entrySet()) {
			String key = mapObj.getKey();
			System.out.println(key);
			List<String> valList = mapObj.getValue();
			int avg = 0;
			int count = 0;
			for (String string : valList) {
				avg += Integer.valueOf(string.replaceAll(",", "").replaceAll(" ", ""));
				count++;
			}
			avg = avg/count;
			
			//System.out.println(excessNutrientMap.containsKey("sodium"));
			//System.out.println(excessNutrientMap.containsKey(key)+" "+ needNutrientMap.containsKey(key));
			if(excessNutrientMap.containsKey(key) && needNutrientMap.containsKey(key)){
				System.out.println(key+ " Both have it");
				//System.out.println((needNutrientMap.get(key).intValue()) +"   "+ (excessNutrientMap.get(key).intValue()));
				if((needNutrientMap.get(key)) > (excessNutrientMap.get(key))){
					System.out.println(key+ " Both have it and needs is winning");
					//System.out.println((needNutrientMap.get(key).intValue()) +" 1 > "+ (excessNutrientMap.get(key).intValue()));
					needNutritionXMLMap.put(key, avg);
				}else if((needNutrientMap.get(key).intValue()) == (excessNutrientMap.get(key).intValue())){
					System.out.println(key+ " Both have it EQUALLY");
					excessNutritionXMLMap.put(key, avg);
				}else{

					System.out.println(key+ " Both have it and Excess is winning");
					excessNutritionXMLMap.put(key, avg);
				}
			}else if(needNutrientMap.containsKey(key)){
				//System.out.println((needNutrientMap.get(key).intValue()) +" 3 only neednutrient  ");
				needNutritionXMLMap.put(key, avg);
			}else if(excessNutrientMap.containsKey(key)){
				//System.out.println((excessNutrientMap.get(key).intValue()) +" 4 only Excessnutrient  ");
				excessNutritionXMLMap.put(key, avg);
			}
			needNutrientMap.remove(key);
			excessNutrientMap.remove(key);
			temp++;
		}
		
		System.out.println(" Loop Terminated after" + temp + " runs ");
		for (Map.Entry<String, Integer> excessMap : excessNutrientMap.entrySet()) {
			System.out.println(excessMap.getKey());
			excessNutritionXMLMap.put(excessMap.getKey(), Integer.MIN_VALUE);
		}
		for (Map.Entry<String, Integer> needMap : needNutrientMap.entrySet()) {
			needNutritionXMLMap.put(needMap.getKey(), Integer.MIN_VALUE);
		}

		System.out.println("excessNutritionXMLMap : " + excessNutritionXMLMap);
		System.out.println("needNutritionXMLMap : " + needNutritionXMLMap);

		Element diseaser = new Element("Disease");  
		diseaser.setAttribute(new Attribute("name", disease));  
		Document document = new Document(diseaser);  
		Element needNutrient = new Element("needsNutrient");
		Element excessNutrient = new Element("excessNutrient");
		Element nutrient=null;
		diseaser.addContent(needNutrient);
		diseaser.addContent(excessNutrient);

		
		for (Map.Entry<String, Integer> nutritionObj : needNutritionXMLMap.entrySet()) {			
			nutrient = new Element("Nutrient");
			nutrient.addContent(new Element("name").setText(nutritionObj.getKey()));
			needNutrient.addContent(nutrient);
			if(!nutritionObj.getValue().equals(Integer.MIN_VALUE)){
				nutrient.addContent(new Element("value").setText(nutritionObj.getValue().toString()));
			}		
		}
		
		
		for (Map.Entry<String, Integer> nutritionObj : excessNutritionXMLMap.entrySet()) {
			nutrient = new Element("Nutrient");
			nutrient.addContent(new Element("name").setText(nutritionObj.getKey()));
			excessNutrient.addContent(nutrient);
			if(!nutritionObj.getValue().equals(Integer.MIN_VALUE)){
				nutrient.addContent(new Element("value").setText(nutritionObj.getValue().toString()));
			}
		}


		try {
			XMLOutputter xmlOutput = new XMLOutputter();  
			xmlOutput.output(document, System.out);  
			xmlOutput.setFormat(Format.getPrettyFormat());  
			xmlOutput.output(document, new FileWriter("generatedXml.xml"));  
		} catch (IOException io) {  
			System.out.println(io.getMessage());  
		}

	}

	private boolean rule1(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("lowerbound") && sentence.get(position+2).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule2(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("upperbound") && sentence.get(position+2).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule3(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("numbers") && sentence.get(position+2).getTag().equals("lowerbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule4(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("numbers") && sentence.get(position+2).getTag().equals("upperbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule5(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("numbers") && sentence.get(position+1).getTag().equals("lowerbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule6(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("numbers") && sentence.get(position+1).getTag().equals("upperbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule7(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("lowerbound") && sentence.get(position+1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule8(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("upperbound") && sentence.get(position+1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule9(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-2).getTag().equals("numbers") && sentence.get(position-1).getTag().equals("lowerbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule10(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-2).getTag().equals("numbers") && sentence.get(position-1).getTag().equals("upperbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule11(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-2).getTag().equals("lowerbound") && sentence.get(position-1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule12(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-2).getTag().equals("upperbound") && sentence.get(position-1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule13(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("lowerbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule14(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("upperbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule15(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("lowerbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule16(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("upperbound")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule17(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position+1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
	private boolean rule18(List<TaggerObject> sentence, int position){
		boolean retVal = false;
		try{
			if(sentence.get(position-1).getTag().equals("numbers")){
				retVal=true;
			}
		}catch(Exception e){
			retVal=false;
		}
		return retVal;
	}
}
