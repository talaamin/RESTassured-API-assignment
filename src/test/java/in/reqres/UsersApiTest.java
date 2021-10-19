package in.reqres;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UsersApiTest {
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "https://reqres.in/api";

	}

	@Test
	public void createUserTest() {

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Tala");
		requestParams.put("job", "QA");
		Response response = RestAssured.given().when().body(requestParams.toJSONString()).post("/users").then()
				.statusCode(201).extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();
		String id = jsonPathEvaluator.getString("id");
		System.out.println("The returned id is " + id);
	}

	@Test
	public void getUserDataTest() {

		Response response = RestAssured.given().when().get("/users/7").then().statusCode(200)
				.contentType(ContentType.JSON).extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();
		String firstName = jsonPathEvaluator.getString("data.first_name");
		String lastName = jsonPathEvaluator.getString("data.last_name");
		System.out.println("The user with ID 7 is " + firstName + " " + lastName);

	}

	@Test
	public void nonExistingIdTest() {
		RestAssured.given().when().get("/users/1000").then().statusCode(404);

	}

}
