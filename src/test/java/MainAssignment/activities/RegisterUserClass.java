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

public class registerUserClass {
    Logger logger;
    String url;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    public registerUserClass(Logger logger, String url) {
        this.logger = logger;
        this.url = url;

        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(url).addHeader("Content-Type","application/json");
        requestSpecification= RestAssured.with().spec(requestSpecBuilder.build());


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
        }else{
            logger.warn("registration failed ");
            return false;
        }

    }

}
