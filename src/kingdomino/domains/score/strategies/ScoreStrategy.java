package kingdomino.domains.score.strategies;

import kingdomino.domains.Kingdom;

public interface ScoreStrategy {
	public void setKingdom(Kingdom kingdom);
	public int getValue();
}
