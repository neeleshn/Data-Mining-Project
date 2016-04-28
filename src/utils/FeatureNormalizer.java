package utils;

public class FeatureNormalizer {
   public static double normalizeBoolean(boolean booleanFeature) {
    	if(booleanFeature)
    		return 1;
    	else
    		return 0;
    }
   
   public static double normalizeGoodFeatures(boolean isGoodFeature) {
   	if(isGoodFeature)
   		return 0;
   	else
   		return 1;
   }
   
   public static double normalizeEnumForCuisine(String category) {
       switch(category){
       	case "Delis":
       	case "Sandwiches":
       	case "Burgers":
       	case "Fast Food":
       		return 1;
       	case "Chinese": 
       		return 2;
       	case "Indian" : 
       		return 3;
       	case "Maxican":
       		return 4;
       	case "Italian": 
       		return 4;
       	case "Bakeries": 
       		return 5;
       	case "Seafood": 
       		return 6;
       	case "Breakfast & Brunch":
       	case "Cafes":
       	case "Coffee & Tea":
       		return 7;
       	case "Bars":
       	case "Nightlife": 
       	case "Pubs": 
       	case "Gay Bars":  
       		return 8;
       	case "Pizza": 
       		return 9;
       	case "Thai": 
       		return 10;
       	case "Mediterranean":
       	case "Falafel":
       	case "Middle Eastern":
       		return 11;
       	case "Ice Cream & Frozen Yogurt": 
       		return 12;
       	case "Sushi Bars": 
       		return 13;
       	case "Caribbean": 
       		return 14;
       	case "American (New)":
       	case "American (Traditional)":
       		return 15;
       	case "African": 
       	case "Moroccan": 
       		return 16;	
       	case "Cuban": 
       		return 17;	
       	default: 
       		return 0;
       }
   }


    public static double normalizeEnumForNoiseLevel(String noiseLevel) {
        switch(noiseLevel){
        	case "quiet": return 1;
        	case "average": return 2;
        	case "loud" : return 3;
        	case "very_loud": return 4;
        	default: return 0;
        }
    }

    public static double normalizeEnumForBusinessType(String businessType) {
        return 0;
    }

    public static double normalizeInteger(int stars) {
        return stars;
    }

    public static double normalizeEnumForPriceRange(int priceRange) {
    	switch(priceRange){
    	case 1: return 1;
    	case 2: return 2;
    	case 3: return 3;
    	case 4: return 4;
    	case 5: return 5;
    	default: return 0;
    	}
    }
}
