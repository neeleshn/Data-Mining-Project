package feature_parsers;

import model.AllViolationData;
import model.BusinessSet;
import model.ReviewSet;
import model.ViolationEntry;

import org.json.simple.parser.ParseException;

import parser.AllViolationParser;
import utils.FeatureNormalizer;
import utils.TextAnalyzer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UnnecessaryLocalVariable")
public class FeatureParser {

    // Preprocessors
    List<AllViolationData> entries;
    Map<Integer, FeatureInstance> instanceMap;
    Map<String, BusinessSet> businessSetMapper;

    // Predictors X
    Map<Integer, ReviewSet> reviewSetMapper;                // -- generated from AllViolations.csv
    Map<Integer, Double> map_serialID_pastViolation;        // -- generated from Review.json
    Map<Integer, BusinessSet> businessInstanceMapper;       // -- generated from Business.json

    // Target Variable Y
    Map<Integer, Double> mapTargetVariable;
    Map<Integer, ViolationEntry> mapTargetVariables;


    // Class private locators
    private Map<Integer, Double> averageLength;
    private Map<Integer, Double> averageRating;
    private Map<Integer, Double> averageReviewCount;
    private Map<Integer, Double> averageReviewResponse;
    private Map<Integer, Double> averagePenaltyScore;

    private Map<Integer, Double> isAlcoholic;
    private Map<Integer, Double> isWaiterService;
    private Map<Integer, Double> isValetParking;
    private Map<Integer, Double> isRomantic;
    private Map<Integer, Double> isIntimate;
    private Map<Integer, Double> isTouristy;
    private Map<Integer, Double> isHipster;
    private Map<Integer, Double> isUpscale;
    private Map<Integer, Double> isDeliveryAvailable;
    private Map<Integer, Double> isGoodForDessert;
    private Map<Integer, Double> isLatenight;

    private Map<Integer, Double> priceRange;
    private Map<Integer, Double> stars;
    private Map<Integer, Double> cuisine;
    
    private Map<Integer, Double> businessType;
    private Map<Integer, Double> noiseLevel;

    private Map<Integer, Double> textAnalysisScore;
    
    /* TODO:
     * add zipcode as feature
     * 
     */

    private Map<Integer, Double> targetVariable;
    
    private Map<Integer, ViolationEntry> targetVariables;


    public FeatureParser() throws IOException, ParseException {

    	System.out.println("Hello from feature parser constructor");
        buildParser();

        // -- Review.json
        averageLength = populateFeatureMap1();
        averageRating = populateFeatureMap2();
        averageReviewCount = populateFeatureMap3();
        averageReviewResponse = populateFeatureMap4();
        averagePenaltyScore = populateFeatureMap5();

        // -- Business.json
        isAlcoholic = populateFeatureMap6();
        isWaiterService = populateFeatureMap7();
        isValetParking = populateFeatureMap8();
        isRomantic = populateFeatureMap9();
        isIntimate = populateFeatureMap10();
        isTouristy = populateFeatureMap11();
        isHipster = populateFeatureMap12();
        isUpscale = populateFeatureMap13();
        isDeliveryAvailable = populateFeatureMap14();
        isGoodForDessert = populateFeatureMap15();
        isLatenight = populateFeatureMap16();

        priceRange = populateFeatureMap17();
        stars = populateFeatureMap18();

        businessType = populateFeatureMap19();
        noiseLevel = populateFeatureMap20();
          

        cuisine = populateFeatureMap22();
        targetVariable = populateTargetVariable();
        targetVariables = populateTargetVariables();
        textAnalysisScore = populateFeatureMap21();  // Text Analysis Score, pulled frm ReviewParser
  
    }
    
    
      private Map<Integer, Double> populateFeatureMap22() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeEnumForCuisine(businessInstanceMapper.get(sampleID).getCuisineType());

