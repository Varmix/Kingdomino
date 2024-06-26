package kingdomino.domains;

import java.util.*;

/**
 * Fixe le nombre de domino selon le nombre de joueurs.
 * Associe des dominos à des joueurs sur base d'une position.
 * Enumère des dominos.
 * Détermine le joueur qui prend la main.
 * Détermine le domino que le joueur qui a la main doit jouer.
 * 
 * [ALGO] Type de collection
 * Les dominos peuvent être associés à un joueur, donc on pourrait utiliser une 
 * Map<Domino, Player> dans laquelle initialement toutes les valeurs associées 
 * seraient nulles. 
 * Les dominos doivent être triés par numéro, une SortedMap serait donc idéale, 
 * puisque nos dominos sont comparables. L'énumération se fera alors via le 
 * keySet() et son parcours séquentiel, et la méthode firstKey() permet de 
 * retrouver facilement le premier domino et le joueur associé. 
 * On peut aussi retirer un domino qui a été joué via remove(domino) et 
 * firstKey() nous fournit le domino suivant à jouer.
 * 
 * [ALGO] Implémentation de collection
 * La seule implémentation possible d'une SortedMap est la TreeMap, une implémentation
 * en arbre rouge-noir (arbre binaire de recherche plutôt équilibré)
 * keySet() et firstKey() sont de CTT constante (firstKey accède en effet à la 
 * racine de l'arbre), put(k, v) et remove(k) sont en O(logN) (log en base 2) 
 * car au pire, il faut parcourir toute une branche de l'arbre, et la 
 * longueur max d'une branche est de 2.logN
 * */
public final class DrawLine implements Iterable<Domino> {
	public static final DrawLine EMPTY = new DrawLine(null);
	private SortedMap<Domino, Player> dominoes2Players = new TreeMap<>();
	
	/*
	 * [ALGO] CTT du constructeur de DrawLine
	 * Dans le cas de la Map: si N représente le nombre de dominos de la ligne 
	 * de tirage, comme on a une boucle pour les ajouter dans la SortedMap, 
	 * on a déjà O(N). 
	 * Dans cette première boucle, la méthode put de la TreeMap est en O(logN) 
	 * (log en base 2) car elle ajoute une paire clé-valeur (domino-joueur null)
	 * dans un arbre rouge-noir (arbre binaire de recherche plutôt équilibré, et
	 * dont la plus longue branche compte 2logN éléments). 
	 * Au total, on multiplie ces 2 CTT pour obtenir une CTT en O(N.logN)
	 */
	public DrawLine(Collection<Domino> dominoes) {
		dominoes = dominoes != null ? dominoes : List.of();
		for(var domino : dominoes) {
			this.dominoes2Players.put(domino, Player.UNKNOWN);
		}
	}

	@Override
	public Iterator<Domino> iterator() {
		return dominoes2Players.keySet().iterator();
	}
	
	public void affectRandomly(List<Player> givenPlayers) {
		if(givenPlayers == null || givenPlayers.isEmpty()) {
			return;
		}
		var shuffled = new ArrayList<>(givenPlayers);
		Collections.shuffle(shuffled);
		
		int i = 0;
		for(var domino : this) {
			dominoes2Players.replace(domino, shuffled.get( i % givenPlayers.size()));
			++i;
		}
	}

	public void affectDominoToPlayer(Domino d, Player p) {
		dominoes2Players.replace(d, p);
	}
	
	public String getColorFor(Domino d) {
		var p = d == null ? Player.UNKNOWN : dominoes2Players.getOrDefault(d, Player.UNKNOWN);
		return p.getHexColor();
	}

	public Player getTopPlayer() {
		return dominoes2Players.getOrDefault(getTopDomino(), Player.UNKNOWN);
	}

	public Domino getTopDomino() {		
		return dominoes2Players.isEmpty() ? Domino.UNKNOWN : dominoes2Players.firstKey();
	}

	public int getCount() {
		return this.dominoes2Players.size();
	}

	public boolean isFree(Domino d) {
		var p = dominoes2Players.getOrDefault(d, Player.UNKNOWN);
		return p == Player.UNKNOWN;
	}

	public void removeFirst() {
		dominoes2Players.remove(getTopDomino());
	}

	public int getFreeDominosCount() {
		int freeDominos = 0;
		
		for(var d : this) {
			freeDominos += isFree(d) ? 1 : 0;
		}
		
		return freeDominos;
	}

	public Domino getNextFreeFrom(Domino start) {
		var freeDominoBefore = Domino.UNKNOWN;
		var freeDominoAfter = Domino.UNKNOWN;
		
		for(var d : this) {
			if(isFree(d) && start.compareTo(d) >= 0  && freeDominoBefore == Domino.UNKNOWN) {
				freeDominoBefore = d;
			}
			if(isFree(d) && start.compareTo(d) < 0 && freeDominoAfter == Domino.UNKNOWN) {
				freeDominoAfter = d;
			}
		}
		
		return freeDominoAfter != Domino.UNKNOWN ? freeDominoAfter : freeDominoBefore;
	}

}
