package feature_parsers;

import java.util.HashSet;

public class CategoryMap {
	HashSet<String> map = new HashSet<String>();
	
	public CategoryMap(){
		map.add("Restaurant");
		map.add("Chinese");
		map.add("Indian");
		map.add("Maxican");
		map.add("Italian");
		map.add("Bakeries");
		map.add("Delis");
		map.add("Fast Food");
		map.add("Burgers");
		map.add("Sandwiches");
		map.add("Seafood");
		map.add("Caribbean");
		map.add("Breakfast & Brunch");
		map.add("Cafes");
		map.add("Coffee & Tea");
		map.add("Pizza");
		map.add("Thai");
		map.add("Mediterranean");
		map.add("Falafel");
		map.add("Middle Eastern");
		map.add("Sushi Bars");
		map.add("Ice Cream & Frozen Yogurt");
		map.add("American (New)");
		map.add("American (Traditional)");
		map.add("Cuban");
		map.add("African");
		map.add("Moroccan");
		map.add("Bars");
		map.add("Pubs");
		map.add("Gay Bars");
		map.add("Nightlife");
		
	}
	public HashSet<String> getMap(){
		return this.map;
	}
}
