import java.util.*;
public class TrieNode {
	TrieNode[] children = new TrieNode[2];
	boolean isEnd;
	
	public TrieNode() {
		isEnd = false;
		for (int i = 0; i < 2; i++) {
			children[i] = null;
		}
	}
	
	static TrieNode root;
	
	static void insert(String key) {
		int index = 0;
		
		TrieNode curr = root;
		
		for (int level = 0; level < key.length(); level++) {
			index = key.charAt(level) - 'a';
			if (curr.children[index] == null)
				curr.children[index] = new TrieNode();
			
			curr = curr.children[index];
		}
		
		curr.isEnd = true;
	}
	
	static boolean search(String key) {
		int index;
		TrieNode curr = root;
		
		for (int level = 0; level < key.length(); level++) {
			index = key.charAt(level) - 'a';
			if (curr.children[index] == null)
				return false;
			
			curr = curr.children[index];
		}
		
		return (curr != null && curr.isEnd);
	}
}

/*HashMap<String, TrieNode> children;
String word;

public void addWord(TrieNode root, String in ) {
	TrieNode curr = root;
	String [] split = in.split("");
	for (int i = 0; i < split.length; i++) {
		if (!curr.children.containsKey(split[i])) {
			curr.children.put(split[i], new TrieNode());
		}
		curr = curr.children.get(split[i]);
	}
	curr.word = in;
}

public boolean hasWord(TrieNode root, String target) {
	TrieNode curr = root;
	String [] split = target.split("");
	for (int i = 0; i < split.length; i++) {
		if (curr.children.containsKey(split[i]))
			curr = curr.children.get(split[i]);
		else
			return false;
	}
	return true;
}

public ArrayList<String> getAllWords(TrieNode root) {
	ArrayList<String> result = new ArrayList<>();
	if (root.word != null)
		result.add(root.word);
	ArrayList<String> keys = new ArrayList<>();
	keys.addAll(root.children.keySet());
	Collections.sort(keys);
	for (String i : keys) {
		ArrayList<String> childWords = getAllWords(root.children.get(i));
		result.addAll(childWords);
	}
	return result;
}

public ArrayList<String> autocomplete(TrieNode root, String start) {
	TrieNode curr = root;
	ArrayList<String> ret = new ArrayList<String>();
	String [] split = start.split("");
	for (int i = 0; i < split.length;i++) {
		if (curr.children.containsKey(split[i]))
			curr = curr.children.get(split[i]);
		else
			return ret;
	}
	return getAllWords(curr);
}
*/