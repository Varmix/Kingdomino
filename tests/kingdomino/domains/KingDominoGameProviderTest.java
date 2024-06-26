package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import kingdomino.io.CsvDataReader;

public class KingDominoGameProviderTest {
	private final static List<Domino> DOMINOES = CsvDataReader.readDominoesFromFile("resources/data/dominoes.csv").stream()
				.map(dto -> new Domino(dto.getId(), 
						new Tile(Terrain.valueOf(dto.getTerrain1()), dto.getCrownsCount1()),
						new Tile(Terrain.valueOf(dto.getTerrain2()), dto.getCrownsCount2())))
				.collect(Collectors.toList());
	
	private final static List<Player> PLAYERS = CsvDataReader.readPlayersFromFile("resources/data/players.csv").stream()
			.map(dto -> new Player(dto.getName(), dto.getHexColor()))
			.collect(Collectors.toList());
	
	@Test
	public void validatesPlayersCountMinPlayers() {
		var factory = new KingDominoGameProvider(DOMINOES, PLAYERS);
		factory.createGameForPlayerCount(2);
		
		var game = factory.getLastGame();
		
		assertEquals(2, game.getPlayers().size());
	}
	
	@Test
	public void validatesPlayersCountMax() {
		var factory = new KingDominoGameProvider(DOMINOES, PLAYERS);
		factory.createGameForPlayerCount(4);
		
		var game = factory.getLastGame();
		
		assertEquals(4, game.getPlayers().size());
	}
	
	
	@Test
	public void validatesPlayersCountUndeflow() {
		var factory = new KingDominoGameProvider(DOMINOES, PLAYERS);
		factory.createGameForPlayerCount(1);
		
		var game = factory.getLastGame();
		
		assertEquals(2, game.getPlayers().size());
	}
	
	@Test
	public void validatesPlayersCountOverflow() {
		var factory = new KingDominoGameProvider(DOMINOES, PLAYERS);
		factory.createGameForPlayerCount(5);
		
		var game = factory.getLastGame();
		
		assertEquals(4, game.getPlayers().size());
	}
	

}
