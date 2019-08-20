package project.nflsim;

import java.io.FileReader;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
//import org.json.*;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


public class ParseJSON{
    
    public static void getJSONFromFile(String file, team teams[]){

        JsonParser JSONParser = new JsonParser();

        try (FileReader reader = new FileReader(file)){

            JsonElement obj = JSONParser.parse(reader);

            JsonArray array = (JsonArray) obj;

            if (array.size() > 33){

                //If more than 33 - then schedule
                for (int i=0;i<array.size();i++){

                    JsonElement t = array.get(i);
                    JsonObject te = (JsonObject) ((JsonObject) t).get("gameSchedules");

                    String[] g = {te.get("Date").getAsString(),te.get("AwayCode").getAsString(),te.get("HomeCode").getAsString(),te.get("Venue").getAsString()};

                    Connect.createGame(g,te.get("Week").getAsInt());
                }


            }else{

                //If less than 33 - then rankings

                for (int i=0;i<array.size();i++){

                

                    JsonElement t = array.get(i);
                    JsonObject te =  (JsonObject) t;

                    team tm = new team(te.get("team").getAsString(),te.get("code").getAsString(),te.get("team_comp_home").getAsDouble(),te.get("team_comp_away").getAsDouble());
                    teams[i] = tm;
                }
            }

            //team t = new team(result.getString("team"),result.getString("code"),result.getDouble("team_comp_home"),result.getDouble("team_comp_away"));


        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }


}