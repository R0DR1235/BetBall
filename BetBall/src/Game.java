import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


public class Game {
	
	private Team team1;
	private Team team2;
	
	public Game(Team team1, Team team2) {
		this.team1 = team1;
		this.team2 = team2;
	}

	public Team getTeam1() {
		return team1;
	}

	public Team getTeam2() {
		return team2;
	}
	
	public int numberOfGoals() {
		
		double u1= Math.random();
		if(u1<0.08) {
			return 0;
		}else if(u1>=0.08 && u1<0.27) {
			return 1;
		}else if(u1>=0.27 && u1<0.52) {
			return 2;
		}else if(u1>=0.52 && u1<0.74) {
			return 3;
		}else if(u1>=0.74 && u1<0.90) {
			return 4;
		}else if(u1>=0.90 && u1<0.96) {
			return 5;
		}else if(u1>=0.96 && u1<0.99) {
			return 6;
		}else if(u1>=0.99) {
			return 7;
		}
		return 0;
	}
	
	public ArrayList<Integer> timeOfGoals(int numberGoals) {
		
		ArrayList<Integer> times=new ArrayList<Integer>();
		
		for(int i=0;i<numberGoals;i++) {
			double u1= Math.random();
			double u2= Math.random();
			double x=0;
			if(u1<0.12) {
				x= u2*15; 
			}else if(u1>=0.12 && u1<0.27) {
				x= 15 + u2*15; 
			}else if(u1>=0.27 && u1<0.45) {
				x= 30 + u2*15; 
			}else if(u1>=0.45 && u1<0.61) {
				x= 45 + u2*15; 
			}else if(u1>=0.61 && u1<0.75) {
				x= 60 + u2*15; 
			}else if(u1>=0.75) {
				x= 75 + u2*15; 
			}
			times.add((int)x);
		}
		Collections.sort(times);
		
		return times;
	}
	
	public double ballPossession() {
		double u1= Math.random();
		double u2= Math.random();
		double x=0;
		if(u1<0.10) {
			x= 30 + u2*10; 
		}else if(u1>=0.10 && u1<0.25) {
			x= 40 + u2*10; 
		}else if(u1>=0.25 && u1<0.75) {
			x= 50 + u2*10;  
		}else if(u1>=0.75) {
			x= 60 + u2*10; 
		}
		Double x2=new BigDecimal(x).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return x2;
	}
	
	public static double probabilityOfWinning(Team a,int v) {
		//rating
		double ratingProbability=0;
		int rank=a.getRank();
		if(rank<50) ratingProbability=25;
		if(rank>=50 && rank<65) ratingProbability=40;
		if(rank>=65 && rank<80) ratingProbability=65;
		if(rank>=80) ratingProbability=95;
		//winstreak
		double winstreakProbability=0;
		int winstreak=a.getWinStreak();
		if(winstreak==0) winstreakProbability=10;
		if(winstreak==1) winstreakProbability=20;
		if(winstreak==2) winstreakProbability=40;
		if(winstreak>=3) winstreakProbability=70;
		//homeaway
		double homeawayProbability=0;
		if(v==0) homeawayProbability=35;
		else homeawayProbability=65;
		
		//total
		return ratingProbability*0.80+winstreakProbability*0.10+homeawayProbability*0.10;
	}
	
	public double team1Winning() {
		double team1P = probabilityOfWinning(team1,1);
		double team2P = probabilityOfWinning(team2,0);
		
		double finalTeam1= (((team1P*100)/(team1P+team2P))/100) - 0.025;
		return finalTeam1;
	}
	
	
	public Team winner(double finalTeam1) {
		
		double u1= Math.random();
		if(u1<=finalTeam1) {
			team1.setPoints(3);
			team1.setWinStreak(team1.getWinStreak()+1);
			if(team2.getWinStreak()-1>=0)team2.setWinStreak(team2.getWinStreak()-1);
			team1.addNumberGames();team2.addNumberGames();
			return team1;
		}else if(u1>finalTeam1 && u1<finalTeam1+0.05){
			team1.setPoints(1);
			if(team1.getWinStreak()-1>=0)team1.setWinStreak(team1.getWinStreak()-1);
			team2.setPoints(1);
			if(team2.getWinStreak()-1>=0)team2.setWinStreak(team2.getWinStreak()-1);
			team1.addNumberGames();team2.addNumberGames();
			return null;
		}else if(u1>=finalTeam1+0.05){
			team2.setPoints(3);
			team2.setWinStreak(team2.getWinStreak()+1);
			if(team1.getWinStreak()-1>=0)team1.setWinStreak(team1.getWinStreak()-1);
			team1.addNumberGames();team2.addNumberGames();
			return team2;
		}
		return null;
	}
	
	
}
