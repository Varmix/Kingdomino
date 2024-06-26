package kingdomino.domains.score.strategies;

import java.util.*;

import kingdomino.domains.*;

public class RecursiveScoreStrategy implements ScoreStrategy {

	private Kingdom kingdom;

	private final Set<Coordinate> visited = new HashSet<>();
	private final Deque<Coordinate> discovered = new ArrayDeque<>();

	private ScoreCounter sc = new ScoreCounter();

	public RecursiveScoreStrategy(Kingdom kingdom) {
		this.setKingdom(kingdom);
	}

	public static RecursiveScoreStrategy forKingdom(Kingdom kingdom) {
		return new RecursiveScoreStrategy(kingdom);
	}

	@Override
	public void setKingdom(Kingdom kingdom) {
		if (kingdom != null) {
			this.kingdom = kingdom;
			this.compute();
		}
	}

	@Override
	public int getValue() {
		return sc.getScore();
	}

	private void compute() {
		initialise();
		discovered.add(Coordinate.ofRowCol(4, 4)); // Chateau doit être présent.
		visitAreas();
	}

	private void initialise() {
		visited.clear();
		discovered.clear();

		sc.reset();
	}

	private void visitAreas() {
		if (!discovered.isEmpty()) {
			Coordinate next = discovered.pollFirst();
			if (!visited.contains(next)) {
				sc.newArea();
				visitSameArea(next);
			}
			visitAreas();
		}
		// discovered is empty -> end	
	}

	private void visitSameArea(Coordinate coord) {
		
		sc.addInArea(kingdom.getCrownsCountAt(coord));
		visited.add(coord);
		
		List<Coordinate> neighbors = kingdom.getNeighbors(coord);

		// Version récursive laissée au lecteur à titre d'exercice :-P
		for(Coordinate c : neighbors) { 
			if (!visited.contains(c)) {
				if (kingdom.getTerrainAt(c) == kingdom.getTerrainAt(coord)) {
					visitSameArea(c);
				}
				else {
					discovered.addLast(c);
				}
			}
		}
	}

}
