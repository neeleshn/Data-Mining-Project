package linearregression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.json.simple.parser.ParseException;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import feature_parsers.FeatureParser;

public class RandomForestClassifierForHygieneRelated {

	public static void main(String[] args) throws IOException, ParseException {

		FeatureParser featureParser = new FeatureParser();

		ArrayList<String> hygenic = new ArrayList<String>();
		hygenic.add("0");
		hygenic.add("1");

		Attribute sentiments_attr = new Attribute("sentiments");
		Attribute feature_attr = new Attribute("featureTarget", hygenic);

		ArrayList<Attribute> attrList = new ArrayList<Attribute>();

		attrList.add(sentiments_attr);
		attrList.add(feature_attr);

		Instances trainData = new Instances("prediction", attrList, 0);
		int cIdx = trainData.numAttributes() - 1;
		trainData.setClassIndex(cIdx);

		Set<Integer> trainInstances = featureParser.getInstances();
		for (int serialID : trainInstances) {

			double Y = featureParser.getTargetVariable(serialID);
			double x21 = featureParser.getTextAnalysisScore(serialID);
			Instance inst;
			inst = new DenseInstance(2);
			inst.setValue(sentiments_attr, x21);
			if(Y >= 8)
				inst.setValue(feature_attr, 1);
			else
				inst.setValue(feature_attr, 0);

			trainData.add(inst);
		}


		RandomForest rf = new RandomForest();

		try {
			rf.buildClassifier(trainData);
			
			Evaluation eval = new Evaluation(trainData);
			Random rand = new Random(1);
			eval.crossValidateModel(rf,trainData,5, rand);
			System.out.println("Summary: "+eval.toSummaryString());

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
