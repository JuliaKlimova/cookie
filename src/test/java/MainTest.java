import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.*;
import java.util.StringTokenizer;

public class MainTest extends DriveInit {
    @Title("Class Title : Login with username and password")
    @Test
    public void case00() throws Exception {
        driver.get("http://way2automation.com/");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/header/div[2]/div/div[2]/div/a[1]")).click();
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();
        // Switch to new window opened
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        login("jul.klimova2011@yandex.ru", "19941111");

        writeCookiesToFile(driver);
        Thread.sleep(5000);
        Assert.assertTrue("Case 00 is failed.",
                driver.getCurrentUrl().contains("https://courses.way2automation.com/"));
        System.out.println("Case 00 is passed.");

    }

    @Title("Class Title : Login using cookie")
    @Test
    public void case01() throws Exception {

        driver.get("http://way2automation.com/");


        driver.get("https://courses.way2automation.com/");

        readCookiesFromFile(driver);

        driver.get("https://courses.way2automation.com/");

        Thread.sleep(5000);

        Assert.assertTrue("Case 01 is failed.",
                driver.findElement(By.xpath("//*[@id=\"navbar\"]/div/div/div/ul/li[4]/a/img")).isEnabled());
        System.out.println("Case 01 is passed.");
    }

    //Функция записи куки в файл
    public void writeCookiesToFile(WebDriver webDriver) {
        // create file named Cookies to store Login Information
        File file = new File("Cookies.data");
        try
        {
            // Delete old file if exists
            file.delete();
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for(Cookie ck : driver.manage().getCookies())
            {
                if (ck.getName().equals(""))
                    Bwrite.newLine();
                else {
                    Bwrite.write((ck.getName() + ";" + ck.getValue()));
                    Bwrite.newLine();
                }

            }
            Bwrite.close();
            fileWrite.close();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

}
    //Функция чтения куки из файла
    public void readCookiesFromFile(WebDriver webDriver) throws IOException {
        try {

            File file = new File("Cookies.data");
            FileReader fileReader = new FileReader(file);
            BufferedReader Buffreader = new BufferedReader(fileReader);
            String strline;
            while ((strline = Buffreader.readLine()) != null) {
                StringTokenizer token = new StringTokenizer(strline, ";");
                while (token.hasMoreTokens()) {
                    String  name = token.nextToken();
                    String value = token.nextToken();
                    Cookie ck = new Cookie(name, value);
                    System.out.println(ck);
                    webDriver.manage().addCookie(ck); // This will add the stored cookie to your current session
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void login (String login, String password)
    {
        driver.findElement(By.id("user_email")).sendKeys(login);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"new_user\"]/div[3]/input")).click();
    }
}
