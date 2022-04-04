package MainAssignment;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class testNGListener implements ITestListener {

    Logger log = baseClass.log;


    @Override
    public void onTestStart(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " start");
        System.out.println("*** Test Suite " + result.getName() + " started ***");




    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(result.getMethod().getMethodName() + " success");
        System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");


    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.fatal(result.getMethod().getMethodName()+"failed");


    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

        baseClass.log.traceExit();

    }

}
