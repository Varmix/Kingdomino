package kingdomino.supervisers;

import java.util.List;
import java.util.ArrayList;


import kingdomino.domains.*;

public final class MainMenuSuperviser extends Superviser {
	public static final String QUIT = "Quitter";
	public static final String NEW_4_PLAYERS = "Jouer à 4";
	public static final String NEW_3_PLAYERS = "Jouer à 3";
	public static final String NEW_2_PLAYERS = "Jouer à 2";
	public static final String GAME_OPTIONS = "Options du jeu";
	public static final String FAST_GAME_DISABLE = "[] Partie Rapide";
	public static final String MIDDLE_EMPIRE_DISABLE = "[] Empire du milieu";
	public static final String HARMONIE_DISABLE = "[] Harmonie";
	public static final String FAST_GAME_ENABLE = "[X] Partie rapide";
	public static final String MIDDLE_EMPIRE_ENABLE = "[X] Empire du milieu";
	public static final String HARMONIE_ENABLE = "[X] Harmonie";
	public static final String RETURN = "Retour";
	
	private boolean optionItemsMenu = false;
	
	private final List<String> items = List.of(NEW_2_PLAYERS,NEW_3_PLAYERS, NEW_4_PLAYERS, GAME_OPTIONS, QUIT);
	private List<String> optionItems = List.of(FAST_GAME_DISABLE, MIDDLE_EMPIRE_DISABLE, HARMONIE_DISABLE, RETURN);
	private final KingDominoGameProvider gameFactory;
	private MainMenuView screen;
	
	public MainMenuSuperviser(KingDominoGameProvider gameFactory) {
		this.gameFactory = gameFactory;
	}

	public void setView(MainMenuView screen) {
		
		this.screen = screen;
		this.screen.setItems(items);
	}
	/**
	 * Gestion du menu principal permet de jouer une partie à N joueurs, aller dans les options de jeu ou de quitter l'application
	 * @param selected le numéro de la constante parmi la liste des options du menu principal
	 */
	public void onItemSelected(int selected) {
		if(selected < 0 || selected >= items.size()) {
			return;
		}
		if(optionItemsMenu) {
			onOptionItemSelected(selected);
			return;
		}
		var item = items.get(selected);
		if(QUIT == item) {
			this.screen.onQuitConfirmed("MainMenu");
		} else if (GAME_OPTIONS == item) {
				gameOptionsDisplay();
		} else {
			gameFactory.createGameForPlayerCount(getPlayersCountFromItem(item));
			screen.goTo("PlayGame");
		}	
	}

	private void gameOptionsDisplay() {
		this.screen.setItems(optionItems);
		optionItemsMenu = true;
	}
	
	/**
	 * Gère le traitement d'affichage des options du jeu
	 * @param selected (le numéro de la constante parmi la liste des options de jeu)
	 * 
	 */
	public void onOptionItemSelected(int selected) {
		
		var optionItemsMutable = new ArrayList<>(optionItems);
		var optionItem = optionItemsMutable.get(selected);
		
		if(selected == 0) {
			fastGameTreatment(optionItem, optionItemsMutable, selected);
		} else if(selected == 1) {
			middleEmpireTreatment(optionItem, optionItemsMutable, selected);
		} else if(selected == 2) {
			harmonieTreatment(optionItem, optionItemsMutable, selected);
		} else {
			treatmentReturn();
		}
		
	}
	
	private void harmonieTreatment(String optionItem, List<String> optionItemsMutable, int selected) {
		if (HARMONIE_ENABLE == optionItem) {
			treatmentOption(selected, optionItemsMutable, HARMONIE_DISABLE);
		} else if (HARMONIE_DISABLE == optionItem) {
			treatmentOption(selected, optionItemsMutable, HARMONIE_ENABLE);
		}
		
	}

	private void fastGameTreatment(String optionItem, List<String> optionItemsMutable, int selected) {
		if(FAST_GAME_DISABLE == optionItem) {
			treatmentOption(selected, optionItemsMutable, FAST_GAME_ENABLE);
		} else if(FAST_GAME_ENABLE== optionItem) {
			treatmentOption(selected, optionItemsMutable, FAST_GAME_DISABLE);	
		}
	}
	
	private void middleEmpireTreatment(String optionItem, List<String> optionItemsMutable, int selected) {
		if (MIDDLE_EMPIRE_DISABLE == optionItem) {
			treatmentOption(selected, optionItemsMutable, MIDDLE_EMPIRE_ENABLE);

		} else if (MIDDLE_EMPIRE_ENABLE == optionItem) {
			treatmentOption(selected, optionItemsMutable, MIDDLE_EMPIRE_DISABLE);
		}
	}

	private void treatmentReturn() {
		this.screen.setItems(items);
		optionItemsMenu = false;
	}

	private void treatmentOption(int selected, List<String> optionItemsMutable, String optionItem) {
		optionItemsMutable.set(selected, optionItem);
		optionItems = optionItemsMutable;
		gameFactory.activateGameOptions(optionItem);
		this.screen.setItems(optionItems);
	}




	private int getPlayersCountFromItem(String item) {
		if(NEW_4_PLAYERS == item) {
			return 4;
		} else if(NEW_3_PLAYERS == item) {
			return 3;
		} else {
			return 2;
		}
	}


}
