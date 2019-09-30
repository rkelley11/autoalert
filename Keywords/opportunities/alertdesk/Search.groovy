package opportunities.alertdesk

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

import groovy.time.*

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class Search extends WrappedSelenium{

	@Keyword
	def returnTotalFoundFromSearch(){

		// This code is almost exactly like the same method in Opportunities  Need to refactor.

		def bottomSearchNumberText = WebUI.getText(returnTestObject('Total Number in Search Bottom Text'))

		KeywordUtil.logInfo("Search Number Text (Full): " + bottomSearchNumberText + ", Length: " + bottomSearchNumberText.size() as String)

		def trimmedNumberText = bottomSearchNumberText.trim().replaceAll("\u00A0", "");

		KeywordUtil.logInfo("Search Number Text (Trimmed): " + trimmedNumberText + ", Length: " + trimmedNumberText.size() as String)

		def subTrimmedNumberText = trimmedNumberText.substring(trimmedNumberText.lastIndexOf(" ") + 1)

		KeywordUtil.logInfo("Search Number Text (SubTrimmed): " + subTrimmedNumberText + ", Length: " + subTrimmedNumberText.size() as String)

		subTrimmedNumberText as Integer
	}

	def waitForSearchingOverlayToFinish(){

		def overlay, overlayClasses

		def overlayFinished = false

		def by = By.cssSelector(".search-container .cg-busy-animation")

		sleep(3000) // When opening there are several overlays.  Could refactor in the future to avoid sleep, but using to wait for these to be done.

		def timeExpired

		def timeoutPeriod = new TimeDuration(0,0,10,0)

		def timeStart = new Date()

		while(!overlayFinished && !timeExpired){

			//while(!overlayFinished && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			overlay = findElements(by)

			KeywordUtil.logInfo("Number of overlays found: " + overlay.size() as String)

			if(overlay){

				overlayClasses = getAttribute(overlay[0], "class")

				KeywordUtil.logInfo("Overlay classes: " + overlayClasses as String)

				overlayFinished = overlayClasses.contains('ng-hide') ? true : false
			}

			timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		overlayFinished
	}
}
