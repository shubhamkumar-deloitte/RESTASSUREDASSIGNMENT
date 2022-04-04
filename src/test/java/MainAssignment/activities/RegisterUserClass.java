package MainAssignment.activities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.Logger;
import MainAssignment.registrationUtils.userClass;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class RegisterUserClass {
    Logger logger;
    String url;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    public RegisterUserClass(Logger logger, String url) {
        this.logger = logger;
        this.url = url;

        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(url).addHeader("Content-Type","application/json");
        requestSpecification= with().spec(requestSpecBuilder.build());


        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecification=responseSpecBuilder.build();
    }

    public boolean registerUser(userClass user){
        logger.debug(user.getJsonForRegistration());

        Response response=given().spec(requestSpecification).body(user.getJsonForRegistration().toString()).
                post("/user/register").then().spec(responseSpecification).extract().response();

        System.out.println("the response code for register user is "+ response.statusCode());

        if(response.statusCode()==200 || response.statusCode()==201){
            logger.info("registration successful");
            return true;
        }else if(response.statusCode()==400){
            logger.warn("user already exists");
           // Assert.assertTrue(false);


            return false;

        }else{
            logger.warn("registration failed ");
            return false;
        }


    }

}
