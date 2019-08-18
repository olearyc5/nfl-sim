package project.nflsim;

public class team {
	
	private String name;
	private String code;
	private String timezone; //East, Central, Mountain, West (To add in SQL table / JSON?)
	private Double home_rank;
	private Double away_rank;
	private Double wins;
	private Double losses;
	private Double proj_wins;
	private Double proj_losses;
	game schedule[];
	
	team(){
		name = "";
		code = "";
		timezone = "";
		home_rank = 0.0;
		away_rank = 0.0;
		wins = 0.0;
		losses = 0.0;
		proj_wins = 0.0;
		proj_losses = 0.0;
		schedule = new game[16];
		//createBlankSchedule();
	}
	
	team(String n, String c, Double h, Double a){
		name = n;
		code = c;
		home_rank = h;
		away_rank = a;
		wins = 0.0;
		losses = 0.0;
		proj_wins = 0.0;
		proj_losses = 0.0;
		schedule = new game[16];
		//createBlankSchedule();
		
	}
	
	void createBlankSchedule(){
		
		schedule = new game[16];
		for (int i=0;i<schedule.length;i++){
			schedule[i] = new game();
		}
		
	}

	void print(){
		System.out.println("Team: " + name + " " + code);
		print_proj_record();
		System.out.println("Home points: " + home_rank);
		System.out.println("Away points: " + away_rank);
		for (int i=0;i<schedule.length;i++){
			this.schedule[i].print();
		}
		System.out.println("\n");
	}
	
	void print_proj_record(){
		//System.out.println("Team: " + name + " " + code);
		System.out.println("Projected Record: " + proj_wins+ "-" +proj_losses);
		//System.out.println("\n");
	}
	
	Boolean checkEmpty(){
		Boolean x = false;
		
		if(this.name.equals("")){
			x = true;
		}
		
		return x;
	}
	
	Boolean getTeam(String s){
	
		Boolean resp = false;
		
		
		if(s.equals(this.code)){
			resp = true;
		}
				
		return resp;
	}
	
	Boolean getTeam(team t){
		
		Boolean resp = false;
		
		
		if(t.getCode().equals(this.code)){
			resp = true;
		}
				
		return resp;
	}
	
	public void addGame(game g){
		
		Boolean notFound = true;
		int i =0;
		while(notFound){
			
			if (schedule[i]!=null){
				//Not the right game
				i++;
			}else{
				schedule[i] = g;
				notFound = false;
			}
			
		}
		
		checkFull();
		/*
		//Find spot in schedule
		for(int i=0;i<schedule.length;i++){
			//Check schedule[i] empty or not
			if(schedule[i] != null){
				if(schedule[i].checkEmpty()){
					//Check order of weeks
					if (g.getWeek() > schedule[i].getWeek()){
						//Continue
					}else if (g.getWeek() == schedule[i].getWeek()){
						//Problem - same game?
					}else{
						//Order problem
					}
				}else{
					schedule[i] = g;
					checkFull();
				}
			}else{
				schedule[i] = g;
				checkFull();
				break;
			}
		}
		*/
		
	}
	
	public void checkFull(){
		
		int count = 0;
		for(int i=0;i<schedule.length;i++){
			//Check schedule[i] empty or not
			if(schedule[i] != null){
				if(!schedule[i].checkEmpty()){
					count++;
				}else{
	
				}
			}else{
				break;
			}
		}
		
		if (count == schedule.length){
			getRecord();
		}
	}
	
	public void getRecord(){
		for(int i=0;i<schedule.length;i++){
			if(this.getCode().equals(schedule[i].getAway().getCode())){
				//If away
				if(schedule[i].getLine() > 0){
					this.proj_losses++;
				}else if (schedule[i].getLine() == 0){
					this.proj_wins = this.proj_wins + 0.5;
					this.proj_losses = this.proj_losses + 0.5;
				}else{
					this.proj_wins++;
				}
					
			}else{
				//If home
				if(schedule[i].getLine() > 0){
					this.proj_wins++;
					
				}else if (schedule[i].getLine() == 0){
					this.proj_wins = this.proj_wins + 0.5;
					this.proj_losses = this.proj_losses + 0.5;
				}else{
					this.proj_losses++;
				}
			}
		}
		
		if(this.proj_losses + this.proj_wins != 16){
			System.out.println("Problem");
		}else{
			//this.print_proj_record();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getHome_rank() {
		return home_rank;
	}

	public void setHome_rank(Double home_rank) {
		this.home_rank = home_rank;
	}

	public Double getAway_rank() {
		return away_rank;
	}

	public void setAway_rank(Double away_rank) {
		this.away_rank = away_rank;
	}
	
}
