package kingdomino.domains.score.strategies;

import java.util.*;

import kingdomino.domains.*;

/**
 * Calcule le score d'une partie de KingDomino à l'aide de Deque et de territoires.
 * 
 * @author Nicolas Hendrikx
 *
 */
public final class DequeScoreStrategy implements ScoreStrategy {
	private final List<Territory> territories = new ArrayList<>(2);
	private final Deque<Coordinate> pending = new ArrayDeque<>();
	private final Set<Coordinate> visited = new HashSet<>();
	private Kingdom kingdom;
	private GameConfiguration gameConfigurator;

	public DequeScoreStrategy(Kingdom kingdom) {
		this.setKingdom(kingdom);
	}

	public static DequeScoreStrategy forKingdom(Kingdom kingdom) {
		return new DequeScoreStrategy(kingdom == null ? Kingdom.EMPTY : kingdom);
	}

	@Override
	public void setKingdom(Kingdom kingdom) {
		if (kingdom != null) {
			this.kingdom = kingdom;
			this.compute();
		}
	}

	public void compute() {
		// initialiser les tuiles en attentes
		initCollections();
		// tant qu'il reste des tuiles en attentes l'explorer
		while (!pending.isEmpty()) {
			Coordinate c = pending.pollFirst();
			if (!visited.contains(c)) {
				addToTerritory(c);
				visited.add(c);
				expandFrom(c);
			}
		}
	}

	@Override
	public int getValue() {
		return territories.stream().mapToInt(Territory::getScore).sum();
	}
	

	private void initCollections() {
		territories.clear();
		visited.clear();
		pending.clear();

		for (Coordinate neighbour : kingdom.getNeighbors(Coordinate.ofRowCol(4, 4))) {
			pending.add(neighbour);
		}
	}

	private void addToTerritory(Coordinate c) {
		Tile tile = kingdom.getTileAt(c);
		if (territories.isEmpty()) {
			territories.add(new Territory(c, tile));
		} else {
			var territory = territories.get(territories.size() - 1);
			if (territory.isExtensibleWith(c, tile)) {
				territory.add(c, tile);
			} else {
				territories.add(new Territory(c, tile));
			}
		}
	}

	private void expandFrom(Coordinate c) {
		Tile tile = kingdom.getTileAt(c);
		for (Coordinate neighbour : kingdom.getNeighbors(c)) {
			Tile neighbourTile = kingdom.getTileAt(neighbour);
			if (tile.getTerrain() == neighbourTile.getTerrain()) {
				pending.addFirst(neighbour);
			} else if (!visited.contains(neighbour)) {
				pending.addLast(neighbour);
			}
		}
	}

}