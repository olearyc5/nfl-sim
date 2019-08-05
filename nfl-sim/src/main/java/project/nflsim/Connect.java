package project.nflsim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class Connect {
	
	static Connection conn = null;
	static String dbName = "Sports";
    static String serverport="1433";
    static String url = "jdbc:sqlserver://LAPTOP-GHB06ES2\\SQLEXPRESS:"+serverport+";integratedSecurity=true;databaseName="+dbName+"";
    static Statement stmt = null;
    static ResultSet result = null;
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static team teams[] = new team[32];
    static game games[] = new game[256];
	
	public static void makeConnection(){
		
		try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            result = null;
            result = stmt.executeQuery("select * FROM dbo.NFLRankings"); //Change from * to maximise speed and security


            int count = 0;
        	
    		while (result.next()) {
    		 	
    			team t = new team(result.getString("team"),result.getString("code"),result.getDouble("team_comp_home"),result.getDouble("team_comp_away"));
    			//t.print();
    			teams[count] = t;
    			count++;
    		}

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();//access .json file
        }
		
	}
	
	public static void getSchedule(){
	
		
		try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            result = null;
            result = stmt.executeQuery("select * FROM dbo.NFLSchedule2019");


            int count = 0;
        	
    		while (result.next()) {
    		 	
    			String[] g = {result.getString("Gamedate"),result.getString("AwayCode"),result.getString("HomeCode"),result.getString("GameLocation")};
    			   			
    			int h = getTeam(g[2]);
    			team home = teams[h];
    			int a = getTeam(g[1]);
    			team away = teams[a];
    			
    			if (!(home.checkEmpty()  || away.checkEmpty())){
    				
    				Boolean n = false;
    				if((g[3].contains("London"))||(g[3].contains("Mexico"))){
    					n = true;
    				}
    				
    				game gd = new game(result.getInt("Gameweek"), home,away,n);
    				teams[h].addGame(gd);
    				teams[a].addGame(gd);
    				games[count]= gd;
    				count++;
    			}else{
    				System.out.println("Something went wrong.");
    			}

    		}

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	
	public static int getTeam(String s){
		
		int t = 0;
		
		int c = 0;
		Boolean found = false;

		while(!found){

			if(teams[c].getTeam(s)){
				
				t = c;
				found = true;
			}
			
			c++;
			
			if(c == 32){
				found = true;
			}
		}
		
		return t;
	}
	
	public static void getweekSchedule(int j){
		
		 for (int i=0;i<games.length;i++){
			 if(games[i].getWeek() == j ){
	        	games[i].print();
			 }
		 }
		
	}

	
	public static void main(String[] args) {
		
		makeConnection();
		getSchedule();
		
		/*
		for(int j=0;j<teams[13].schedule.length;j++){
			teams[13].schedule[j].print();
		}
		*/
		for (int k=0;k<teams.length;k++){
			teams[k].print();
		}
		
		//getweekSchedule(11);
		/*
        for (int i=0;i<games.length;i++){
        	games[i].print();
        }
        */
        
    }

}