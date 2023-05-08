package com.beeran.spider.Utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankKeyword;
import io.opentelemetry.api.internal.StringUtils;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

public class Spider {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\driver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver= new ChromeDriver(options);
        driver.get("https://www.code-nav.cn/user/1626450481553383425/post");
        System.out.println("=---------------请求成功---------------=" );
        sleep(6000);
        // 登录编程导航
        WebElement Code = driver.findElement(By.id("captcha"));
        WebElement login = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/form/form/button"));
        Code.sendKeys("ztigcr");
        login.click();
        // 获取个人页所有文章信息，进而进行爬取
        ArrayList<String> Articles = new ArrayList<>();
        sleep(4000);
        List<WebElement> elements = driver.findElements(By.className("post-preview"));
        for (WebElement e: elements) {
            String href = e.getAttribute("href");
            if(StringUtils.isNullOrEmpty(href)) continue;
            Articles.add(href);
        }
        ArticleTagger articleTagger = new ArticleTagger();
        // 定义一个函数用来爬取文章， 使用Jsoup进行解析文章
        for (String art: Articles) {
            driver.get(art);
            sleep(5000);
            String content = null;
            String path = null;
            WebElement title = driver.findElement(By.className("ant-typography"));
            if (title == null){
                System.out.println("未找到标题");
            }else{
                path = title.getText() + ".md";
                System.out.println(path);
            }

            WebElement article = driver.findElement(By.className("markdown-body"));
            if (article == null) {
                System.out.println("未找到文章");
            }else{
                content = article.getText();
            }
            // 进行分词，提取关键词，并将关键词输出
            try {
                // 判断结果
                // TODO 识别关键词
                List<String> textRankKeyword = HanLP.extractKeyword(content,5);
                textRankKeyword.add("Java");
                articleTagger.addTags(path, textRankKeyword);
                if (articleTagger.getResultDict().isEmpty()){
                    HashMap<String, ArrayList<Object>> resultDict = articleTagger.getResultDict();
                    articleTagger.setResultDict(resultDict);
                }
            }catch (NullPointerException ne){
                System.out.println(ne);
            }

            // 保存文件
            try {
                path = "src/main/java/com/beeran/spider/files/" + path;
                Files.write(Paths.get(path), content.getBytes()); // 将字符串写入文件
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.println(articleTagger.getResultDict());
    }
}
