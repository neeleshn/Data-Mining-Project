
Goal: Predict Hygiene of a restaurant from reviews on Social Media.

Dataset:
 - Violations by all Restaurants from Boston Public Health Department.
 - Yelp Reviews.

Result: 78% accuracy is predicting if a restaurant is hygienic or unhygienic.

Data:
The data is available on the following link: https://drive.google.com/open?id=0B_LGUXrleYirYkJRc29pLTg0OTQ
Create a data folder in the project outside src folder and unzip the data from the link and add it to the data folder.

allReview.out: The output from the Hadoop program which is a Map which relates each yelp reviewId with a sentiment score
AllViolations.csv: The data provided by the city of Boston for the past violations provided for restaurents.
restaurent_ids_to_yelp_ids.csv: maps yelp business id to the restuarent id for the city of Boston
train_review.json: Manually labelled data from yelp_academic_dataset_review.json, Used this data to train the Naive bayes text classifier.
test_review.json: The remaining data from yelp_academic_dataset_review.json used to predict the hygiene relation in a review. Used as input to the Naive bayes text classifier.
yelp_academic_dataset_review.json: The data provided by yelp for all the reviews.
yelp_academic_dataset_business.json: The data provided by yelp about businesses.


Hadoop Program:
1. Software required : Gradle
2. Build jar file by running "gradle build" on terminal in the directory "hadoop".
3. Put yelp_academic_dataset_review.json in S3. Run the jar on AWS EMR.
4. The output of this program is downloaded in hadoop/postprocessing/output.
5. run Main.java in hadoop/postprocessing to get allReviews.out which needs to be placed in data directory to execution of rest of the program.


Project Build:
1. Open the source code in an IDE.
2. Configure the program to a Maven Configuration
3. The pom.xml is provided with the source code which should automatically build the dependencies into the project.
4. run "mvn clean install" in terminal for building the project.


Running the code: 

--correlation package
1. WekaPearsonCorealtion.java: Run the program directly to find the pearson corelation for features.
2. SpearmanCorelation.java: Run the program directly to find the spearmann corelation for features.

-- linearregression
1. Programs which  consider only text as a feature, run each of them directly
	- J48ClassifierHygieneRelated.java
	- NaiveBayesHygieneRelatedClassfier.java
	- RandomForestClassifierForHygieneRelated.java

2. Programs which consider features along with text and from business.json
	- J48FeatureDemo.java
	- NaiveBayesClassifier.java
	- RandonForestDemo.java

3. Run RegressionDemo.java to see the results of Ridge Regression.
