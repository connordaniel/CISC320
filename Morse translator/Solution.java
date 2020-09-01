import java.util.Scanner;
import java.lang.String;
import java.io.*;
import java.util.*;
//Collaborated with Cohort 4 for this project
public class Solution {
	 class TrieNode {
		HashMap<String, TrieNode> children;
		ArrayList<String> words;

		public TrieNode() {
			children = new HashMap<>();
			words = new ArrayList<>();
		}
	

		public void addWord(TrieNode root, String in ) {
			TrieNode curr = root;
			String combine = in.replaceAll(" ", "");
			String [] split = combine.split("");
			
			for (String i : split) {
				if (!(curr.children.containsKey(i)))
					curr.children.put(i, new TrieNode());
				curr = curr.children.get(i);
			}
			curr.words.add(handleSpacedLetters(in));
		}

		public ArrayList<String> findWord(TrieNode root, String target) {
			TrieNode curr = root;
			String [] split = target.split("");
			for (String i : split) {
				if (curr.children.containsKey(i))
					curr = curr.children.get(i);
			}
			return curr.words;
		}
	 }
	
	static HashMap<String, String> hash = new HashMap<>();
	static HashMap<String, String> revHash = new HashMap<>();
	static ArrayList<String> dict = new ArrayList<>();
	static ArrayList<String> morseDict = new ArrayList<>();

    public static String handleSpacedLetters(String morsed) {   
    	String [] work = morsed.split(" ");
    	String out = "";
    	for (String i : work) {
    		out = out + (hash.get(i));
    	}
    	//check if output is a word, if not it doesn't print
    	int index = Collections.binarySearch(dict, out);
    	if (index >= 0)
        	return out;
    	return "";
    }
    
    public static ArrayList<String> handleWord(String morsed, String word, int start, int end, ArrayList<String> output) {
    	  boolean outOfBounds = start + end > morsed.length(); //keeps track if you've reached the end of the morse string
          
          if(dict.contains(word) && !output.contains(word) && realLength(revHash, morsed, word)) { //adds word to output list if it is in the dictionary and not already added
      		output.add(word);
      	}
          
      	if(!outOfBounds) {
      		String code = morsed.substring(start, start + end);
      		
      		if(hash.containsKey(code)) {
      			String updatedWord = word + hash.get(code);
      			
      			handleWord(morsed, updatedWord, start + end, 1, output);
      			handleWord(morsed, word, start, end + 1, output);
      		}
      	}
      	
      	return output;
    }
    
    public static boolean realLength(HashMap<String, String> revHash, String morsed, String word) {
    	String[] splitWord = word.split("");
    	word = "";
    	for(String s : splitWord) {
    		word = word + revHash.get(s);
    	}
    	
    	return morsed.length() == word.length();
    }
    
    public static void handleSpacedWords(String morsed, TrieNode root) {
    	for(String d : morseDict) 
    		root.addWord(root, d);
    	
    	
    	ArrayList<ArrayList<String>> list = new ArrayList<>();
    	String [] split = morsed.split(" ");
    	
    	for (String i : split) 
    		list.add(root.findWord(root, i));
    	ArrayList<String> output = generatePermutations(list, new ArrayList<String>(), 0, "");
    	for (String i : output) {
    		i = i.replaceFirst(" ", "");
    		System.out.println(i);
    	}
    	
    }

    
    public static ArrayList<String> generatePermutations(ArrayList<ArrayList<String>> lists, ArrayList<String> result, int depth, String current) {
        if (depth == lists.size()) {
            result.add(current);
            return result;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            generatePermutations(lists, result, depth + 1, current + " " + lists.get(depth).get(i));
        }
        
        return result;
    }
    
    public static ArrayList<String> handleSentence(String morsed, TrieNode root) {
    	//puts all the words from the dictionary in the trie
    	for(String d : morseDict) {
    		root.addWord(root, d);
    	}
    	
    	ArrayList<String> sentences = findAllWords(morsed, root, new ArrayList<>());
    	ArrayList<String> sentence = new ArrayList<>();
    	ArrayList<String> sentenceList = new ArrayList<>();
    	
    	for(String s : sentences) {
    		if(s.equals("")) {
    			sentenceList.addAll(sentence);
    			sentence.clear();
    		}
    		else {
    			sentence.add(s);
    		}
    	}
    	
    	//System.out.println(sentenceList);
    	
    	//sort
    	int minLen = Integer.MAX_VALUE;
    	int temp = 0;
    	for (String i : sentenceList) {
    		if (i.equals(" ")) {
    			if (temp < minLen) {
    				minLen = temp;
        			temp = 0;
        			continue;
    			}
    		} else
    			temp++;
    	}
    	
    	
    	ArrayList<String> outputSentences = new ArrayList<>();
    	ArrayList<String> tempSentence = new ArrayList<>();
    	String tempString = "";
    	for(String a : sentenceList) {
    		if(a.equals(" ")) {
    			if(tempSentence.size() <= minLen) {
    				for(String w : tempSentence) {
    					if(tempString.equals("")) {
    						tempString = w;
    					}
    					else {
    						tempString = tempString + " " + w;
    					}
    				}    				
    				outputSentences.add(tempString);
    				tempSentence.clear();
    				tempString = "";
    			}
    		}
    		else {
    			tempSentence.add(a);
    		}
    	}
    	
    	//sorts sentences alphabetically
    	Collections.sort(outputSentences);
    	
    	return outputSentences;
    	
    }
    
