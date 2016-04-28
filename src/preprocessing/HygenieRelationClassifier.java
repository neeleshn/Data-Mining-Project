package preprocessing;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import parser.ClassifierReviewParser;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class HygenieRelationClassifier {

	public static void main(String[] args) {
		
		hygenieRelatedClassfier();
		}

	public static void hygenieRelatedClassfier(){
		
		HashMap<String, Double> hygieneRelatedMap = new HashMap<String, Double>();
		
		ArrayList<String> hygieneRelated = new ArrayList<String>();
		hygieneRelated.add("1");
		hygieneRelated.add("0");
        
		Attribute reviewAttr = new Attribute("featureReviewText", (ArrayList<String>) null);
		Attribute classAttr = new Attribute("featureClassAttribute", hygieneRelated);
		
		ArrayList<Attribute> attrList = new ArrayList<Attribute>();
		attrList.add(reviewAttr);
		attrList.add(classAttr);
		
		
		Instances trainData = new Instances("hygieneRelatedClassifier", attrList, 1);
		int cIdx = trainData.numAttributes() - 1;
		trainData.setClassIndex(cIdx);
		
		Instances testData = new Instances("hygieneRelatedClassifier", attrList, 1);
		cIdx = testData.numAttributes() - 1;
		testData.setClassIndex(cIdx);
		
		
		Map<String,String> reviewMap = ClassifierReviewParser.buildReviewSetMap("data/train_review.json");
		System.out.println("reviewMap size: "+reviewMap.size());
		
		for(String reviewID : reviewMap.keySet()){
			Instance inst;
			inst = new DenseInstance(2);
			String reviewData = reviewMap.get(reviewID);
			String[] reviewSplit = reviewData.split("\t");
			String reviewText = reviewSplit[0];
			String related = reviewSplit[1];
			
			inst.setValue(reviewAttr,reviewAttr.addStringValue(reviewText));
			inst.setValue(classAttr,related);
			
			trainData.add(inst);
			
			hygieneRelatedMap.put(reviewID, Double.parseDouble(related));
		}
		
		StringToWordVector m_Filter = new StringToWordVector();
		NaiveBayes nb = new NaiveBayes();
		try{
			 m_Filter.setInputFormat(trainData);
			 Instances filteredData  = Filter.useFilter(trainData, m_Filter);
			 
			 nb.buildClassifier(filteredData);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		Map<String,String> reviewTestMap = ClassifierReviewParser.buildTestReviewSetMap("data/test_review.json");
		int count = 0;
		for(String reviewID : reviewTestMap.keySet()){
			count++;
			Instance inst;
			inst = new DenseInstance(2);
			String reviewData = reviewTestMap.get(reviewID);
			String reviewText = reviewData;
			inst.setValue(reviewAttr,reviewAttr.addStringValue(reviewText));
			testData.add(inst);
			try{
			Instances filteredTestData = Filter.useFilter(testData, m_Filter);
			Instance lastInstance = filteredTestData.remove(filteredTestData.size()-1);
			testData.remove(testData.size()-1);
			double predictedClass;
			try {
				predictedClass = nb.classifyInstance(lastInstance);
				hygieneRelatedMap.put(reviewID, predictedClass);
				System.out.println(count+"--Review Id: "+reviewID+"---predicted class "+predictedClass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		
		try {
			FileOutputStream fos = new FileOutputStream("data/hygieneRelated.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(hygieneRelatedMap);
			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
