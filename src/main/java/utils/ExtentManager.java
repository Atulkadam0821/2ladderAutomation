package utils;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
public class ExtentManager {
	private static ExtentReports extent;
    private static ExtentTest test;

    public static ExtentReports getinstance() {
    	if (extent == null) {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        spark.config().setReportName("2Ladders Test Report");
        spark.config().setDocumentTitle("2Ladders Automation Report");
        
        spark.config().setTimelineEnabled(true);
        spark.config().setEncoding("utf-8");
        spark.config().setCss(
            ".nav-logo { content: url('Two_Ladders_Blue_Logo_Header (1).png'); width: auto; height: 45px; padding: 5px 10px;vertical-align: middle;"
            + " }"
        );

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Atul Kadam");
    }
        return extent;
}
    

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }
}

