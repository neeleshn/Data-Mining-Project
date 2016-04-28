package neelux;

import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.csvreader.CsvWriter;
import org.tartarus.snowball.ext.englishStemmer;



public class Main {
	
	public static class CsvMapper  extends Mapper<Object, Text, Text, Text> {
		Text customKey = new Text();
		Text customValue = new Text();
		boolean notInit = true;
		boolean dictionaryNotRead = true;
		static StanfordCoreNLP pipeline;
		public static HashSet<String> hygieneDict = new HashSet<String>();
		public static HashSet<String> hygieneDictionary = new HashSet<String>();
	    
	    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			if(notInit) {
				init();
				notInit=false;
			}

			if(dictionaryNotRead){
				createHygieneDict();
				stemDict();
				dictionaryNotRead=false;
			}

	    	String eachLine = value.toString();
			try {
				JSONParser parser = new JSONParser();
                Object obj = parser.parse(eachLine);
                JSONObject jsonStringObject = (JSONObject) obj;

				String review_id = (String) jsonStringObject.get("review_id");
				String business_id = (String) jsonStringObject.get("business_id");
				String review = (String) jsonStringObject.get("text");
				long stars = (long) jsonStringObject.get("stars");

				customKey.set(business_id);
				String newReview = hygieneComment(review);

				if(!(newReview.equals(""))) {
			        int sentiScore=findSentiment(newReview);
					String customValueStr = review_id+"::::"+sentiScore;
					customValue.set(customValueStr);
					context.write(customKey,customValue);
				} else if(stars >= 4) {
			        int sentiScore=findSentiment(review);
					String customValueStr = review_id+"::::"+sentiScore;
					customValue.set(customValueStr);
					context.write(customKey,customValue);
				}
			} catch (Exception e) {
				System.out.println("error at : "+eachLine);
				e.printStackTrace();
			}
	    }

		public static void init() {
		    Properties props = new Properties();
		    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		    pipeline = new StanfordCoreNLP(props);
		}

		public static void createHygieneDict(){
			hygieneDict.add("mess");
			hygieneDict.add("dirty");
			hygieneDict.add("clean");
			hygieneDict.add("table");
			hygieneDict.add("rat");
			hygieneDict.add("flies");
			hygieneDict.add("raw");
			hygieneDict.add("bathroom");
			hygieneDict.add("hygiene");
			hygieneDict.add("ill");
			hygieneDict.add("cheap");
			hygieneDict.add("floor");
			hygieneDict.add("pleasant");
			hygieneDict.add("hair");
			hygieneDict.add("wash");
			hygieneDict.add("paper");
			hygieneDict.add("kitchen");
			hygieneDict.add("soap");
			hygieneDict.add("gross");
			hygieneDict.add("filthy");
			hygieneDict.add("sanity");
			hygieneDict.add("regimen");
			hygieneDict.add("healthy");
			hygieneDict.add("foul");
			hygieneDict.add("glass");
			hygieneDict.add("fork");
			hygieneDict.add("plate");
			hygieneDict.add("spoon");
			hygieneDict.add("knife");
			hygieneDict.add("chair");
			hygieneDict.add("fresh");
			hygieneDict.add("odor");
			hygieneDict.add("unsanitary");
			hygieneDict.add("gloves");
			hygieneDict.add("greasy");
			hygieneDict.add("stale");
			hygieneDict.add("bathroom");
			hygieneDict.add("wall");
			hygieneDict.add("smoke");
			hygieneDict.add("neat");
			hygieneDict.add("clear");
			hygieneDict.add("stain");
			hygieneDict.add("undercook");
			hygieneDict.add("romance");
			hygieneDict.add("romantic");
			hygieneDict.add("date");
			hygieneDict.add("husband");
			hygieneDict.add("boyfriend");
			hygieneDict.add("beautiful");
			hygieneDict.add("ambiance");
			hygieneDict.add("atmosphere");
		}
		
		public static void stemDict() {
			englishStemmer stemmer = new englishStemmer();
		    for (String line : hygieneDict) {
	            stemmer.setCurrent(line);
	            if (stemmer.stem()) {
	                String stemLine = stemmer.getCurrent();
	                hygieneDictionary.add(stemLine);
	            } else {
	                System.out.println("NOT stemmer stem :- " + line);
	                hygieneDictionary.add(line);
	            }
	        }
	    }
		
		private static String hygieneComment(String reviewText) {
    	
			englishStemmer stemmer = new englishStemmer();
			String[] sentences = reviewText.split("\\. ");
		    String newReview = "";
		    
		    for (String sentence : sentences) {
		        String[] words = sentence.split(" ");
		        for (String eachWord : words) {
		            stemmer.setCurrent(eachWord);
		            if (stemmer.stem()) {
		                String stemWord = stemmer.getCurrent();
		                if (hygieneDictionary.contains(stemWord)) {
		                    newReview += sentence + ". ";
		                    break;
		                }
		            }
		        }
		    }
			return newReview;
		}
		
		public static int findSentiment(String review) {

		    int mainSentiment = 0;
		    if (review != null && review.length() > 0) {
		        int longest = 0;
		        Annotation annotation = pipeline.process(review);

		        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
		            Tree tree = sentence.get(SentimentCoreAnnotations.AnnotatedTree.class);
		            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
		            String partText = sentence.toString();
		            if (partText.length() > longest) {
		                mainSentiment = sentiment;
		                longest = partText.length();
		            }
		        }
		    }
		    return mainSentiment;
		}
	}
	

	public static class CsvReducer extends Reducer<Text,Text,Text,Text> {
		public static HashMap<String,Integer> reviewScore = new HashMap<String,Integer>();		
		static String fileName = "";
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context ) throws IOException, InterruptedException {
			String eachValue="";
			Text customKey = new Text();
			Text customValue = new Text();

			for (Text val : values) {

				eachValue= val.toString();
				String[] valuesArray = eachValue.split("::::");
				reviewScore.put(valuesArray[0],Integer.parseInt(valuesArray[1]));
				
				System.out.println(valuesArray[0]+"\t"+valuesArray[1]);
				customKey.set(valuesArray[0]);
				customValue.set(valuesArray[1]);
				context.write(customKey,customValue);
				fileName=valuesArray[0];
			}
		}
		
		public void cleanup(Context context){
			System.out.println("====================================================================");
			try {
				FileOutputStream fos = new FileOutputStream(fileName+".out");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(reviewScore);
				oos.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "csv");
	    job.setJarByClass(Main.class);
	    job.setMapperClass(CsvMapper.class);
	    job.setReducerClass(CsvReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
