package MainAssignment;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class baseClass {

    public static ExtentReports extentReports;
    public static Logger log;
    public static String baseUri="https://api-nodejs-todolist.herokuapp.com";

    static {
        extentReports=new ExtentReports();
        extentReports.attachReporter(new ExtentHtmlReporter("extent.html"));
        log=LogManager.getLogger(baseClass.class.getName());
    }
}
