package MainAssignment;

import MainAssignment.activities.LoginUser;
import MainAssignment.activities.RegisterUserClass;
import MainAssignment.activities.TaskActivities;
import MainAssignment.registrationUtils.readingFromExcel;
import MainAssignment.registrationUtils.userClass;
import com.aventstack.extentreports.ExtentReports;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;

public class testClass {

    Logger logger=baseClass.log;
    ExtentReports extentReports=baseClass.extentReports;

    @Test(priority = 1)
    public void registerUser() throws IOException {

        RegisterUserClass registerUserClass=new RegisterUserClass(baseClass.log,baseClass.baseUri);
        userClass user= readingFromExcel.registerUser();

        registerUserClass.registerUser(user);
    }
    @Test(priority = 2)
    void login() throws IOException {

        TaskActivities taskactivities=new TaskActivities(baseClass.baseUri,baseClass.log);
        taskactivities.login(readingFromExcel.registerUser());
        taskactivities.setup();

        taskactivities.addAllTasks();
        taskactivities.numberOfTasksShown(5);
        taskactivities.validateAllTasksInfo();

    }
    @Test(priority = 3)
    public void negRegisterCase() throws IOException {

        RegisterUserClass registerUserClass=new RegisterUserClass(baseClass.log,baseClass.baseUri);
        userClass user= readingFromExcel.registerUser();

        registerUserClass.registerUser(user);

    }
    @Test(priority = 4)
    public void negLoginCase(){

        LoginUser loginUser=new LoginUser(baseClass.baseUri,baseClass.log);
        loginUser.negLogin();
    }


    @Test(priority = 5)
    public void negTaskCase() throws IOException {

        TaskActivities taskActivities=new TaskActivities(baseClass.baseUri,baseClass.log);
        taskActivities.login(readingFromExcel.registerUser());

        taskActivities.setup();
        taskActivities.negAddTask();

    }

}
