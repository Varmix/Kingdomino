package kingdomino.supervisers;

public interface GameOverView {
	void startDraw();
	void addScore(String playerName, int score, String color);
	void endDraw();
	void setWinner(String winnerMessage);
	void goTo(String toView);
}
