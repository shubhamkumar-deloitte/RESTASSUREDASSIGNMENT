package miniAssignment_2;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.with;

public class test2 {

    RequestSpecification requestSpecification;
    RequestSpecification requestSpecification2;

    @BeforeClass
    public void setup() {
        // Creating an object of RequestSpecBuilder
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        RequestSpecBuilder reqBuilder2=new RequestSpecBuilder();
        // Setting Base URI
        reqBuilder.setBaseUri("https://jsonplaceholder.typicode.com").addHeader("Content-Type", "application/json");
        reqBuilder2.setBaseUri("https://reqres.in/api").addHeader("Content-Type","application/json");

        //creating object of
        requestSpecification = RestAssured.with().spec(reqBuilder.build());
        requestSpecification2= RestAssured.with().spec(reqBuilder2.build());

    }

    @Test(priority = 1)
    public void get_request() {

        Response response = requestSpecification.get("/posts");

        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            //System.out.println(arr.getJSONObject(i).get("id"));

            if (arr.getJSONObject(i).get("id").equals(40)) {
                assert (arr.getJSONObject(i).get("userId").equals(4));
            }
            assert (response.statusCode() == 200);
            assert (response.contentType().contains("json"));

        }
    }
    @Test(priority = 2)
    public void put_request(){
        File jsonData=new File("C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\resources\\putData.json");

        Response response=requestSpecification2.body(jsonData).put("/users");
        assert(response.getStatusCode()==200);
        assert(response.getContentType().contains("json"));

        JSONObject object=new JSONObject(response.asString());
       assert(object.get("name").equals("Arun") && object.get("job").equals("Manager"));
    }
}
