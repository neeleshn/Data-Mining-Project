package linearregression;

import feature_parsers.FeatureParser;

import org.json.simple.parser.ParseException;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class RegressionDemo {

    public static void main(String[] args) throws IOException, ParseException {
    	//TextAnalyzer textAnalyser = new TextAnalyzer();
        FeatureParser featureParser = new FeatureParser();
        
        
        Attribute featureAverageLength_attr = new Attribute("featureAverageLength");
		Attribute featureAverageRating_attr = new Attribute("featureAverageRating");
		Attribute featureAverageReviewCount_attr = new Attribute("featureAverageReviewCount");
		Attribute featurePenaltyScore_attr = new Attribute("featurePenaltyScore");
		Attribute featureReviewResponse_attr = new Attribute("featureReviewResponse");
		Attribute featureIsAlcoholic_attr = new Attribute("featureIsAlcoholic");
		Attribute featureisWaiterService_attr = new Attribute("featureisWaiterService");
		Attribute featureisValetParking_attr = new Attribute("featureisValetParking");
		Attribute featureisRomantic_attr = new Attribute("featureisRomantic");
		Attribute featureisIntimate_attr = new Attribute("featureisIntimate");
		Attribute featureisTouristy_attr = new Attribute("featureisTouristy");
		Attribute featureisHipster_attr = new Attribute("featureisHipster");
		Attribute featureisUpscale_attr = new Attribute("featureisUpscale");
		Attribute featureisDeliveryAvailable_attr = new Attribute("featureisDeliveryAvailable");
		Attribute featureisGoodForDessert_attr = new Attribute("featureisGoodForDessert");
		Attribute featureisLatenight_attr = new Attribute("featureisLatenight");
		Attribute featurePriceRange_attr = new Attribute("featurePriceRange");
		Attribute featureIsBusinessStars_attr = new Attribute("featureIsBusinessStars");
		Attribute featureNoiseLevel_attr = new Attribute("featureNoiseLevel");
		Attribute featureCuisineType_attr = new Attribute("featureCuisineType");
		Attribute sentiments_attr = new Attribute("sentiments");
		Attribute feature_attr = new Attribute("featureTarget");
		
		
		ArrayList<Attribute> attrList = new ArrayList<Attribute>();
		attrList.add(featureAverageLength_attr);
		attrList.add(featureAverageRating_attr);
		attrList.add(featureAverageReviewCount_attr);
		attrList.add(featurePenaltyScore_attr);
		attrList.add(featureReviewResponse_attr);
		attrList.add(featureIsAlcoholic_attr);
		attrList.add(featureisWaiterService_attr);
		attrList.add(featureisValetParking_attr);
		attrList.add(featureisRomantic_attr);
		attrList.add(featureisIntimate_attr);
		attrList.add(featureisTouristy_attr);
		attrList.add(featureisHipster_attr);
		attrList.add(featureisUpscale_attr);
		attrList.add(featureisDeliveryAvailable_attr);
		attrList.add(featureisGoodForDessert_attr);
		attrList.add(featureisLatenight_attr);
		attrList.add(featurePriceRange_attr);
		attrList.add(featureIsBusinessStars_attr);
		attrList.add(featureNoiseLevel_attr);
		attrList.add(featureCuisineType_attr);
		attrList.add(sentiments_attr);
		attrList.add(feature_attr);
		
		
		Instances trainData = new Instances("train", attrList, 0);
		int cIdx = trainData.numAttributes() - 1;
		trainData.setClassIndex(cIdx);
		
		Instances testData = new Instances("test", attrList, 0);
		cIdx = testData.numAttributes() - 1;
		testData.setClassIndex(cIdx);
	
        Set<Integer> instances = featureParser.getInstances();

        for (int serialID : instances) {
        	
            // Target Variables Y
            double Y = featureParser.getTargetVariable(serialID);
            
            
            // Predictors X -- Review.json
            double x1 = featureParser.getFeatureAverageLength(serialID);
            double x2 = featureParser.getFeatureAverageRating(serialID);
            double x3 = featureParser.getFeatureAverageReviewCount(serialID);
            double x4 = featureParser.getFeaturePenaltyScore(serialID);
            double x5 = featureParser.getFeatureReviewResponse(serialID);

            // Predictors X -- Business.json
            double x6 = featureParser.getFeatureIsAlcoholic(serialID);
            double x7 = featureParser.getFeatureisWaiterService(serialID);
            double x8 = featureParser.getFeatureisValetParking(serialID);
            double x9 = featureParser.getFeatureisRomantic(serialID);
            double x10 = featureParser.getFeatureisIntimate(serialID);
            double x11 = featureParser.getFeatureisTouristy(serialID);
            double x12 = featureParser.getFeatureisHipster(serialID);
            double x13 = featureParser.getFeatureisUpscale(serialID);
            double x14 = featureParser.getFeatureisDeliveryAvailable(serialID);
            double x15 = featureParser.getFeatureisGoodForDessert(serialID);
            double x16 = featureParser.getFeatureisLatenight(serialID);
            double x17 = featureParser.getEnumFeaturePriceRange(serialID);
            double x18 = featureParser.getFeatureIsBusinessStars(serialID);
            double x19 = featureParser.getEnumFeaturebusinessType(serialID);
            double x20 = featureParser.getEnumFeatureNoiseLevel(serialID);
            double x21 = featureParser.getEnumFeatureCusineType(serialID);
            double x22 = featureParser.getTextAnalysisScore(serialID);
            
            Instance inst;
			inst = new DenseInstance(22);
            
            inst.setValue(featureAverageLength_attr, x1);
            inst.setValue(featureAverageRating_attr, x2);
            inst.setValue(featureAverageReviewCount_attr, x3);
            inst.setValue(featurePenaltyScore_attr, x4);
            inst.setValue(featureReviewResponse_attr, x5);
            inst.setValue(featureIsAlcoholic_attr, x6);
            inst.setValue(featureisWaiterService_attr, x7);
            inst.setValue(featureisValetParking_attr, x8);
            inst.setValue(featureisRomantic_attr, x9);
            inst.setValue(featureisIntimate_attr, x10);
            inst.setValue(featureisTouristy_attr, x11);
            inst.setValue(featureisHipster_attr, x12);
            inst.setValue(featureisUpscale_attr, x13);
            inst.setValue(featureisDeliveryAvailable_attr, x14);
            inst.setValue(featureisGoodForDessert_attr, x15);
            inst.setValue(featureisLatenight_attr, x16);
            inst.setValue(featurePriceRange_attr, x17);
            inst.setValue(featurePriceRange_attr, x18);
            inst.setValue(featurePriceRange_attr, x19);
            inst.setValue(featureNoiseLevel_attr, x20);
            inst.setValue(featureCuisineType_attr, x21);
            inst.setValue(sentiments_attr, x22);
            inst.setValue(feature_attr, Y);
            
            testData.add(inst);
        }
        
        LinearRegression lrModel = new LinearRegression();
        
        try {
        	  lrModel.setRidge(3450);
			  lrModel.buildClassifier(testData);
			  
			  Evaluation eval = new Evaluation(testData);

			  eval.evaluateModel(lrModel, testData);
			  System.out.println("Summary measure: "+eval.toSummaryString());
			  System.out.println("Recall measure: "+eval.recall(testData.classIndex()));
			} catch (Exception e) {
			
		}
        
    }
}