    public static ArrayList<String> findAllWords(String morsed, TrieNode root, ArrayList<String> sentence){
    	int sentenceLength = 0;
    	int mcLength = morsed.length();
    	ArrayList<String> finalOutput = new ArrayList<>();
    	ArrayList<String> output = new ArrayList<>();
    	
    	for(String w : sentence) {
    		String currMorse = morseDict.get(dict.indexOf(w));
    		sentenceLength += currMorse.length();
    	}
    	
    	if(sentence.size() > 5) { //hardcoded have to fix
    		return new ArrayList<>();
    	}

    	if(sentenceLength >= mcLength) {
    		ArrayList<String> copy = sentence;
    		copy.add("");
    		return copy;
    	}
    	else {
    		//System.out.println(sentenceLength);
    		ArrayList<String> possibleNext = checkRest(morsed.substring(sentenceLength), root);
    		
    		for(String n : possibleNext) {
    			sentence.add(n);
    			output = findAllWords(morsed, root, sentence);
    			if(!output.isEmpty()) {
    				finalOutput.addAll(output);
    			}
    			sentence.remove(sentence.size() - 1);
    		}
    	}
    	
    	System.out.println(finalOutput);
    	return finalOutput;
    }
    
    public static ArrayList<String> checkRest(String morsed, TrieNode root){
    	ArrayList<String> finalOutput = new ArrayList<>();
    	int curr = morsed.length();
    	int length = morsed.length();
    	String word;
    	
    	while(curr > 0) {
    		if(curr == 0) {
    			word = morsed;
    		}
    		else {
    			word = morsed.substring(0, curr);
    		}
    		ArrayList<String> currList = root.findWord(root, word);
    		if(!currList.isEmpty()) {
    			finalOutput.addAll(currList);
    		}
    		curr--;
    	}
    	

    	return finalOutput;
    }
    

    public static void main(String[] args) throws FileNotFoundException{
        Solution s = new Solution();
    	// Get input from stdin
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        // Parse the style and morsed code value
        String[] parts = command.split(":");
        String style = parts[0].trim();
        String morsed = parts[1].trim();
        //reading in morse code
        File f = new File("morse.txt");
   	 	Scanner scan = new Scanner(f);
		while (scan.hasNextLine()) {
			String [] input = scan.nextLine().split(" ");
			s.hash.put(input[1], input[0]);
			s.revHash.put(input[0], input[1]);
		}
		//read in dictionary
 		File f2 = new File("dictionary.txt");
 		Scanner scan2 = new Scanner(f2);
 		while (scan2.hasNext())
 			s.dict.add(scan2.next());
 		
 		for (int i = 0; i < dict.size(); i++) {
    		String [] split = dict.get(i).split("");
    		String currWord = "";
    		for (String e : split) {
    			if(currWord.equals("")) {
    				currWord = revHash.get(e);
    				morseDict.add(currWord);
    			}
    			else
    				currWord = currWord + " " + revHash.get(e);
    			morseDict.set(i, currWord);
    		}
    	}
        switch (style) {
            case "Spaced Letters":
                String out = handleSpacedLetters(morsed);
                System.out.println(out);
                break;
            case "Word":
            	ArrayList<String> output = new ArrayList<>();
            	String word = "";
                output = handleWord(morsed, word, 0, 1, output);
                
               // System.out.println(output);
                Collections.sort(output);
            	for(String o : output) {
            		System.out.println(o);
            	}
                break;
            case "Spaced Words":
            	TrieNode root = s.new TrieNode();
                handleSpacedWords(morsed, root);
                break;
            case "Sentence":
            	TrieNode root2 = s.new TrieNode();
                handleSentence(morsed, root2);
                break;
        }
        
    }

}