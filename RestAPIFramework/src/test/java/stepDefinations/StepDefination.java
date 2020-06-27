package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefination extends Utils{
	RequestSpecification res;
	ResponseSpecification resSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_Place_payload_with(String name, String language, String address) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
        
		res=given().spec(requestSpecification()) //since defined in utils class and also extending utils call just have to give method name
		.body(data.addPlacePayload(name, language, address));
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
	    // Write code here that turns the phrase above into concrete actions
		
		//Constructor will be called with value of resource which you pass
		APIResources resourceAPI=APIResources.valueOf(resource); //when u call valueOf(string) constructor will get executed
		System.out.println(resourceAPI.getResource());
		
		resSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
		response=res.when().post(resourceAPI.getResource()); //when there is only single line no need to give any braces
		else if(method.equalsIgnoreCase("GET"))
			response=res.when().get(resourceAPI.getResource());
	}
	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	   assertEquals(response.getStatusCode(), 200); 
		
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    // Write code here that turns the phrase above into concrete actions
	   
	   assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		place_id=getJsonPath(response, "place_id");  // defining String place_id globally so that can for other methods
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		//already defined so reusing it the same method
		user_calls_with_http_request(resource, "GET"); //this method will hit automatically get http method with the resource what you are passing
		//get the name of getplace resonse and make sure the name send from placeValidations file is matching
		String actualName=getJsonPath(response, "name");
		//comparing name what we got exactly same or not
		assertEquals(actualName, expectedName);
	}
	
	@Given("Delete place payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    //now have to build request spec which is res variable same as add Place payload method
		
		given().spec(requestSpecification()) 
		//here does not make sense to use pojo classes here in body only key value pair..suggest to go when you see something complex like nested json, arrays
		.body(data.deletePlacePayload(place_id));
	}


}
