import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DriveInit {
    //IP машины, на которой установлен Google Chrome и запущен Selenium Server
    public static WebDriver driver;

    @BeforeClass
    public static void setUp() throws IOException {
        try {
            System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Ошибка связи с удаленным сервером: " + ". Возможно такой адрес не найден.\n" + e.getMessage());
        }
    }



    @AfterClass
    public static void tearDown(){
        driver.close();
        driver.quit();
    }
}
