
public class Team {
	
	private int id;
	private String name;
	private int rating;
	private int winStreak;
	private int points;
	private int numberGames;
	private int goalsScored;
	private int goalsSufered;
	private double odd;

	public Team(int id, String name, int rank) {
		this.id=id;
		this.name=name;
		this.rating = rank;
		this.winStreak = 0;
		this.points=0;
		this.numberGames=0;
		this.goalsScored=0;
		this.goalsSufered=0;
		this.odd=0;
	}

	public int getRank() {
		return rating;
	}

	public void setRank(int rank) {
		this.rating = rank;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getWinStreak() {
		return winStreak;
	}

	public void setWinStreak(int winStreak) {
		this.winStreak = winStreak;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int p) {
		points+=p;
	}
	
	public int getNumberGames() {
		return numberGames;
	}
	
	public void addNumberGames() {
		numberGames++;
	}
	
	public int getGoalsScored() {
		return goalsScored;
	}

	public void setGoalsScored(int goalsScored) {
		this.goalsScored += goalsScored;
	}

	public int getGoalsSufered() {
		return goalsSufered;
	}

	public void setGoalsSufered(int goalsSufered) {
		this.goalsSufered += goalsSufered;
	}

	public double getOdd() {
		return odd;
	}

	public void setOdd(double odd) {
		this.odd = odd;
	}
	
	
	
	
	
	

}
