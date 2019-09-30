package opportunities

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

import general.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*
import java.time.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class LiveSupport extends WrappedSelenium{

	def linkObjName = TestObjectNames.LiveSupport.LINK

	def startChatButtonObjName = TestObjectNames.LiveSupport.START_CHAT_BUTTON

	@Keyword
	def shouldBeAvailable(){

		def shouldBeAvailable

		def closingHour = 16

		def hour = LocalDateTime.now().getHour()

		shouldBeAvailable = hour <= closingHour

		KeywordUtil.logInfo("Current hour: " + hour as String)

		KeywordUtil.logInfo("Live Support SHOULD" + (shouldBeAvailable ? " " : " NOT ") + "be available")

		shouldBeAvailable
	}

	@Keyword
	def doesLiveSupportOpenProperly(TestObject testObject){

		def numberOfWindowsBeforeClicking, numberOfWindowsAfterClicking, currentTitle, titlesMatch, isStartChatButtonVisible, buttons

		def expectedTitle = "WhosOn Chat: Start Chat Session"

		def startChatButton = returnTestObject(startChatButtonObjName)

		def driver = DriverFactory.getWebDriver()

		def windowHandles = driver.getWindowHandles()

		numberOfWindowsBeforeClicking = windowHandles.size()

		KeywordUtil.logInfo("Number of window handles found BEFORE clicking Live Support: " + numberOfWindowsBeforeClicking as String)

		click(testObject)

		windowHandles = driver.getWindowHandles()

		numberOfWindowsAfterClicking = windowHandles.size()

		KeywordUtil.logInfo("Number of window handles found AFTER clicking Live Support: " + numberOfWindowsAfterClicking as String)

		driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 1])

		KeywordUtil.logInfo("Switched to new window")

		buttons = findElements(By.cssSelector("#preChatbtn"), 10)

		isStartChatButtonVisible = buttons ? true : false

		KeywordUtil.logInfo("Start chat button visible: " + isStartChatButtonVisible as String)

		if(isStartChatButtonVisible){

			currentTitle = driver.getTitle()

			titlesMatch = currentTitle == expectedTitle

			KeywordUtil.logInfo("Current window title:\t" + currentTitle)

			KeywordUtil.logInfo("Expected window title:\t" + expectedTitle)

			KeywordUtil.logInfo("Current & Expected window titles match: " + titlesMatch as String)
		}

		driver.close() //Should close the Live Support window only

		driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 2]) //Switching back to the original driver browser

		titlesMatch && (numberOfWindowsAfterClicking > numberOfWindowsBeforeClicking) && isStartChatButtonVisible
	}
}
