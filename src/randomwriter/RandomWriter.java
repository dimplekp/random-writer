package randomwriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class RandomWriter {
	public static void main(String[]args) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Input file? ");
		String filename = input.nextLine();
		
		File f = new File(filename);
		if(f.isFile() && f.canRead()) {
			try {
				System.out.println("Value of N? ");
				int ngramValue;
				if((ngramValue = input.nextInt()) < 2) {
					System.err.println("N must be 2 or greater.");
				}
				
				List<String> wordList = new ArrayList<String>();
				
				// fetch words from file and store them in wordList
				wordList = fetchWords(f);
				
				HashMap<String[], List<String>> hash = new HashMap<String[], List<String>>();
				// make key-value pairs with keys of n-gram words
				// and values of words succeeding words in respective key
				hash = formHash(wordList, ngramValue);
				
				System.out.println("# of random words to generate (0 to quit)? ");
				double storyWordCount = input.nextInt();
				
				if(storyWordCount == 0) {
					System.out.println("Exiting.");
				}
				else {
					makeStory(hash, ngramValue, storyWordCount);
				}
				
			} catch (Exception e) {
				System.err.println("Didn't work.");
			}
		}
		else {
			System.err.println("Unable to open that file. Try again.");
		}
	}

	private static void makeStory(HashMap<String[], List<String>> hash,
			int ngramValue, double storyWordCount) {
		// pick a random key from hash
		// IMP: algorithm needs to be improved so that it doesn't loop through the whole hash
		int rand = 0 + (int)(Math.random() * hash.size());
		double count = 0;
		String[] key = new String[ngramValue];
		for (Entry<String[], List<String>> entry : hash.entrySet()) {
			if(count == rand) {
				key = entry.getKey();
				//List<String> value = entry.getValue();
			}
			  count++;
		}
		
		// start the story with 3 dots to make it look like it's a part of a story 
		System.out.print(" ... ");
		double currentWordCount = 0;
		while(currentWordCount < storyWordCount) {
			for(int i = 0; i < ngramValue; i++) {
				if(currentWordCount < storyWordCount) {
					System.out.print(key[i] + " ");
				}
				else {
					break;
				}
				currentWordCount++;
			}
			
			// get next word sequence from keys of hash based on 
			//the last word used, which was a word from value of previous key
			List<String> nextWordList = hash.get(key);
			String nextWord = nextWordList.get(0);
			hash.get(key).remove(0);
			
			// if the current key doesn't have words in values,
			// remove it, so that it doesn't get searched again
			if(hash.get(key).size() == 0) {
				hash.remove(key);
			}
			
			for(Entry<String[], List<String>> entry : hash.entrySet()) {
				String[] hashKey = entry.getKey();
				if(hashKey[0].equals(nextWord)) {
					key = hashKey;
				}
			}
		}
		System.out.print(" ... ");
	}

	// form hash from the words in file such that the hash has N-gram words as key values
	// and value of that key as words following the sequence of words in the respective key
	private static HashMap<String[], List<String>> formHash(List<String> wordList, int ngramValue) {
		HashMap<String[], List<String>> hash = new HashMap<String[], List<String>>();
		int indexNum = 0;
		boolean isKeyFound = false;
		
		for(int i = 0; (i + ngramValue) < wordList.size(); i++) {
			
			List<String> value = new ArrayList<String>();
			String[] key = new String[ngramValue];
			indexNum = i;
			// make a String array for key with ngramValue number of words
			for(int j = 0; j < ngramValue; j++) {
				key[j] = wordList.get(indexNum);
				indexNum++;
			}
			
			// check if the string array already exist as a key in hash
			Iterator<?> iter = hash.entrySet().iterator();
			while(iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				String[] keyInHash = (String[])pairs.getKey();
				boolean res;
				if(res = Arrays.equals(key, keyInHash)) {
					isKeyFound = hash.get(keyInHash).add(wordList.get(indexNum));
				}
			}
			if(isKeyFound == false) {
				// add the succeeding word in value list
				value.add(wordList.get(indexNum));
				hash.put(key, value);
			}
			isKeyFound = false;
		}
		return hash;
	}

	private static List<String> fetchWords(File f) throws FileNotFoundException, IOException {
		String line;
		List<String> wordList = new ArrayList<String>();
		try (
				InputStream fileInputStream = new FileInputStream(f.getName());
				InputStreamReader inputStreamREader = new InputStreamReader(fileInputStream);
				BufferedReader br = new BufferedReader(inputStreamREader);
		) {
			while((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for(int i = 0; i < words.length; i++) {
					wordList.add(words[i]);
				}
			}
		}
		return wordList;
	}
}