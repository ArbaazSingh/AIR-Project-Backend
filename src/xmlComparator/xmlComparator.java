package xmlComparator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class xmlComparator {
	public static void main(String[] args) {
		try {
			File inputFile1 = new File("manualDiabetes.xml");
			File inputFile2 = new File("diabetes.xml");
			
 
			SAXBuilder saxBuilder = new SAXBuilder();

			Document document1 = saxBuilder.build(inputFile1);
			Document document2 = saxBuilder.build(inputFile2);
			

			Element ManElement = document1.getRootElement();
			Element AutoElement= document2.getRootElement();
			Element needsNutrientofManual = ManElement.getChild("needsNutrient");
			Element needsNutrientofAuto = AutoElement.getChild("needsNutrient");
			

			List<Element> ManList = needsNutrientofManual.getChildren("Nutrient");
			List<Element> AutoList = needsNutrientofAuto.getChildren("Nutrient");

			List<String> manualStringList = new ArrayList<String>();
			List<String> autoStringList = new ArrayList<String>();
			
			
			for (Element element : AutoList) {
				autoStringList.add(element.getChildText("name"));
			}
			for (Element element : ManList) {
				manualStringList.add(element.getChildText("name"));
			}
			
			System.out.println("ManList : " + manualStringList.toString());
			System.out.println("AutoList : " + autoStringList.toString());
			
			float needMatchCount = 0;
			for(int i=0;i<manualStringList.size();i++){
				for(int j=0;j<autoStringList.size();j++){
					if(manualStringList.get(i).equalsIgnoreCase(autoStringList.get(j))){
						needMatchCount++;
					}
				}
			}
			float totalManualTagged = manualStringList.size();
			
			
			
			Element excessNutrientofManual = ManElement.getChild("excessNutrient");
			Element excessNutrientofAuto = AutoElement.getChild("excessNutrient");

			List<Element> excessManList = excessNutrientofManual.getChildren("Nutrient");
			List<Element> excessAutoList = excessNutrientofAuto.getChildren("Nutrient");

			List<String> excessManualStringList = new ArrayList<String>();
			List<String> excessAutoStringList = new ArrayList<String>();
			

			
			for (Element element : excessAutoList) {
				excessAutoStringList.add(element.getChildText("name"));
			}
			for (Element element : excessManList) {
				excessManualStringList.add(element.getChildText("name"));
			}	
			float excessMatchCount = 0;
			for(int i=0;i<excessManualStringList.size();i++){
				for(int j=0;j<excessAutoStringList.size();j++){
					if(excessManualStringList.get(i).equalsIgnoreCase(excessAutoStringList.get(j))){
						excessMatchCount++;
					}
				}
			}
			float totalExcessManualTagged = excessManualStringList.size();
			
			List<String> totalManualList = new ArrayList<String>();
			totalManualList.addAll(manualStringList);
			totalManualList.addAll(excessManualStringList);
			
			List<String> totalAutoList = new ArrayList<String>();
			totalAutoList.addAll(autoStringList);
			totalAutoList.addAll(excessAutoStringList);
			float totalManualCount = totalManualList.size();
			
			float totalMatchCount=0;
			for(int i=0;i<totalManualList.size();i++){
				for(int j=0;j<totalAutoList.size();j++){
					if(totalManualList.get(i).equalsIgnoreCase(totalAutoList.get(j))){
						totalMatchCount++;
					}
				}
			}		
			System.out.println("totalManualList : " + totalManualList);
			System.out.println("totalAutoList : " + totalAutoList);
			System.out.println("Recall (i.e entities tagged in Need Nutrient and Excess Nutrient): " + totalMatchCount/totalManualCount);
			
			System.out.println("Precision for Need Nutrient : " + needMatchCount/totalManualTagged);
			System.out.println("Precision for Excess Nutrient : " + excessMatchCount/totalExcessManualTagged);
			
			
			
			
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}