package e_commerce;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Project {
	public static void main(String[] args) {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/client");
		String productName = "ADIDAS ORIGINAL";
		driver.findElement(By.id("userEmail")).sendKeys("frnklnsv@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Rsa@2024");
		driver.findElement(By.id("login")).click();

		List<WebElement> products = driver.findElements(By.cssSelector(".offset-md-0"));

		products.stream().filter(product -> product.findElement(By.xpath("//div//b")).getText().equals(productName))
				.findFirst().orElse(null);

		driver.findElement(By.xpath("(//button[@class='btn w-10 rounded'])[2]")).click();
		WebDriverWait we = new WebDriverWait(driver, Duration.ofSeconds(5));
		we.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		we.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		List<WebElement> cartProduct = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean cartText = cartProduct.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(cartText);
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();

		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "india").build().perform();
		we.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ta-results"))));
		driver.findElement(By.xpath("(//span[@class='ng-star-inserted'])[2]")).click();

		driver.findElement(By.cssSelector(".action__submit")).click();

		String text = driver.findElement(By.xpath("//h1[contains(text(),'Thankyou for the order.')]")).getText();
		Assert.assertTrue(text.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		driver.close();

	}
}
