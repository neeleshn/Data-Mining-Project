package wordleGenerator;/* Authored by Kushagra on 4/24/2016. */

import feature_parsers.FeatureInstance;
import model.ReviewSet;
import model.ViolationEntry;
import org.json.simple.parser.ParseException;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static feature_parsers.ReviewParser.buildReviewSetMap;
import static feature_parsers.TargetVariableParser.getMap_TargetVariables;
import static wordleGenerator.TopKWordFeatureList.getTextFeatureListMap;
import static wordleGenerator.TopKWordFeatureList.getTopKWords;


@SuppressWarnings("ConstantConditions")
public class WordleRegressionModel {

    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Building Instance Map");
        Map<Integer, FeatureInstance> instanceMap = FeatureInstance.getMap_Instances();
        System.out.println("Building Review Set Mapper");
        Map<Integer, ReviewSet> reviewSetMapper = buildReviewSetMap(instanceMap);
        Map<Integer, ViolationEntry> targetVariable = getMap_TargetVariables();

        String[] topKWords = getTopKWords();
        System.out.println("Building Feature List Mapper");
        Map<Integer, Map<String, Integer>> featureListMap = getTextFeatureListMap(reviewSetMapper);        // TODO

        System.out.println("Building Feature List Mapper.. complete");

        /* ============================================== */

        Attribute[] topwordFeatures = getTopWordsAttributes(topKWords);
        ArrayList<Attribute> attrList = buildAttributeList(topwordFeatures);

        Instances trainData = new Instances("prediction", attrList, 0);
        int cIdx = trainData.numAttributes() - 1;
        trainData.setClassIndex(cIdx);

        Instances testData = new Instances("prediction", attrList, 0);
        cIdx = testData.numAttributes() - 1;
        testData.setClassIndex(cIdx);

        System.out.println("Creating Instances.. ");
        int index = 1;
        for (int serialID : reviewSetMapper.keySet()) {
            System.out.println(index++ + " / " + reviewSetMapper.size());
            // Feature Vector
            Map<String, Integer> wordFeatures = featureListMap.get(serialID);

            // Target Score
            double Y = ViolationEntry.calculateSimplePenaltyScore(targetVariable.get(serialID));

            // Instances Building, for word features... x
            Instance inst = buildDenseInstanceEntry(wordFeatures, topKWords, attrList);

            // Instances Building, for word features... y
            // add Y into --inst--
            inst.setValue(topwordFeatures[topwordFeatures.length - 1], Y);

            // Adding instances to testdata
            testData.add(inst);
        }


        // clean memory
        System.out.println("Cleaning Heap Memory");
        instanceMap.clear();
        reviewSetMapper.clear();
        targetVariable.clear();
        featureListMap.clear();

        // end of memory clean


        System.out.println("Linear Regression.. ");
        LinearRegression lrModel = new LinearRegression();
        try {

            lrModel.setRidge(3450);
            System.out.println("Linear Regression.. Building Classifier");
            lrModel.buildClassifier(testData);
            System.out.println("Linear Regression.. Classifier Ready");

            double[] topWordCoeff = lrModel.coefficients();
            postProcessWords(topWordCoeff, topKWords);
            System.out.println(Arrays.toString(topWordCoeff));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: ...
    private static void postProcessWords(double[] topWordCoeff, String[] topKWords) {

    }

    private static Instance buildDenseInstanceEntry(Map<String, Integer> map, String[] topKWords,
                                                    ArrayList<Attribute> attrList) {

        int featuresCount = topKWords.length + 1;
        Instance inst = new DenseInstance(featuresCount);

        int wordIndex = 0;
        for (String topword : topKWords) {

            double wordCount = map.get(topword);

            inst.setValue(attrList.get(wordIndex), wordCount);
            wordIndex++;
        }

        return inst;
    }


    private static ArrayList<Attribute> buildAttributeList(Attribute[] topwordFeatures) {

        ArrayList<Attribute> attrList = new ArrayList<>();

        Collections.addAll(attrList, topwordFeatures);

        return attrList;
    }

    private static Attribute[] getTopWordsAttributes(String[] topKWords) {

        Attribute[] topwordFeatures = new Attribute[topKWords.length + 1];

        int wordIndex = 0;
        for (String topword : topKWords) {
            topwordFeatures[wordIndex++] = new Attribute(topword);
        }

        topwordFeatures[wordIndex] = new Attribute("featureTarget");
        return topwordFeatures;
    }
}
