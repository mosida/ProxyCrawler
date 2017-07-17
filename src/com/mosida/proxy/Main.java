package com.mosida.proxy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;

public class Main {
    //http://freeproxylists.net/zh/
    //http://freeproxylists.net/zh/?c=&pt=&pr=HTTPS&a%5B%5D=0&a%5B%5D=1&a%5B%5D=2&u=50
    //http://www.cool-proxy.net

    static WebDriver driver;

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        // 设置语言
        options.addArguments("--lang=en");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver(capabilities);
        driver.get("http://freeproxylists.net/zh/");

        //System.out.println(getCellText(By.className("DataGrid"), "1.1"));
        int rowSize = getTableTrSize(By.className("DataGrid"));
        for (int i=0; i<rowSize; i++){
            try{
                System.out.println(getCellText(By.className("DataGrid"), i+".0"));
                System.out.println(getCellText(By.className("DataGrid"), i+".1"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static int getTableTrSize(By by){
        WebElement table = driver.findElement(by);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        System.out.println(rows.size());
        return rows.size();
    }


    public static String getCellText(By by, String tableCellAddress) {
        //得到table元素对象
        WebElement table = driver.findElement(by);
        //对所要查找的单元格位置字符串进行分解，得到其对应行、列。
        int index = tableCellAddress.trim().indexOf('.');
        int row =  Integer.parseInt(tableCellAddress.substring(0, index));
        int cell = Integer.parseInt(tableCellAddress.substring(index+1));
        //得到table表中所有行对象，并得到所要查询的行对象。
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement theRow = rows.get(row);
        //调用getCell方法得到对应的列对象，然后得到要查询的文本。
        String text = getCell(theRow, cell).getText();
        return text;
    }

    private static WebElement getCell(WebElement Row, int cell){
        List<WebElement> cells;
        WebElement target = null;
        //列里面有"<th>"、"<td>"两种标签，所以分开处理。
        if(Row.findElements(By.tagName("th")).size()>0){
            cells = Row.findElements(By.tagName("th"));
            target = cells.get(cell);
        }
        if(Row.findElements(By.tagName("td")).size()>0){
            cells = Row.findElements(By.tagName("td"));
            target = cells.get(cell);
        }
        return target;
    }
}
