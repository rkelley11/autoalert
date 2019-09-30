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

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import groovy.time.*

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import general.*
import opportunities.alertdesk.TestObjectNames as TestObjectNamesOppsAlertDesk

public class PrintOffers extends WrappedSelenium {

	def driver

	def printOfferTitleObjName = TestObjectNamesOppsAlertDesk.ServiceDriveOffers.PRINT_OFFER_TITLE

	PrintOffers(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def didPrintOfferOpen(){

		KeywordUtil.logInfo("Checking to see if the print offer has opened")

		def errorMessage = 'Offer Statement could not be generated at this time. Please try again later.'

		def printOfferTitleAppears, message

		def printTitleTestObject = returnTestObject(printOfferTitleObjName)

		def toastMessageBy = By.cssSelector(".toast-warning .toast-message .ng-star-inserted")

		def waiting = true

		def timeExpired = false

		def timeoutPeriod = new TimeDuration(0,0,30,0)

		def timeStart = new Date()

		while(waiting && !timeExpired){

			printOfferTitleAppears = waitForElementVisible(printTitleTestObject, 1)

			KeywordUtil.logInfo("Print offer title is found: " + printOfferTitleAppears as String)

			if(!printOfferTitleAppears){

				def toastMessages = findElements(toastMessageBy, 1)

				KeywordUtil.logInfo("Toast message(s) found: " + (toastMessages.size() > 0) as String)

				message = toastMessages ? getText(toastMessages[0]) : null

				if(message) KeywordUtil.logInfo("Toast message: " + message)

				timeExpired = isTimeExpired(timeStart, timeoutPeriod)
			}

			waiting = !printOfferTitleAppears && message != errorMessage

			KeywordUtil.logInfo("Still waiting for print offer title or error message: " + waiting as String)
		}

		if(timeExpired) KeywordUtil.logInfo("Timeout period of " + (timeoutPeriod as String) + " has expired.")

		KeywordUtil.logInfo("Print offer appeared: " + printOfferTitleAppears as String)

		printOfferTitleAppears
	}
}
