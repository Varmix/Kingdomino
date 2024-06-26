package kingdomino.domains;

import java.util.*;

/**
 * Crée une nouvelle partie de Kingdomino.
 * Mémorise la dernière partie de Kingdomino créée.
 * 
 * [Algo] Relation entre le nombre de dominos à jouer et le nombre de joueurs.
 * Dans le cas d'une partie standard, le nombre de dominos maximum à jouer varie (24 pour 2 joueurs, 36 pour 3 joueurs et 48 pour 4 joueurs).
 * La formule sera : le nombre maximum de dominos à jouer en fonction des joueurs / le nombre de joueurs.
 * Dans le cas d'une partie standard de 2 joueurs : 24/2  = 12, Dmax sera à 12.
 * Dans le cas d'une partie standard de 3 joueurs : 36/3 = 12, Dmax sera à 12.
 * Dans le cas d'une partie standard de 4 joueurs : 48/4 = 12, Dmax sera à 12.
 * Dans tous les cas d'une partie standard, le nombre de dominos qu'au maxium un joueur pourra posé sera de 12.
 * 
 * Dans le cas d'une partie rapide, on utilise 3 fois moins de dominos qu'une partie normale ( Par exemple, pour 36 dominos en partie normale à 3 joueurs,
 * il n'en restera que 12 lors d'une partie rapide.)
 * Le formule sera : ((le nombre maximum de dominos à placer dans le cas d'une partie standard)/3) / le nombre de joueurs.
 * Dans le cas d'une partie rapide de 2 joueurs : ((24)/3) / 2 = 4
 * Dans le cas d'une partie rapide de 3 joueurs : ((36)/3) / 3 = 4
 * Dans le cas d'une partie rapide de 4 joueurs : ((48)/3) / 4 = 4
 * Dans tous les cas d'une partie rapide, le nombre de dominos qu'un joueur pourra posé au maximum sera de 4.
 * 
 * */
public class KingDominoGameProvider {
	private final Collection<Domino> allDominoes;
	private final List<Player> allPlayers;
	private KingDominoGame lastInstance;
	private GameConfiguration gameConfigurator;
	
	public KingDominoGameProvider(Collection<Domino> allDominoes, Collection<Player> allPlayers) {
		this.allDominoes = List.copyOf(allDominoes);
		this.allPlayers = List.copyOf(allPlayers);
		gameConfigurator = new GameConfiguration(false, false, false);
	}
	
	public void createGameForPlayerCount(int playersCount) {
		var validatedPlayersCount = Math.max(2, Math.min(4, playersCount));
		
		
		var pile = createPileForPlayersCount(validatedPlayersCount);
		var players = selectAndResetPlayers(validatedPlayersCount);
		
		lastInstance = new KingDominoGame(players, pile);
	}

	private Collection<Player> selectAndResetPlayers(int validatedPlayersCount) {
		var selection = allPlayers.subList(0, validatedPlayersCount);
		
		for(var p : selection) {
			p.playInGameOptions(gameConfigurator);
			if(gameConfigurator.isFastGame()) {
				p.setKingdom(Kingdom.ofCapacity(3));
			} else {
				p.setKingdom(Kingdom.ofCapacity(5));
			}
		}
		
		return selection;
	}

	private DominoPile createPileForPlayersCount(int count) {
		var allDominoesCount = allDominoes.size();
		var quarterDominoesCount = allDominoesCount/4;
		
		if(gameConfigurator.isFastGame()) {
			return new DominoPile(allDominoes,(quarterDominoesCount*count)/3);
		} else {
			return new DominoPile(allDominoes,quarterDominoesCount*count);
		}
		
	}
	
	public KingDominoGame getLastGame() {
		return lastInstance;
	}

	/**
	 * permet d'activer ou non les options du jeu
	 * @param optionItem optionItem une chaine de caractères relatif aux options de jeu
	 */
	public void activateGameOptions(String optionItem) {
		this.gameConfigurator.setGameOptions(optionItem);
	}
	
	/**
	 * 
	 * @return
	 * les différentes informations concernant les options du jeu ("Partie rapide", "Empire du milieu", "Harmonie")
	 */
	public GameConfiguration getConfigurationGames() {
		return gameConfigurator;
	}

}
