package kingdomino.domains.score.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import kingdomino.domains.Kingdom;

public class ScoreStrategiesTest {
	
	@ParameterizedTest
	@MethodSource("kingdomino.domains.score.strategies.ScoreTestSource#getSamples")
	public void validatesDequeStrategy(Kingdom sample, int expected) {
		ScoreStrategy strategy = DequeScoreStrategy.forKingdom(sample);
		
		assertEquals(expected, strategy.getValue());
	}
	
	@ParameterizedTest
	@MethodSource("kingdomino.domains.score.strategies.ScoreTestSource#getSamples")
	public void validatesQueueStrategy(Kingdom sample, int expected) {
		ScoreStrategy strategy = new QueueScoreStrategy();
		strategy.setKingdom(sample);
		assertEquals(expected, strategy.getValue());
	}
	
	@ParameterizedTest
	@MethodSource("kingdomino.domains.score.strategies.ScoreTestSource#getSamples")
	public void validatesRecursiveStrategy(Kingdom sample, int expected) {
		ScoreStrategy strategy = RecursiveScoreStrategy.forKingdom(sample);
		
		assertEquals(expected, strategy.getValue());
	}
}
