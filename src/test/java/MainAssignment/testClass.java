package MainAssignment;

import MainAssignment.activities.LoginUser;
import MainAssignment.activities.RegisterUserClass;
import MainAssignment.activities.TaskActivities;
import MainAssignment.registrationUtils.readingFromExcel;
import MainAssignment.registrationUtils.userClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

public class testClass {

    Logger logger=baseClass.log;
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;


    @BeforeSuite
    public void setExtents(){

        // start reporters
        htmlReporter = new ExtentHtmlReporter("extent.html");

        // create ExtentReports and attach reporter(s)
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    @Test(priority = 1)
    public void registerUser() throws IOException {

        ExtentTest test = extent.createTest("registerUser", "it will register the user");
        RegisterUserClass registerUserClass=new RegisterUserClass(baseClass.log,baseClass.baseUri);
        userClass user= readingFromExcel.registerUser();
        test.info("reading data from the excel and passing to the function to register the user");

        //test.info(test.getModel().getName());
        registerUserClass.registerUser(user);
        test.pass("user registered successfully");
        //extent.flush();
    }
    @Test(priority = 2)
    void login() throws IOException {

        ExtentTest test1 = extent.createTest("loginUser", "it will login the user and save the tokens and add tasks usingthose tokens");
        TaskActivities taskactivities=new TaskActivities(baseClass.baseUri,baseClass.log);
        taskactivities.login(readingFromExcel.registerUser());
        test1.info("user loggedin");
        taskactivities.setup();

        taskactivities.addAllTasks();
        test1.info("all tasks added successfully");
        taskactivities.numberOfTasksShown(5);
        test1.info("page limit verified");
        taskactivities.validateAllTasksInfo();
        test1.info("info of all tasks verified");
        test1.pass("test finished");

    }
    @Test(priority = 3)
    public void negRegisterCase() throws IOException {

        ExtentTest test3 = extent.createTest("negregisterUser", "it will try to register user with existing credentials");

        RegisterUserClass registerUserClass=new RegisterUserClass(baseClass.log,baseClass.baseUri);
        userClass user= readingFromExcel.registerUser();
        test3.info("passed existing credentials");

        registerUserClass.registerUser(user);
        test3.pass("test finished");

    }
    @Test(priority = 4)
    public void negLoginCase(){

        ExtentTest test4 = extent.createTest("negLoginUser", "it will try to login the user with invalid credentials");

        LoginUser loginUser=new LoginUser(baseClass.baseUri,baseClass.log);

        loginUser.negLogin();
        test4.info("passed invalid credentials and error verified");
        test4.pass("test passed");
    }


    @Test(priority = 5)
    public void negTaskCase() throws IOException {

        ExtentTest test5 = extent.createTest("negTaskcase", "it will try to add a task with wrong key name");
        TaskActivities taskActivities=new TaskActivities(baseClass.baseUri,baseClass.log);
        taskActivities.login(readingFromExcel.registerUser());
        test5.info("loggedIn to get the token");

        taskActivities.setup();
        taskActivities.negAddTask();
        test5.info("tried to add a task with wrong key name and failed and error verified");
        test5.pass("test finished");

    }
    @AfterSuite
    public void tearDown() throws InterruptedException {


        Thread.sleep(1500);
        extent.flush();

    }

}
