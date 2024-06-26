package kingdomino.domains.dummies;

import java.util.*;
import java.util.stream.Collectors;

import kingdomino.domains.*;
import kingdomino.io.CsvDataReader;

public class DummyDominoPileFactory {
	private final static List<Domino> DOMINOES = CsvDataReader.readDominoesFromFile("resources/data/dominoes.csv").stream()
			.map(dto -> new Domino(dto.getId(), 
					new Tile(Terrain.valueOf(dto.getTerrain1()), dto.getCrownsCount1()),
					new Tile(Terrain.valueOf(dto.getTerrain2()), dto.getCrownsCount2())))
			.collect(Collectors.toList());
	
	public static DominoPile newDominoPile(int count, int threshold) {
		var dominoes = new ArrayList<Domino>();
		for(int i=0; i < count; ++i) {
			dominoes.add(DOMINOES.get(i));
		}
		return new DominoPile(dominoes, threshold);
	}
}
