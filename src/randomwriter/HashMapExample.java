package randomwriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashMapExample {
	public static void main(String[]args) {
		HashMap<String[], List<String>> hmList = new HashMap<String[], List<String>>();
		List<String> values = new ArrayList<String>();
		values.add("Hi");
		values.add("Hello");
		hmList.put(new String[]{"Dimple", "Dhatri"}, values);
		List<String> values1 = new ArrayList<String>();
		values1.add("Sadhu");
		values1.add("Thug Life");
		hmList.put(new String[]{"Bhargavi", "Shiralee"}, values1);
		List<String> values2 = new ArrayList<String>();
		values2.add("Funny");
		values2.add("Hilarious");
		hmList.put(new String[]{"Kajal", "Karishma"}, values2);
		List<String> values3 = new ArrayList<String>();
		values3.add("Sweet but talks a lot");
		values3.add("Nice");
		hmList.put(new String[]{"Shraddha", "Dipali"}, Collections.unmodifiableList(Arrays.asList("Pizza partner", "Nice")));
		List<String> values4 = new ArrayList<String>();
		values4.add("Woody Allen");
		values4.add("Dentist");
		hmList.put(new String[]{"Disha", "Asma"}, Collections.unmodifiableList(Arrays.asList("Woody Allen", "Dentist")));

		Iterator iter = hmList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry pairs = (Map.Entry)iter.next();
			String[] key = (String[])pairs.getKey();
			String[] obj = {"Dimple", "Dhatri"};
			boolean res;
			if(res = Arrays.equals(obj, key)) {
				hmList.get(key).add("new value");
			}
			System.out.println(hmList.get(key));
		}
	}
}