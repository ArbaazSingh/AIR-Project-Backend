package XMLParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLParser {
	public static void main(String[] args) {
		try {
			File inputFile = new File("C:/Users/Arbaaz/Desktop/Diabetes.docx.out.xml");
			Map<String, Integer> wordCounter = new HashMap<String,Integer>();
			SAXBuilder saxBuilder = new SAXBuilder();

			Document document = saxBuilder.build(inputFile);

			Element rootelement = document.getRootElement();
			Element textdata = rootelement.getChild("AnnotationSet");
			
			
			StringBuffer sb = new StringBuffer();			
			List<Element> annotationlist = textdata.getChildren("Annotation");
			sb.append(rootelement.getChildText("TextWithNodes"));
			List<TaggerObject> tagList = new ArrayList<TaggerObject>();
			//System.exit(-1);
			while(true){
				int i = sb.indexOf(".");
				if(i == -1)
					break;
				else{
					TaggerObject obj = new TaggerObject();
					obj.setTag("stops");
					obj.setWord(".");
					obj.setStartNode(i);
					tagList.add(obj);				
					sb.setCharAt(i, ' ');				
				}
			}

			for (int temp = 0; temp <annotationlist.size(); temp++) {	
				TaggerObject tag = new TaggerObject();
				Element singleannotation = annotationlist.get(temp);
				
				if(!singleannotation.getAttributeValue("Type").equals("Token") 
						&& !singleannotation.getAttributeValue("Type").equals("SpaceToken")
						&& !singleannotation.getAttributeValue("Type").equals("Sentence")){
				
				Element Feature =singleannotation.getChild("Feature");
				/*tag.setStartNode(singleannotation.getAttribute("StartNode").getIntValue());
				tag.setEndNode(singleannotation.getAttribute("EndNode").getIntValue());*/
				int startIndex = singleannotation.getAttribute("StartNode").getIntValue();
				int endIndex = singleannotation.getAttribute("EndNode").getIntValue();
				tag.setStartNode(startIndex);
				
				//try{
				
				tag.setTag(Feature.getChildText("Value"));
				/*}
				catch(Exception e){
					
					System.out.println(e.getMessage());
				}*/
				String word = sb.substring(startIndex,endIndex);
				if(tag.getTag().equals("numbers")){
					tag.setWord(word.replace("-", ""));
				}else{
					if(tag.getTag().equals("nutrients") && word.endsWith("s")){
						word = word.substring(0, word.length()-1);
					}
					tag.setWord(word.toLowerCase());
				}
				if(wordCounter.containsKey(word)){
					wordCounter.put(word, wordCounter.get(word)+1 );
				}else{
					wordCounter.put(word, 1 );
				}
				tagList.add(tag);
				
				}
			}
			Collections.sort(tagList, new Comparator<TaggerObject>() {

				@Override
				public int compare(TaggerObject o1, TaggerObject o2) {
					if(o1.getStartNode() < o2.getStartNode()){						
						return -1;
					}else if(o1.getStartNode() > o2.getStartNode()){
						return 1;
					}else
						return 0;
				}
			});

			//printing tagged keywords as statements
			/*for (TaggerObject taggerObject : tagList) {
				System.out.print(taggerObject.getWord() + " ");
				if (taggerObject.getTag().equalsIgnoreCase("stops")) {
					System.out.println();
				}
			}*/
			//System.out.println(wordCounter.toString());
			Integer max = 0;
			String disease = new String();
			for (Map.Entry<String, Integer> entry : wordCounter.entrySet()) {
				String key = entry.getKey();
				Integer val = entry.getValue();
				if(max<val){
					disease = key;
					max = val;
				}			
			}
			//System.out.println("Disease : " + disease);
			OntologyGenerator og = new OntologyGenerator();
			og.generateOntology(tagList,disease);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}