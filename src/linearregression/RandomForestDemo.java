package linearregression;

import feature_parsers.FeatureParser;

import org.json.simple.parser.ParseException;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomForestDemo {

    public static void main(String[] args) throws IOException, ParseException {

        FeatureParser featureParser = new FeatureParser();
        
        ArrayList<String> hygenic = new ArrayList<String>();
        hygenic.add("0");
        hygenic.add("1");
        
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
		Attribute sentiments_attr = new Attribute("sentiments");
		Attribute feature_attr = new Attribute("featureTarget", hygenic);
		
		ArrayList attrList = new ArrayList<Attribute>();
		/*attrList.add(featureAverageLength_attr);
		attrList.add(featureAverageRating_attr);
		attrList.add(featureAverageReviewCount_attr);
		attrList.add(featurePenaltyScore_attr);
		attrList.add(featureReviewResponse_attr);
		attrList.add(featureIsAlcoholic_attr);*/
		attrList.add(featureisWaiterService_attr);
		/*attrList.add(featureisValetParking_attr);
		attrList.add(featureisRomantic_attr);
		attrList.add(featureisIntimate_attr);
		attrList.add(featureisTouristy_attr);
		attrList.add(featureisHipster_attr);
		attrList.add(featureisUpscale_attr);
		attrList.add(featureisDeliveryAvailable_attr);
		attrList.add(featureisGoodForDessert_attr);
		attrList.add(featureisLatenight_attr);
		attrList.add(featurePriceRange_attr);
		attrList.add(featureNoiseLevel_attr);*/
		attrList.add(sentiments_attr);
		attrList.add(feature_attr);
		
		Instances trainData = new Instances("prediction", attrList, 0);
		int cIdx = trainData.numAttributes() - 1;
		trainData.setClassIndex(cIdx);
		
		Instances testData = new Instances("prediction", attrList, 0);
		int cIdx1 = testData.numAttributes() - 1;
		testData.setClassIndex(cIdx1);
	
        Set<Integer> trainInstances = featureParser.getInstances();
        int n = (int) (trainInstances.size() * 0.8);
        System.out.println("N: "+n);
        
        /*List<Integer> list = new ArrayList<>(trainInstances);
        Set<Integer> testInstances = new HashSet(list.subList(n, trainInstances.size()));
        System.out.println("Test Instances size: "+testInstances.size());
        trainInstances.removeAll(testInstances);
        */
        int counter = 0;
        double avgTarget = 0;
        
        for (int serialID : trainInstances) {
        	
            // Target Variables Y
            double Y = featureParser.getTargetVariable(serialID);
            avgTarget+=Y;
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
            double x21 = featureParser.getTextAnalysisScore(serialID); 

            // TODO: More features to add here.
            Instance inst;
			inst = new DenseInstance(3);
            
            /*inst.setValue(featureAverageLength_attr, x1);
            inst.setValue(featureAverageRating_attr, x2);
            inst.setValue(featureAverageReviewCount_attr, x3);
            inst.setValue(featurePenaltyScore_attr, x4);
            inst.setValue(featureReviewResponse_attr, x5);
            inst.setValue(featureIsAlcoholic_attr, x6);*/
            inst.setValue(featureisWaiterService_attr, x7);
            /*inst.setValue(featureisValetParking_attr, x8);
            inst.setValue(featureisRomantic_attr, x9);
            inst.setValue(featureisIntimate_attr, x10);
            inst.setValue(featureisTouristy_attr, x11);
            inst.setValue(featureisHipster_attr, x12);
            inst.setValue(featureisUpscale_attr, x13);
            inst.setValue(featureisDeliveryAvailable_attr, x14);
            inst.setValue(featureisGoodForDessert_attr, x15);
            inst.setValue(featureisLatenight_attr, x16);
            inst.setValue(featurePriceRange_attr, x17);
            inst.setValue(featureNoiseLevel_attr, x20);*/
            inst.setValue(sentiments_attr, x21);
            if(Y >= 8)
            inst.setValue(feature_attr, 1);
            else
            inst.setValue(feature_attr, 0);
            
            trainData.add(inst);
        }

        /*ArrayList<Double> testY = new ArrayList<Double>();
        for (int serialID : testInstances) {
        	
            // Target Variables Y
            double Y = featureParser.getTargetVariable(serialID);
            testY.add(Y);
            // Predictors X -- Review.json
            //double x1 = featureParser.getFeatureAverageLength(serialID);
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
            double x21 = featureParser.getTextAnalysisScore(serialID);      // fake score of 2, for now

            // TODO: More features to add here.
            Instance inst;
			inst = new DenseInstance(19);
            
            inst.setValue(featureAverageLength_attr, 6);
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
            inst.setValue(featureNoiseLevel_attr, x20);
            inst.setValue(sentiments_attr, x21);
            
            testData.add(inst);
        }
*/
        
        
        RandomForest rf = new RandomForest();
        
        try {
        	rf.buildClassifier(trainData);
			int accuracyCounter = 0;
			  
			Evaluation eval = new Evaluation(trainData);
			Random rand = new Random(1);
			eval.crossValidateModel(rf,trainData,5, rand);
			System.out.println("Summary: "+eval.toSummaryString()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
        
  
    }
}
