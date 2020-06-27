Feature: Validating place API's
@AddPlace @Regression
Scenario Outline: verify if Place is being successfully added AddPlaceAPI
   Given Add Place payload with "<name>" "<language>" "<address>"
   When user calls "AddPlaceAPI" with "POST" http request
   Then the API call got success with status code 200
   And "status" in response body is "OK"
   And "scope" in response body is "APP"      
   And verify place_id created maps to "<name>" using "getPlaceAPI"

Examples:
   |name    | language | address          |
   |AAhouse | English  |world cross center|  
 # |BBhouse | English   |sea cross center |

@DeletePlace @Regression
Scenario: verify if Delete Place functionality is working
   Given Delete place payload 
   When user calls "deletePlaceAPI" with "POST" http request
   Then the API call got success with status code 200
   And "status" in response body is "OK"   