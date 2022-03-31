package miniAssignment_2;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.with;

public class test2 {

    RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {
        requestSpecification = with().baseUri("https://jsonplaceholder.typicode.com").
                header("Content-Type", "application/json");

    }

    @Test
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
}
