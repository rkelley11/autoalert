package dealsheet

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

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

import internal.GlobalVariable

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import general.*

public class UpperIcons extends WrappedSelenium{
	
	/*
	 * Areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def driver

	def openNewWindowObjName = TestObjectNames.UpperSection.Icons.OPEN_DEALSHEET_IN_NEW_WINDOW

	UpperIcons(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def verifyOpenInNewWindow(dealsheetInfo){

		def windowHandles, numberOfWindowsBeforeClicking, numberOfWindowsAfterClicking, currentUrl, correctUrl, titleFound

		def by = By.cssSelector("[title=\"" + dealsheetInfo["customerName"] + "\"]")

		def expectedUrl = dealsheetInfo["newWindowLink"]

		windowHandles = this.driver.getWindowHandles()

		numberOfWindowsBeforeClicking = windowHandles.size()

		KeywordUtil.logInfo("Number of window handles found BEFORE clicking Open in New Window: " + numberOfWindowsBeforeClicking as String)

		click(returnTestObject(openNewWindowObjName))

		windowHandles = this.driver.getWindowHandles()

		numberOfWindowsAfterClicking = windowHandles.size()

		KeywordUtil.logInfo("Number of window handles found AFTER clicking Open in New Window: " + numberOfWindowsAfterClicking as String)

		this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 1])

		currentUrl = this.driver.getCurrentUrl()

		correctUrl = currentUrl == expectedUrl

		KeywordUtil.logInfo("Current Url : " + currentUrl)

		KeywordUtil.logInfo("Expected Url: " + expectedUrl)

		KeywordUtil.logInfo("Url's match : " + (correctUrl as String))

		titleFound = (findElements(by)).size() > 0

		KeywordUtil.logInfo("Title of '" + dealsheetInfo["customerName"] + "' found: " + (titleFound as String))

		this.driver.close()

		this.driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 2])

		correctUrl && titleFound
	}

	def getEntityId(){

		def testObject = returnTestObject(openNewWindowObjName)

		def link = WebUI.getAttribute(testObject, "href")

		def entityStart = link.lastIndexOf('/') + 1

		def entityId = link.substring(entityStart, link.size())

		entityId
	}
}
