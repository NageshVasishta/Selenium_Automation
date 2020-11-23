package resources;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utilities.Log;

public class ListenerTest implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		Log.startTestCase(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info("The name of the testcase passed is :" + result.getName());
		Log.endTestCase(result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.error("The name of the testcase Failed is :" + result.getName());
		Log.endTestCase(result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.warn("The name of the testcase Skipped is : " + result.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
