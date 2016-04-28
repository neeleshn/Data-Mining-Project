package model;

public class BusinessSet {

    private String yelpID;

    private boolean isAlcoholic;
    private boolean isWaiterService;
    private boolean isValetParking;
    private boolean isRomantic;
    private boolean isIntimate;
    private boolean isTouristy;
    private boolean isHipster;
    private boolean isUpscale;
    private boolean isDeliveryAvailable;
    private boolean isGoodForDessert;
    private boolean isLatenight;

    private int priceRange;
    private int stars;
    
    private String businessType;
    private String noiseLevel;
    private String category;

    public BusinessSet() {
        // Empty constructor
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public boolean isWaiterService() {
        return isWaiterService;
    }

    public void setWaiterService(boolean waiterService) {
        isWaiterService = waiterService;
    }

    public boolean isValetParking() {
        return isValetParking;
    }

    public void setValetParking(boolean valetParking) {
        isValetParking = valetParking;
    }

    public boolean isRomantic() {
        return isRomantic;
    }

    public void setRomantic(boolean romantic) {
        isRomantic = romantic;
    }

    public boolean isIntimate() {
        return isIntimate;
    }

    public void setIntimate(boolean intimate) {
        isIntimate = intimate;
    }

    public boolean isTouristy() {
        return isTouristy;
    }

    public boolean isHipster() {
        return isHipster;
    }

    public void setHipster(boolean hipster) {
        isHipster = hipster;
    }

    public boolean isUpscale() {
        return isUpscale;
    }

    public void setUpscale(boolean upscale) {
        isUpscale = upscale;
    }

    public void setTouristy(boolean touristy) {
        isTouristy = touristy;
    }

    public boolean isDeliveryAvailable() {
        return isDeliveryAvailable;
    }

    public void setDeliveryAvailable(boolean deliveryAvailable) {
        isDeliveryAvailable = deliveryAvailable;
    }

    public boolean isGoodForDessert() {
        return isGoodForDessert;
    }

    public void setGoodForDessert(boolean goodForDessert) {
        isGoodForDessert = goodForDessert;
    }

    public boolean isLatenight() {
        return isLatenight;
    }

    public void setLatenight(boolean latenight) {
        isLatenight = latenight;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getNoiseLevel() {
        return noiseLevel;
    }

    public void setNoiseLevel(String noiseLevel) {
        this.noiseLevel = noiseLevel;
    }
    
    public String getCuisineType() {
        	return category;
    	}

    public void setCuisineType(String category) {
        this.category = category;
    }

    public String getYelpID() {
        return yelpID;
    }

    public void setYelpID(String yelpID) {
        this.yelpID = yelpID;
    }
}
