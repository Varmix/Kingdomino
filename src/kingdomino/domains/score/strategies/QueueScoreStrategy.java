package kingdomino.domains.score.strategies;

import java.util.*;

import kingdomino.domains.*;

/**
 * Calcule le score d'une partie de KingDomino
 * 
 * @author Simon Liénardy
 *
 */
public class QueueScoreStrategy implements ScoreStrategy {
	private int value = 0;
	
	@Override
	public void setKingdom(Kingdom kingdom) {
		Map<Coordinate, Tile> asMap = new HashMap<>();
		for(var coord : kingdom) {
			asMap.put(coord, kingdom.getTileAt(coord));
		}
		this.value = QueueScoreStrategy.computeScore(asMap);
	}

	@Override
	public int getValue() {
		return value;
	}
	
	/**
	 * Precondition : kingdom est la représentation sous la forme <Coordinate, Tile> d'un royaume connexe non-vide
	 * (i.e. : si le royaume possède plus d'une coordonée, toute coordonée a au moins une voisine dans le royaume)
	 * start est une coordonnée du royaume 
	 * 
	 * PostCondtion : computeScore est le score atteint par le royaume kingdom, peut importe la Coordinate de
	 * départ choisie. 
	 * 
	 * Require : Coordinate doit pouvoir fournir ses voisins (N/S/O/E)
	 * 
	 * @param kingdom
	 * @param start une Coordinate de départ. voir ci-dessous pour s'en passer.
	 * @see Coordinate Interface qui demande une methode neighbors
	 * @return
	 * 
	 * @throws AssertionError si start ne fait pas partie du royaume.
	 */
	public static int computeScore(Map<Coordinate, Tile> kingdom, Coordinate start) {
		assert kingdom.containsKey(start); // Test de la precondition

		/*
		 * Invariant :
		 *  - sameArea est une FIFO des coord dont il faut aller checker les voisins dans la zone courante
		 *  - territoryArea est le nombre de tuiles observées jusqu'à présent dans la zone courante
		 *  - territoryCrowns est le nombre de couronne dans la zone courante
		 *  - inScore sont les coordonnées qui ont déjà été comptabilisées (dans le score ou dans les var territory*)
		 *  - score est le score de toutes les régions COMPLÈTES dont les coordonnées sont dans inScore
		 *  - seenCoord sont les coordonnées qui ont été vues mais d'une autre couleur que la couleur courante.
		 *  - Si seenCoord est non vide, alors sameArea ne peut pas être vide.
		 * ATTENTION : l'intersection de seenCoord et inScore n'est pas forcément vide !
		 */

		// INITIALISATIONS :
		Queue<Coordinate> sameTerritory = new ArrayDeque<>(); // AD faster than LL
		Set<Coordinate> visited = new HashSet<>();
		Set<Coordinate> discovered = new HashSet<>();

		int territoryArea = 1;
		int territoryCrowns = kingdom.get(start).getCrownsCount();
		visited.add(start); // On a tenu compte de start donc on l'ajoute
		sameTerritory.add(start);
		
		int score = 0;

		// GARDIEN : par l'invariant, on ne doit tester que sameArea.
		// Ça reviendrait au même (logiquement, hein) de boucler tant que kingdom.keySet() et inScore sont différents.
		while(!sameTerritory.isEmpty()) { 

			// Sélectionner la coordonnée courante
			Coordinate cur = sameTerritory.poll(); // Vu le gardien, n'échoue jamais;

			Terrain curArea = kingdom.get(cur).getTerrain(); // Par construction, existe dans le royaume

			/*
			 *  Par définition, 'cur' a déjà été ajoutée au score (vu l'invariant) et il faut aller voir ses voisins.
			 *  Des 4 voisins potentiels, on ne prend que ceux qui existent bel et bien dans le royaume.
			 */
			Collection<Coordinate> neighbors = keepInKingdom(cur.getNeighbors(), kingdom);

			// Parcours des voisins
			for (Coordinate coordinate : neighbors) {

				// Soit la coordonnée est déjà intégrée au score. Dans ce cas, il ne faut rien faire
				// Sinon :
				if(!visited.contains(coordinate)) {

					// La coordonée peut être associée au terrain courant (ou pas)
					if (kingdom.get(coordinate).getTerrain().equals(curArea)) {
						// Si c'est le même terrain, on ajoute cette tuile au calcul de la région en cours... 
						sameTerritory.add(coordinate);
						territoryArea++;
						territoryCrowns += kingdom.get(coordinate).getCrownsCount();
						// ...et on la marque comme vue dans le score.
						visited.add(coordinate);
					}
					else {
						discovered.add(coordinate); // C'est un set donc l'add ne fait rien si existe déjà.
					}
				}
				/*else {
					// Coordonnée déjà vue => ne rien faire.
				}*/
			}

			// S'il n'y a plus de Coordinate dans la région courante
			if(sameTerritory.isEmpty()) {
				// Passer à la région suivante. D'abord comptabiliser le score
				score += territoryArea * territoryCrowns;
				// Et réinitialiser les compteurs de région
				territoryArea = 0;
				territoryCrowns = 0;

				// Parcourir les autres Coordinates vues et choper la 1re qui n'est pas intégrée au score.				
				if (!discovered.isEmpty()) {
					
					// Pas moyen de faire ceci sans l'itérateur et un break dans la boucle. Pardonne-moi, Dijkstra.
					// (en vrai, on pourrait faire une différence d'ensemble, ce serait aussi joli qu'inefficace).
					Coordinate tmp = null;
					for(Coordinate coord : discovered) {
						if (!visited.contains(coord)) { // Filtrer ceux qui ont déjà été ajouté au score.
							tmp = coord;
							break; // Beurk.
						}
					}
					if (tmp != null) {
						discovered.remove(tmp);
						sameTerritory.add(tmp); // Nouvelle région
						territoryArea = 1;
						territoryCrowns += kingdom.get(tmp).getCrownsCount();
						visited.add(tmp);
						// Invariant restauré.
					}
				}
			}
		}
		/*
		 * De la condition d'arrêt : il n'y a plus de coordonnées à visiter
		 * => toutes les régions ont été visitées (puisque par construction, toutes les tuiles vues non ajoutées
		 * au score finissent par être empilées).
		 * De l'invariant : si toutes les régions ont été visitées, score est le score final
		 */
		return score;
	}
	
	public static int computeScore(Map<Coordinate, Tile> kingdom) {
		if (kingdom.isEmpty()) {
			return 0;
		}
		Coordinate start = kingdom.keySet().iterator().next();
		
		return computeScore(kingdom, start);
	}

	private static Collection<Coordinate> keepInKingdom(Collection<Coordinate> candidates, Map<Coordinate, Tile> kingdom) {
		List<Coordinate> result = new LinkedList<>(candidates);
		result.retainAll(kingdom.keySet());

		return result;
	}
	
}
