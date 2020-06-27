package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {

	
	public AddPlace addPlacePayload(String name, String language, String address)
	{
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setAddress(address);
		p.setLanguage(language);
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("https://rahulshettyacademy.com");
		p.setName(name);
		
		List<String> myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
		p.setTypes(myList); //this is expecting List<String>
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l); //expecting return type location so need to create Location object class also if passing l sub json will get injected
	    return p;
	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n \\  \"place_id\":\""+placeId+"\"\r\n}";  //after converting JSON to escape string pasting it here instead
	}
}
