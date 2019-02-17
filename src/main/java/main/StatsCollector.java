package main;

public class StatsCollector {
	
	private static StatsCollector instance;
	private StatsCollector() {
		setShots(0);
		setMissed(0);
		setEnemies(0);
		setKills(0);
		setPerfectGame(true);
	}
	public static StatsCollector getInstance () {
		if (StatsCollector.instance == null) {
			StatsCollector.instance = new StatsCollector();
	    }
	    return StatsCollector.instance;
	}
	
	public int getMissed() {
		return missed;
	}
	public void setMissed(int missed) {
		this.missed = missed;
	}

	public int getEnemies() {
		return enemies;
	}
	public void setEnemies(int enemies) {
		this.enemies = enemies;
	}

	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getShots() {
		return shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
	}

	public boolean isPerfectGame() {
		return perfectGame;
	}
	public void setPerfectGame(boolean perfectGame) {
		this.perfectGame = perfectGame;
	}

	private int shots,missed,enemies,kills;
	private boolean perfectGame;
	
}
