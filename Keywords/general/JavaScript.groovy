package general

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class JavaScript extends WrappedSelenium{

	def driver

	def JavaScript(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def imageDisplays(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def imagePath = testObject.getImagePath()

		KeywordUtil.logInfo(logHeader + "Image path: " + imagePath)

		def loaded = false

		def element = findElement(testObject)

		def result = ((JavascriptExecutor) this.driver).executeScript(
				"return arguments[0].complete && "+
				"typeof arguments[0].naturalWidth != \"undefined\" && "+
				"arguments[0].naturalWidth > 0", element)

		if (result instanceof Boolean) {
			loaded = (Boolean) result
		}

		KeywordUtil.logInfo(logHeader + "The image DOES" + (loaded ? " " : " NOT ") + "display")

		loaded
	}

	def execute(TestObject testObject, String javaScript){

		def logHeader, result

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def element = findElement(testObject)

			if(element){

				result = execute(element, javaScript)
			}
			else{

				throw new Exception("Unable to convert TestObject to WebElement")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		result
	}

	def execute(WebElement element, String javaScript){

		def logHeader, result

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to execute javascript")

			result = ((JavascriptExecutor) this.driver).executeScript(javaScript, element)

			KeywordUtil.logInfo(logHeader + "Result: " + result as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		result
	}

	def click(TestObject testObject){

		def logHeader, result

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to click a Test Object using javascript")

			result = execute(testObject, "arguments[0].click();")

			KeywordUtil.logInfo(logHeader + "Result: " + result as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		result
	}
}
