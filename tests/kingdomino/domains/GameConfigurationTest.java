package kingdomino.domains;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameConfigurationTest {

	@Test
	void gameWithFastGameActivated() {
		GameConfiguration gameConfigurator = new GameConfiguration(true, false, false);
		assertTrue(gameConfigurator.isFastGame());
	}
	
	@Test
	void gameWithHarmonieActivated() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, true, false);
		assertTrue(gameConfigurator.isHarmonie());
	}
	
	@Test
	void gameWithEmpireDuMilieuActivated() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, true);
		assertTrue(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void gameWithFastGameAndMidlleEmpireOptions() {
		GameConfiguration gameConfigurator = new GameConfiguration(true, false, true);
		assertTrue(gameConfigurator.isFastGame());
		assertFalse(gameConfigurator.isHarmonie());
		assertTrue(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void gameWithFastGameAndHarmonieOptions() {
		GameConfiguration gameConfigurator = new GameConfiguration(true, true, false);
		assertTrue(gameConfigurator.isFastGame());
		assertTrue(gameConfigurator.isHarmonie());
		assertFalse(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void gameWithNoGameOptions() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		assertFalse(gameConfigurator.isFastGame());
		assertFalse(gameConfigurator.isHarmonie());
		assertFalse(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void gameWithALLGameOptions() {
		GameConfiguration gameConfigurator = new GameConfiguration(true, true, true);
		assertTrue(gameConfigurator.isFastGame());
		assertTrue(gameConfigurator.isHarmonie());
		assertTrue(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void setGameOptionsFastGameEnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
		assertTrue(gameConfigurator.isFastGame());
	}
	
	@Test
	void setGameOptionsFastGameUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
		assertFalse(gameConfigurator.isFastGame());
	}
	
	@Test
	void setGameOptionsHarmonieEnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Harmonie";
		gameConfigurator.setGameOptions(optionItem);
		assertTrue(gameConfigurator.isHarmonie());
	}
	
	@Test
	void setGameOptionsHarmonieUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[] Harmonie";
		gameConfigurator.setGameOptions(optionItem);
		assertFalse(gameConfigurator.isHarmonie());
	}
	
	@Test
	void setGameOptionsMidlleEmpireEnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Empire du milieu";
		gameConfigurator.setGameOptions(optionItem);
		assertTrue(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void setGameOptionsMidlleEmpireUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[] Empire du milieu";
		gameConfigurator.setGameOptions(optionItem);
		assertFalse(gameConfigurator.isMiddleEmpire());
	}
	
	@Test
	void setGameOptionsFastGameAndMiddleEmpireAndHarmonieUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
	    optionItem = "[X] Empire du milieu";
		gameConfigurator.setGameOptions(optionItem);
		optionItem = "[X] Harmonie";
		gameConfigurator.setGameOptions(optionItem);
		
		assertTrue(gameConfigurator.isFastGame());
		assertTrue(gameConfigurator.isMiddleEmpire());
		assertTrue(gameConfigurator.isHarmonie());
	}
	
	@Test
	void setGameOptionsFastGameAndMiddleEmpireUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
	    optionItem = "[X] Empire du milieu";
		gameConfigurator.setGameOptions(optionItem);
		
		assertTrue(gameConfigurator.isFastGame());
		assertTrue(gameConfigurator.isMiddleEmpire());
		assertFalse(gameConfigurator.isHarmonie());
	}
	
	@Test
	void setGameOptionsFastGameAndHarmonieUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(false, false, false);
		String optionItem = "[X] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
		optionItem = "[X] Harmonie";
		gameConfigurator.setGameOptions(optionItem);
		
		assertTrue(gameConfigurator.isFastGame());
		assertFalse(gameConfigurator.isMiddleEmpire());
		assertTrue(gameConfigurator.isHarmonie());
	}
	
	@Test
	void setAllGameOptionsUnable() {
		GameConfiguration gameConfigurator = new GameConfiguration(true, true, true);
		String optionItem = "[] Partie rapide";
		gameConfigurator.setGameOptions(optionItem);
	    optionItem = "[] Empire du milieu";
		gameConfigurator.setGameOptions(optionItem);
		optionItem = "[] Harmonie";
		gameConfigurator.setGameOptions(optionItem);
		
		assertFalse(gameConfigurator.isFastGame());
		assertFalse(gameConfigurator.isMiddleEmpire());
		assertFalse(gameConfigurator.isHarmonie());
	}


}
