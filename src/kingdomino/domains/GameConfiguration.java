package kingdomino.domains;

public class GameConfiguration {
	
	private boolean fastGame = false;
	private boolean harmonie = false;
	private boolean middleEmpire = false;
	
	/**
	 * 
	 * @param fastGame booléen pour l'option de jeu "Partie rapide"
	 * @param harmonie booléen pour l'option de jeu "Harmonie"
	 * @param middleEmpire booléen pour l'option de jeu "Empire du milieu"
	 * Instancie l'objet GameConfiguration via les trois attributs ci-dessus
	 */
	public GameConfiguration(boolean fastGame, boolean harmonie, boolean middleEmpire) {
		this.fastGame = fastGame;
		this.harmonie = harmonie;
		this.middleEmpire = middleEmpire;
	}
	
	/**
	 * 
	 * @return true si l'option du jeu "Partie Rapide" est activée, sinon false
	 */
	public boolean isFastGame() {
		return fastGame;
	}
	
	/**
	 * 
	 * @return true si l'option de jeu "Harmonie" est activée, sinon false
	 */
	public boolean isHarmonie() {
		return harmonie;
	}
	
	/**
	 * 
	 * @return true si l'option de jeu "Empire du milieu" est activée, sinon false
	 */
	public boolean isMiddleEmpire() {
		return middleEmpire;
	}
	
	/**
	 * Permet d'activer les options de jeu en fonction de la chaine de caractères reçue.
	 * @param optionItem une chaine de caractères relative aux options de jeu
	 * 
	 */
	public void setGameOptions(String optionItem) {
		
		fastGameTreatment(optionItem);
		harmonieTreatment(optionItem);
		middleEmpireTreatmen(optionItem);

	}

	private void fastGameTreatment(String optionItem) {
		if(optionItem.equalsIgnoreCase("[X] Partie rapide")) {
			fastGame = true;
		} else if(optionItem.equalsIgnoreCase("[] Partie rapide")) {
			fastGame = false;
		}
	}
	
	private void harmonieTreatment(String optionItem) {
		if(optionItem.equalsIgnoreCase("[X] Harmonie")) {
			harmonie = true;
		} else if(optionItem.equalsIgnoreCase("[] Harmonie")) {
			harmonie = false;
		}
	}
		
	private void middleEmpireTreatmen(String optionItem) {
		if(optionItem.equalsIgnoreCase("[X] Empire du milieu")) {
			middleEmpire = true;
		} else if (optionItem.equalsIgnoreCase("[] Empire du milieu")) {
			middleEmpire = false;
		}
	}
	
	
	
	
	
	
	

}
