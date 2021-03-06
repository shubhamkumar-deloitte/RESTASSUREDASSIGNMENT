package MainAssignment.activities;

import MainAssignment.registrationUtils.userClass;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class LoginUser {
    public String url;
    public String token;

    public Logger logger;

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    public LoginUser(String url, Logger logger) {
        this.url = url;
        this.logger = logger;


        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(url).addHeader("Content-Type","application/json");
        requestSpecification= with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecification=responseSpecBuilder.build();
    }

    public void login(userClass user){
        logger.debug("login json data",user.getJsonForLogin());

        Response response=given().spec(requestSpecification).body(user.getJsonForLogin().toString()).post("/user/login").
                then().spec(responseSpecification).extract().response();

        logger.debug("login status code",response.statusCode());
        System.out.println("login status code is "+ response.statusCode());
        assert response.statusCode()==200;


        //validating credentials
        JSONObject object=new JSONObject(response.asString());
        //System.out.println("inside login and token is "+object.get("token"));
        token=object.getString("token");

        //System.out.println("inside login and token is "+token);
        //data show after login
//        {
//            "user": {
//            "age": 25,
//                    "_id": "621e5b22be372e00177ebb41",
//                    "name": "Muhammad Nur Alikhan",
//                    "email": "muh.nurali43@gmail.com",
//                    "createdAt": "2022-03-01T17:42:58.090Z",
//                    "updatedAt": "2022-04-03T19:03:42.915Z",
//                    "__v": 1136
//        },
//            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MjFlNWIyMmJlMzcyZTAwMTc3ZWJiNDEiLCJpYXQiOjE2NDkwMTI2MjJ9.Vx1Skhh3QbUDQQQ8wiabiXUQ3sY8gLRqCWqlDyxz7C0"
//        }

        object=object.getJSONObject("user");
        System.out.println(object.get("name"));
        System.out.println(user.name);
        System.out.println(object.get("email"));
        System.out.println(user.email);
        assert object.getString("name").equals(user.name) && object.getString("email").equals(user.email.toLowerCase());

    }
    public void negLogin(){

        File jsonData=new File("C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\resources\\negLoginData.json");
        Response response=given().spec(requestSpecification).body(jsonData).post("/user/login").
                then().spec(responseSpecification).extract().response();
        System.out.println("login status code is "+ response.statusCode());
        System.out.println("response is "+response.then().extract().body().asString());
        if(response.then().extract().body().asString().equals("Unable to login")){
            logger.warn("invalid login credentials provided");
            assert response.statusCode()==400;
        }
    }
}