            map.put(key, value);
        }

        return map;
    }
     

    private Map<Integer, Double> populateFeatureMap21() {
        Map<Integer, Double> map = new HashMap<Integer, Double>();

        for (int sampleID : reviewSetMapper.keySet()) {

            int key = sampleID;
            double value = reviewSetMapper.get(sampleID).getTextAnalysisScore();

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap20() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeEnumForNoiseLevel(businessInstanceMapper.get(sampleID).getNoiseLevel());

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap19() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeEnumForBusinessType(businessInstanceMapper.get(sampleID).getBusinessType());

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap18() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeInteger(businessInstanceMapper.get(sampleID).getStars());

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap17() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeEnumForPriceRange(businessInstanceMapper.get(sampleID).getPriceRange());

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap16() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeBoolean(businessInstanceMapper.get(sampleID).isLatenight());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap15() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isGoodForDessert());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap14() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeBoolean(businessInstanceMapper.get(sampleID).isDeliveryAvailable());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap13() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isUpscale());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap12() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeBoolean(businessInstanceMapper.get(sampleID).isHipster());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap11() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeBoolean(businessInstanceMapper.get(sampleID).isTouristy());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap10() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isIntimate());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap9() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isRomantic());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap8() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isValetParking());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap7() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeGoodFeatures(businessInstanceMapper.get(sampleID).isWaiterService());

            map.put(key, value);
        }

        return map;
    }


    private Map<Integer, Double> populateFeatureMap6() {
        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : businessInstanceMapper.keySet()) {

            int key = sampleID;
            double value = FeatureNormalizer.normalizeBoolean(businessInstanceMapper.get(sampleID).isAlcoholic());

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateTargetVariable() {

        Map<Integer, Double> map = new HashMap<>();

        for (int serialID : mapTargetVariable.keySet()) {

            int key = serialID;
            Double value = mapTargetVariable.get(serialID);

            map.put(key, value);
        }

        return map;
    }
    
    
    private Map<Integer, ViolationEntry> populateTargetVariables() {

        Map<Integer, ViolationEntry> map = new HashMap<>();

        for (int serialID : mapTargetVariables.keySet()) {

            int key = serialID;
            ViolationEntry value = mapTargetVariables.get(serialID);

            map.put(key, value);
        }

        return map;
    }

    public Set<Integer> getInstances() {
        return this.averageLength.keySet();
    }

    public double getTargetVariable(int serialID) {
        return targetVariable.get(serialID);
    }
    
    public ViolationEntry getTargetVariables(int serialID) {
        return targetVariables.get(serialID);
    }

    // Review.json getters
    public double getFeatureAverageLength(int serialID) {
        return averageLength.get(serialID);
    }

    public double getFeatureAverageRating(int serialID) {
        return averageRating.get(serialID);
    }

    public double getFeatureAverageReviewCount(int serialID) {
        return averageReviewCount.get(serialID);
    }

    public double getFeatureReviewResponse(int serialID) {
        return averageReviewResponse.get(serialID);
    }


    // Business.json getters
    public double getFeatureIsAlcoholic(int serialID) {
        return isAlcoholic.get(serialID);
    }

    public double getFeatureisWaiterService(int serialID) {
        return isWaiterService.get(serialID);
    }

    public double getFeatureisValetParking(int serialID) {
        return isValetParking.get(serialID);
    }

    public double getFeatureisRomantic(int serialID) {
        return isRomantic.get(serialID);
    }

    public double getFeatureisTouristy(int serialID) {
        return isTouristy.get(serialID);
    }

    public double getFeatureisIntimate(int serialID) {
        return isIntimate.get(serialID);
    }


    public double getFeatureisHipster(int serialID) {
        return isHipster.get(serialID);
    }

    public double getFeatureisUpscale(int serialID) {
        return isUpscale.get(serialID);
    }

    public double getFeatureisDeliveryAvailable(int serialID) {
        return isDeliveryAvailable.get(serialID);
    }

    public double getFeatureisGoodForDessert(int serialID) {
        return isGoodForDessert.get(serialID);
    }

    public double getFeatureisLatenight(int serialID) {
        return isLatenight.get(serialID);
    }

    public double getFeatureIsBusinessStars(int serialID) {
        return stars.get(serialID);
    }

    public double getEnumFeaturePriceRange(int serialID) {
        return priceRange.get(serialID);
    }

    public double getEnumFeaturebusinessType(int serialID) {
        return businessType.get(serialID);
    }

    public double getEnumFeatureNoiseLevel(int serialID) {
        return noiseLevel.get(serialID);
    }


    public double getFeaturePenaltyScore(int serialID) {
        return averagePenaltyScore.get(serialID);
    }
    
    public double getEnumFeatureCusineType(int serialID) {
        return cuisine.get(serialID);
    }


    private Map<Integer, Double> populateFeatureMap1() {

        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : reviewSetMapper.keySet()) {

            int key = sampleID;
          
            double value = reviewSetMapper.get(sampleID).getAverageLength();
           
            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap2() {

        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : reviewSetMapper.keySet()) {

            int key = sampleID;
            double value = reviewSetMapper.get(sampleID).getAverageRating();

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap3() {

        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : reviewSetMapper.keySet()) {

            int key = sampleID;
            double value = reviewSetMapper.get(sampleID).getReviewCount();

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap4() {

        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : reviewSetMapper.keySet()) {

            int key = sampleID;
            double value = reviewSetMapper.get(sampleID).getReviewResponse();

            map.put(key, value);
        }

        return map;
    }

    private Map<Integer, Double> populateFeatureMap5() {

        Map<Integer, Double> map = new HashMap<>();

        for (int sampleID : map_serialID_pastViolation.keySet()) {

            int key = sampleID;
            double value = map_serialID_pastViolation.get(sampleID);

            map.put(key, value);
        }

        return map;
    }


    private void buildParser() throws IOException, ParseException {

    	System.out.println("Hello from buildParser in Feature Parser");
        // Preprocessors
        this.entries = AllViolationParser.readViolationData();
        this.instanceMap = FeatureInstance.getMap_Instances();

        // Predictors X -- Review.json
        this.reviewSetMapper = ReviewParser.buildReviewSetMap(instanceMap);
        this.map_serialID_pastViolation = PastPenaltyParser.buildPastViolationsMap(entries);

        // Predictors X -- Business.json
        businessSetMapper = BusinessParser.buildBusinessSetMap(instanceMap);
        businessInstanceMapper = BusinessParser.buildBusinessInstanceMap(instanceMap, businessSetMapper);

        // Target Variable Y
        this.mapTargetVariable = TargetVariableParser.getMap_TargetVariable(1, 1, 1);
        this.mapTargetVariables = TargetVariableParser.getMap_TargetVariables();
    }

    public void setAverageLength(Map<Integer, Double> averageLength) {
        this.averageLength = averageLength;
    }

    public void setAverageRating(Map<Integer, Double> averageRating) {
        this.averageRating = averageRating;
    }

    public void setAverageReviewCount(Map<Integer, Double> averageReviewCount) {
        this.averageReviewCount = averageReviewCount;
    }

    public void setAverageReviewResponse(Map<Integer, Double> averageReviewResponse) {
        this.averageReviewResponse = averageReviewResponse;
    }

    public void setAveragePenaltyScore(Map<Integer, Double> averagePenaltyScore) {
        this.averagePenaltyScore = averagePenaltyScore;
    }

    public Map<Integer, Double> getIsAlcoholic() {
        return isAlcoholic;
    }

    public void setIsAlcoholic(Map<Integer, Double> isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    public Map<Integer, Double> getIsWaiterService() {
        return isWaiterService;
    }

    public void setIsWaiterService(Map<Integer, Double> isWaiterService) {
        this.isWaiterService = isWaiterService;
    }

    public Map<Integer, Double> getIsValetParking() {
        return isValetParking;
    }

    public void setIsValetParking(Map<Integer, Double> isValetParking) {
        this.isValetParking = isValetParking;
    }

    public Map<Integer, Double> getIsRomantic() {
        return isRomantic;
    }

    public void setIsRomantic(Map<Integer, Double> isRomantic) {
        this.isRomantic = isRomantic;
    }

    public Map<Integer, Double> getIsIntimate() {
        return isIntimate;
    }

    public void setIsIntimate(Map<Integer, Double> isIntimate) {
        this.isIntimate = isIntimate;
    }

    public Map<Integer, Double> getIsTouristy() {
        return isTouristy;
    }

    public void setIsTouristy(Map<Integer, Double> isTouristy) {
        this.isTouristy = isTouristy;
    }

    public Map<Integer, Double> getIsHipster() {
        return isHipster;
    }

    public void setIsHipster(Map<Integer, Double> isHipster) {
        this.isHipster = isHipster;
    }

    public Map<Integer, Double> getIsUpscale() {
        return isUpscale;
    }

    public void setIsUpscale(Map<Integer, Double> isUpscale) {
        this.isUpscale = isUpscale;
    }

    public Map<Integer, Double> getIsDeliveryAvailable() {
        return isDeliveryAvailable;
    }

    public void setIsDeliveryAvailable(Map<Integer, Double> isDeliveryAvailable) {
        this.isDeliveryAvailable = isDeliveryAvailable;
    }

    public Map<Integer, Double> getIsGoodForDessert() {
        return isGoodForDessert;
    }

    public void setIsGoodForDessert(Map<Integer, Double> isGoodForDessert) {
        this.isGoodForDessert = isGoodForDessert;
    }

    public Map<Integer, Double> getIsLatenight() {
        return isLatenight;
    }

    public void setIsLatenight(Map<Integer, Double> isLatenight) {
        this.isLatenight = isLatenight;
    }

    public Map<Integer, Double> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Map<Integer, Double> priceRange) {
        this.priceRange = priceRange;
    }

    public Map<Integer, Double> getStars() {
        return stars;
    }

    public void setStars(Map<Integer, Double> stars) {
        this.stars = stars;
    }

    public Map<Integer, Double> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Map<Integer, Double> businessType) {
        this.businessType = businessType;
    }

    public Map<Integer, Double> getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(Map<Integer, Double> noiseLevel) {
        this.noiseLevel = noiseLevel;
    }
    
    public Map<Integer, Double> getCuisineType() {
        return cuisine;
    }

    public void setCusineType(Map<Integer, Double> cuisine) {
        this.cuisine = cuisine;
    }

    // TODO: faking results, for now
    public double getTextAnalysisScore(Integer serialID) {
    		return textAnalysisScore.get(serialID);
   
    }

    public Map<Integer, Double> getTextAnalysisScore() {
        return textAnalysisScore;
    }

    public void setTextAnalysisScore(Map<Integer, Double> textAnalysisScore) {
        this.textAnalysisScore = textAnalysisScore;
    }
}
