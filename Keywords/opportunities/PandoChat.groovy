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

import groovy.time.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class PandoChat extends WrappedSelenium{

	def pandoChatIconObjName = TestObjectNames.PandoChat.ICON

	def pandoInboxLogoObjName = TestObjectNames.PandoChat.LOGO

	def expectedPandoChatWindowTitle = TestObjectNames.PandoChat.TITLE

	@Keyword
	def verifyPandoChatOpens(){

		def windowHandles, numberOfWindowsAfterClicking, windowTitle, goodTitle, inboxLogoFound, currentWindowHandle

		def pandoChatIcon = returnTestObject(pandoChatIconObjName)

		def driver = DriverFactory.getWebDriver()

		click(pandoChatIcon)

		windowHandles = driver.getWindowHandles()

		currentWindowHandle = windowHandles[0]

		numberOfWindowsAfterClicking = windowHandles.size()

		driver.switchTo().window(windowHandles[numberOfWindowsAfterClicking - 1])

		windowTitle = driver.getTitle()

		goodTitle = windowTitle == expectedPandoChatWindowTitle

		KeywordUtil.logInfo("Pando Chat current title:\t" + windowTitle)

		KeywordUtil.logInfo("Pando Chat expected title:\t" + expectedPandoChatWindowTitle)

		KeywordUtil.logInfo("Pando Chat current & expected titles match: " + goodTitle as String)

		inboxLogoFound = waitForElementVisible(returnTestObject(pandoInboxLogoObjName), 30)

		KeywordUtil.logInfo("Pando Inbox logo WAS" + (inboxLogoFound ? "" : " NOT") + " found")

		driver.close()

		driver.switchTo().window(currentWindowHandle)

		goodTitle && inboxLogoFound
	}
}
