package MainAssignment.activities;

import MainAssignment.registrationUtils.readingFromExcel;
import org.json.JSONArray;
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
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TaskActivities extends LoginUser {

   private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;
    //list to contain all the tasks
   private List<String>allTasks;

    public TaskActivities(String url, Logger logger) {
        super(url, logger);
    }


    public void setup(){
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(url).addHeader("Content-Type","application/json")
                .addHeader("Authorization","Bearer "+token);

        System.out.println("token is "+token);
        requestSpecification= RestAssured.with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecification=responseSpecBuilder.build();


    }
    //copying all the list into this list
    public void extractAllTasks() throws IOException {
        allTasks= readingFromExcel.readAllTasksFromExcel();
    }

    //method to add a particular task

    public void addOnetask(JSONObject object){
        logger.info("task being added",object);

        Response response=given().spec(requestSpecification).body(object.toString()).when().post("/task")
                .then().extract().response();

        System.out.println("response code inside addonetask is "+response.statusCode());
        assert response.statusCode()==201;

        logger.info("task added");
    }

    //now loop over the list and add task one by one
    public void addAllTasks() throws IOException {
        extractAllTasks();

        //looping over the list of type string
        for(String task:allTasks){
            addOnetask(new JSONObject().put("description",task));
        }

        logger.info("all tasks added successfully");
    }

    //validating number of tasks shown when page limit is entered

    public void numberOfTasksShown(int pageLimit){
        Response response=given().spec(requestSpecification).param("limit",pageLimit).when().get("/task").then().spec(responseSpecification)
                .extract().response();

        JSONObject object=new JSONObject(response.asString());
        //to count the number of tasks shown when page limit is applied
        JSONArray jsonArray=new JSONArray(object.getJSONArray("data"));

        assert pageLimit==jsonArray.length();

    }

    //validate the owner and description of all the tasks shown

    public boolean  validateAllTasksInfo(){
        Response response=given().spec(requestSpecification).when().get("/task").then().spec(responseSpecification)
                .extract().response();

        JSONObject object=new JSONObject(response.asString());
        JSONArray jsonArray=new JSONArray(object.getJSONArray("data"));

        String owner=jsonArray.getJSONObject(0).getString("owner");
        for(int i=0;i<jsonArray.length();i++){
            JSONObject object1=jsonArray.getJSONObject(i);
            if(!allTasks.get(i).equals(object1.getString("description")) || !owner.equals(object1.getString("owner"))){
                return false;
            }
        }

        return true;



    }
    public void negAddTask(){

        File jsondata= new File("C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\resources\\negTaskData.json");

        Response response=given().spec(requestSpecification).body(jsondata).when().post("/task")
                .then().extract().response();

        System.out.println("response code inside addonetask is "+response.statusCode());
        assert response.statusCode()==400;

        if(response.then().extract().body().asString().contains("Task validation failed")){
            logger.warn("wrong task credenrials given. Task not added");
        }


    }
}
