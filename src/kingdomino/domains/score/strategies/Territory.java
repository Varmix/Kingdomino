package kingdomino.domains.score.strategies;

import java.util.*;

import kingdomino.domains.Coordinate;
import kingdomino.domains.Terrain;
import kingdomino.domains.Tile;

/**
 * Calcule sa valeur
 * Connait son type de terrain
 * Connait les coordonnées des tuiles du royaume qui le composent
 * Connait le nombre de couronnes des tuiles qui les composent
 * */
public class Territory {
	private final Set<Coordinate> coordinates = new HashSet<>();
	private final Terrain terrain;
	private int crownsCount;
	
	public Territory(Coordinate c, Tile t) {
		this.coordinates.add(c);
		this.terrain = t.getTerrain();
		this.crownsCount = t.getCrownsCount();
	}

	public static Territory ofCoordinateAndTerrain(Coordinate c, Tile t) {
		return new Territory(c,t);
	}
	
	public boolean isExtensibleWith(Coordinate c, Tile t) {
		if(c == null) {
			return false;
		}
		//T est compatible avec ce territoire
		if(t==null || terrain != t.getTerrain()) {
			return false;
		}
		
		boolean extended = false;
		for(var neighbour : c.getNeighbors()) {
			extended |= this.coordinates.contains(neighbour);
		}
		return extended;
	}
	
	public void add(Coordinate c, Tile t) {
		if(this.isExtensibleWith(c, t)) {
			this.coordinates.add(c);
			this.crownsCount += t.getCrownsCount(); 
		}
	}
	
	public int getScore() {
		return this.crownsCount*this.coordinates.size();
	}
}
