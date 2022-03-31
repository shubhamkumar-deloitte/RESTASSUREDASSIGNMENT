package minAssignment_1;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;



public class test1 {

    @Test(priority = 1)
    public void test_get_call(){

//        given().
//                baseUri("https://jsonplaceholder.typicode.com/posts").header("Content-Type","application/json").
//                when().
//                get("https://jsonplaceholder.typicode.com/posts").
//                then().
//                statusCode(200).body("userId[39]",equalTo(4)).body("title[39]",equalTo("enim quo cumque"));

        Response response=given().when().get("https://jsonplaceholder.typicode.com/posts").then().extract().response();
        JSONArray arr=new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            assert obj.getInt("userId") != 40 || (obj.getInt("id") == 4);
            assert obj.get("title") != null;
            assert obj.get("title") instanceof String;
        }

        assert (response.getStatusCode()==200);
        assert(response.getContentType().contains("json"));



    }
    @Test(priority = 2)
    public void test_put_call(){

        File JsonData=new File("C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\resources\\putData.json");
        Response response = given().baseUri("https://reqres.in/api").body(JsonData).header("Content-Type", "application/json").
                when().put("/users").then().extract().response();
        assert response.statusCode() == 200;
        JSONObject obj = new JSONObject(response.asString());
        assert obj.get("name").equals("Arun") && obj.get("job").equals("Manager");

        assert (response.getStatusCode()==200);
        assert(response.getContentType().contains("json"));




    }
}
