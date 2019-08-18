package project.nflsim;

import java.text.DecimalFormat;

public class game {
	private int week;
	private team home;
	private team away;
	private Double line;
	private Boolean neutral;
	private String time_of_day; //Early,Late, Night
	private String timezone; //East, Central, Mountain, West
	
	//Method to check previous games
	
	game(){
		setWeek(0);
		home = new team();
		away = new team();
		timezone = "";
		time_of_day = "";
		line = 0.0;
		neutral = false;
	}
	
	game(int w, team h, team a, Boolean l){
		setWeek(w);
		home = h;
		away = a;
		neutral = l;
		timezone = "";//FIX
		time_of_day = "";//FIX
		if(neutral){
			calcLineNeutral();
		}else{
			calcLine();
		}
	}
	
	Boolean checkEmpty(){
		Boolean e = false;
		
		if(week == 0){
			e = true;
		}
		
		return e;
	}
	
	void calcLine(team h, team a){
		line = h.getHome_rank() - a.getAway_rank();
	}
	
	void calcLine(){
		line = this.home.getHome_rank() - this.away.getAway_rank();
	}
	
	void calcLineNeutral(){
		line = this.home.getAway_rank() - this.away.getAway_rank();
	}
	
	void print(){
		
		DecimalFormat df = new DecimalFormat("#.##");
		String formatted = df.format((line * -1));
		
		System.out.println("Week " + this.getWeek() + ": " + this.away.getCode() + " @ " + this.home.getCode() + " : " + this.home.getCode() + " " + formatted);
		/*if (line > 0 ){
			
			System.out.println(this.home.getName() + " favoured by " + line + " points");
		}else{
			System.out.println(this.away.getName() + " favoured by " + (line * -1) + " points");
		}*/
		//System.out.println("\n");
	}

	public team getHome() {
		return home;
	}

	public void setHome(team home) {
		this.home = home;
	}

	public team getAway() {
		return away;
	}

	public void setAway(team away) {
		this.away = away;
	}

	public Double getLine() {
		return line;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}


}
