package MainAssignment;

import MainAssignment.activities.loginUser;
import MainAssignment.activities.registerUserClass;
import MainAssignment.activities.taskActivities;
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

        registerUserClass registerUserClass=new registerUserClass(baseClass.log,baseClass.baseUri);
        userClass user= readingFromExcel.registerUser();

        registerUserClass.registerUser(user);
    }
    @Test(priority = 2)
    void login() throws IOException {

        taskActivities taskactivities=new taskActivities(baseClass.baseUri,baseClass.log);
        taskactivities.login(readingFromExcel.registerUser());
        taskactivities.setup();

        taskactivities.addAllTasks();
        taskactivities.numberOfTasksShown(5);
        taskactivities.validateAllTasksInfo();

    }
    @Test(priority = 3)
    public void addAllTasks() throws IOException {

        taskActivities taskactivities=new taskActivities(baseClass.baseUri,baseClass.log);

    }


}
