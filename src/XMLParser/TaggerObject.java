package XMLParser;

public class TaggerObject {
	private int startNode;
	public int getStartNode() {
		return startNode;
	}
	public void setStartNode(int startNode) {
		this.startNode = startNode;
	}
	/*private int endNode;
	public int getEndNode() {
		return endNode;
	}
	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}*/
	private String tag;
	private String word;
private int wordCount=0;
	
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	/*public String toString() {
		return "TaggerObject [startNode=" + startNode + ", tag=" + tag + ", word=" + word + ", wordCount=" + wordCount
				+ "]";
	}*/
	
	public String toString(){
		return word;
	}
	
	

}
