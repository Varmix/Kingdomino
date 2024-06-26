package kingdomino.supervisers;

import java.util.*;
import kingdomino.domains.*;

public class GameOverSuperviser extends Superviser {
	private GameOverView view;
	private final KingDominoGameProvider gameProvider;
	private final NavigableSet<Player> players = new TreeSet<>();
	
	public GameOverSuperviser(KingDominoGameProvider gameProvider) {
		this.gameProvider =gameProvider;
	}
	
	public void setView(GameOverView view) {
		this.view = view;
	}
	
	@Override
	public void onEnter(String fromScreen) {
		if("PlayGame".equals(fromScreen)) {
			var lastGame = gameProvider.getLastGame();
			setScores(lastGame);
			draw();
		}
	}

	private void setScores(KingDominoGame game) {
		this.players.clear();
		this.players.addAll(game.getPlayers());
	}
	
	private void draw() {
		view.startDraw();
		drawBonuses();
		var winner = this.players.first();
		view.setWinner(winner.getName()+" remporte la partie.");
		view.endDraw();
	}

	public void onGoToMainMenu() {
		view.goTo("MainMenu");	
	}
	
	private void drawBonuses() {
		final String BONUS = "(Bonus :";
		final String HARMONIE_BONUS = "H";
		final String MIDLLE_EMPIRE_BONUS = "M";
		for(Player p : this.players) {
			if(gameProvider.getConfigurationGames().isMiddleEmpire() == true && p.getKingdom().kingdomIsCenter() == true && gameProvider.getConfigurationGames().isHarmonie() == false) {
				view.addScore(p.getName() + BONUS + MIDLLE_EMPIRE_BONUS + ")", p.getScore(), p.getHexColor());
			} else if(gameProvider.getConfigurationGames().isHarmonie() == true && p.getKingdom().kingdomIsFull() == true && gameProvider.getConfigurationGames().isMiddleEmpire() == false) {
				view.addScore(p.getName() + BONUS + HARMONIE_BONUS + ")", p.getScore(), p.getHexColor());
			} else if(gameProvider.getConfigurationGames().isHarmonie() == true && p.getKingdom().kingdomIsFull() == true && gameProvider.getConfigurationGames().isMiddleEmpire() == true && 
					p.getKingdom().kingdomIsCenter() == true) {
				view.addScore(p.getName() + BONUS + MIDLLE_EMPIRE_BONUS + "," + HARMONIE_BONUS + ")", p.getScore(), p.getHexColor());
			} else if(gameProvider.getConfigurationGames().isHarmonie() == true && p.getKingdom().kingdomIsFull() == true && gameProvider.getConfigurationGames().isMiddleEmpire() == true && 
					p.getKingdom().kingdomIsCenter() == false) {
				view.addScore(p.getName() + BONUS + HARMONIE_BONUS + ")", p.getScore(), p.getHexColor());
			} else if(gameProvider.getConfigurationGames().isHarmonie() == true && p.getKingdom().kingdomIsFull() == false && gameProvider.getConfigurationGames().isMiddleEmpire() == true && 
					p.getKingdom().kingdomIsCenter() == true) {
				view.addScore(p.getName() + BONUS + MIDLLE_EMPIRE_BONUS + ")", p.getScore(), p.getHexColor());
			} else {
				view.addScore(p.getName(), p.getScore(), p.getHexColor());
			}
		}
	}
}
