import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.*;
import java.util.Date;
import java.util.Set;
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
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        login("jul.klimova2011@yandex.ru","19941111");
        writeCookiesToFile(driver);
        Assert.assertTrue("Case 00 is failed.",
                driver.getCurrentUrl().contains("https://courses.way2automation.com/"));
        System.out.println("Case 00 is passed.");

    }

    @Title("Class Title : Login using cookie")
    @Test
    public void case01() throws Exception {
        driver.get("http://way2automation.com/");
        driver.findElement(By.xpath("//*[@id=\"wrapper\"]/header/div[2]/div/div[2]/div/a[1]")).click();
        // Store the current window handle
        String winHandleBefore = driver.getWindowHandle();
        // Switch to new window opened
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        driver.manage().addCookie(readCookiesFromFile());

        Assert.assertTrue("Case 01 is failed.",
                driver.getCurrentUrl().contains("https://courses.way2automation.com/"));
        System.out.println("Case 01 is passed.");
    }

    //Функция записи куки в файл
    public void writeCookiesToFile(WebDriver webDriver) {
        File file = new File("cookie.txt");
        try {
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Cookie cookie : webDriver.manage().getCookies()) {
                writer.write((cookie.getName() + ";" + cookie.getValue() + ";" +
                        cookie.getDomain() + ";" + cookie.getPath() + ";" + cookie.getExpiry() +
                        ";" + cookie.isSecure()));
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при записи куки - "+ e.getLocalizedMessage());
        }
    }
    //Функция чтения куки из файла
    public Cookie readCookiesFromFile() {
        Cookie cookie = new Cookie("","");
        try {
            File file = new File("cookie.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer str = new StringTokenizer(line, ";");
                while (str.hasMoreTokens()) {
                    String name = str.nextToken();
                    String value = str.nextToken();
                    String domain = str.nextToken();
                    String path = str.nextToken();
                    Date expiry = null;
                    String date= str.nextToken();
                    if (!(date).equals("null")) {
                        expiry = new Date(System.currentTimeMillis()*2);
                    }
                    boolean isSecure = new Boolean(str.nextToken()).booleanValue();
                    cookie = new Cookie(name, value, domain, path, expiry, isSecure);
                }
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при чтении куки - "+ ex.getLocalizedMessage());
        }
        return cookie;
    }

    public void login (String login, String password)
    {
        driver.findElement(By.id("user_email")).sendKeys(login);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"new_user\"]/div[3]/input")).click();
    }
}
