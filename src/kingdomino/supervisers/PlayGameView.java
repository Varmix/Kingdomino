package kingdomino.supervisers;

public interface PlayGameView {
	public enum ViewMode {
		KINGDOM, LINES
	}
	
	void startDraw();

	void addToNextLine(String terrain1, int crownsCount1, String terrain2, int crownsCount2, String color);

	void addToActualLine(String terrain1, int crownsCount1, String string2, int crownsCount2, String colorFor);

	void addToKingdom(String terrain, int crownsCount, int row, int col);

	void addToKingdom(String terrain, int crownsCount, int row, int col, String hexColor);
	
	void endDraw();

	void switchMode(ViewMode mode);
	
	void addMessage(String msg);

	void goTo(String toView);
}
