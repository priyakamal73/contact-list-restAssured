package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseClass {
    private static final Properties prop = new Properties();
    private static final String propertiesFilePath = "C:\\Users\\SANMUKA PRIYA\\eclipse-workspace\\Contact List Application APIs\\src\\test\\java\\utils\\config.properties";
    public static ExtentReports extent;

    public static ExtentTest test;
    static ExtentSparkReporter sparkReporter;

    @BeforeSuite
    public static void setup() {
        sparkReporter = new ExtentSparkReporter(prop.getProperty("report.file", "ExtentReport.html"));
        sparkReporter.config().setDocumentTitle(prop.getProperty("report.title", "Contact List Application Automation Report"));
        sparkReporter.config().setReportName(prop.getProperty("report.name", "Contact List Application Automation Report"));
        sparkReporter.config().setTheme(Theme.valueOf(prop.getProperty("report.theme", "DARK").toUpperCase()));

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        try (InputStream input = Files.newInputStream(Paths.get(propertiesFilePath))) {
            prop.load(input);
            RestAssured.baseURI = prop.getProperty("url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static Properties getProp() {
        return prop;
    }
    public static void updatePropertyFile(String key, String value) {
        try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
            prop.load(fis);
            try (FileOutputStream fos = new FileOutputStream(propertiesFilePath)) {
                prop.setProperty(key, value);
                prop.store(fos,"Updated properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update the properties file", e);
        }
    }

    @AfterSuite
    public void tearDown(){
        extent.flush();
    }
}