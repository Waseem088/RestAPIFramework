package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")  //before running deletePlace scenario have to execute the method writen below 
	public void beforeScenario() throws IOException
	{
	//write a code that will give you place_id
	//execute this code only when place_id is null
	
	StepDefination m = new StepDefination();
	//need one placid not worried about inputs you take
	if(StepDefination.place_id==null)
	{
	m.add_Place_payload_with( "Bangalore", "French", "Asia");
	m.user_calls_with_http_request("AddPlaceAPI", "POST");
	//need not check status code is 200 or not here goal is just to get placeid
	m.verify_place_id_created_maps_to_using("Bangalore", "getPlaceAPI");  //this method will store the placeid
	
	}
 }
	
	
	

}
