
import java.io.*;
import java.util.*;

public class Main {
	
	static HashMap<String,Integer> hygieneReviews = new HashMap<String,Integer>();
	static HashMap<String,Integer> allReviews = new HashMap<String,Integer>();
	
	public static void main(String[] args){
		int count = 0;
		int hcount= 0;
		try {
			File dataDir = new File("output");
			File[] listOfFiles = dataDir.listFiles();
			for(int i=0; i<listOfFiles.length; i++){
				System.out.print(i+"\t");
				FileReader fr = new FileReader(listOfFiles[i]);
		        BufferedReader br = new BufferedReader(fr);
				String eachLine = br.readLine();
				while( eachLine != null){
					count++;
					String[] lineArray = eachLine.split("\t");
					String key = lineArray[0];
					String[] valueArray = lineArray[1].split("::::");
					int review = Integer.parseInt(valueArray[0]);
					int type = Integer.parseInt(valueArray[1]);

					if(type == 1) {
						hygieneReviews.put(key,review);
						hcount++;
					}
					allReviews.put(key,review);
				
					eachLine = br.readLine();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		System.out.println("\n====================================================================");
		System.out.println("\nall Count : "+count);
		System.out.println("hygiene Count : "+hcount);
		System.out.println("====================================================================");
		System.out.println("Before All Reviews Size : "+allReviews.size());
		System.out.println("Before Hygiene Reviews Size : "+hygieneReviews.size());
		try {
			FileOutputStream fos1 = new FileOutputStream("allReviews.out");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			oos1.writeObject(allReviews);
			oos1.close();
			fos1.close();

			FileOutputStream fos2 = new FileOutputStream("hygieneReviews.out");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			oos2.writeObject(hygieneReviews);
			oos2.close();
			fos2.close();

			System.out.println("====================================================================");

			FileInputStream fis1 = new FileInputStream("allReviews.out");
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			HashMap<String, Integer> ar = (HashMap<String,Integer>) ois1.readObject();
			System.out.println("After All Reviews Size : "+ar.size());
			ois1.close();
			fis1.close();

			FileInputStream fis2 = new FileInputStream("hygieneReviews.out");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			HashMap<String, Integer> hr = (HashMap<String,Integer>) ois2.readObject();
			System.out.println("After Hygiene Reviews Size : "+hr.size());
			ois2.close();
			fis2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
