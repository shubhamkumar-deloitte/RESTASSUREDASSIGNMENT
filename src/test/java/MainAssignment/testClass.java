package MainAssignment;

import MainAssignment.activities.registerUserClass;
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
    void login(){


    }


}
