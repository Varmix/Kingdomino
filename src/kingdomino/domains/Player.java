package kingdomino.domains;

import kingdomino.domains.score.strategies.DequeScoreStrategy;
import kingdomino.domains.score.strategies.ScoreStrategy;

/**
 * Connait son nom et sa couleur 
 * Gère son royaume.
 * */
public class Player implements Comparable<Player> {
	public static final Player UNKNOWN = new Player("?", "#ffffff");
	private final String name;
	private final String hexColor;
	private Kingdom kingdom;
	private ScoreStrategy score;
	private GameConfiguration optionsChooseByPlayer;
	
	public Player(String name, String hexColor) {
		this.name = name;
		this.hexColor = hexColor;
		this.kingdom = Kingdom.EMPTY;
	}
	
	public String getHexColor() {
		return hexColor;
	}

	public Kingdom getKingdom() {
		return kingdom;
	}

	public void setKingdom(Kingdom kingdom) {
		this.kingdom = kingdom;
		
	}
	/**
	 * 
	 * @return
	 * Le score du joueur comprenant ses bonus.
	 */
	public int getScore() {
		if(score == null) {
			score = DequeScoreStrategy.forKingdom(kingdom);
		}
		int optionValues = optionsChooseByPlayer == null ? 0 : optionsScore();
		return  score.getValue() + optionValues;
	}

	private int optionsScore() {
		final int MIDDLE_EMPIRE_POINT = 10;
		final int HARMONIE_POINT = 5;
		int optionsValue = 0;
		if(optionsChooseByPlayer.isMiddleEmpire() && kingdom.kingdomIsCenter()) {
			optionsValue = optionsChooseByPlayer.isFastGame() ? (MIDDLE_EMPIRE_POINT/2) : MIDDLE_EMPIRE_POINT;
			
		} else if (optionsChooseByPlayer.isHarmonie() && kingdom.kingdomIsFull()) {
			optionsValue = optionsChooseByPlayer.isFastGame() ? (HARMONIE_POINT/2) : HARMONIE_POINT;

		}
		return optionsValue;
	}

	
	public String getName() {
		return name;
	}
	

	public boolean setDominoAt(Domino currentDomino, Coordinate tilePos1, Coordinate tilePos2) {
		if(kingdom.isValid(currentDomino, tilePos1, tilePos2)) {
			kingdom.setDominoAt(currentDomino, tilePos1, tilePos2);
			score = null;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Player o) {
		var scoreComparison = o.getScore() - this.getScore();
		if(scoreComparison == 0) {
			return this.name.compareTo(o.name);
		} else {
			return scoreComparison;
		}
	}

	public void playInGameOptions(GameConfiguration gameConfigurator) {
		optionsChooseByPlayer = gameConfigurator;	
	}

}
