package kingdomino.domains.score.strategies;

public class ScoreCounter {
	private int crowns;
	private int area;
	private int score;
	
	public ScoreCounter() {
		reset();
	}
	
	public void reset() {
		crowns = 0;
		area = 0;
		score = 0;
	}
	
	public int getScore() {
		return score + (area * crowns);
	}
	
	public void addInArea(int crowns) {
		this.crowns += crowns;
		area++;
	}
	
	public void newArea() {
		score += area * this.crowns;
		area = 0;
		crowns = 0;
	}

	@Override
	public String toString() {
		return "ScoreCounter [crowns=" + crowns + ", area=" + area + ", score=" + score + "]";
	}
	
	
}
