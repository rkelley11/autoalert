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

import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

import groovy.time.*

public class ServiceDriveOffers extends WrappedSelenium{

	def driver

	def printOfferTitleObjName = TestObjectNames.ServiceDriveOffers.PRINT_OFFER_TITLE

	ServiceDriveOffers(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def clickPrintOffer(String fullName){

		def successful = false

		def allCells = getAllCells(fullName)

		if(allCells){

			def printOfferLinks = findElements(By.cssSelector("a"), allCells[9], 1)

			if(printOfferLinks){

				click(printOfferLinks[0])

				successful = true
			}
		}

		successful
	}

	@Keyword
	def clickDealsheetWithPrintOffer(String fullName){

		def successful = false

		def allCells = getAllCells(fullName)

		if(allCells){

			click(allCells[0])

			successful = true
		}

		successful
	}

	def getAllCells(String fullName){

		def message, person, fullNameLowerCase, allCells

		def successful = false

		person = fullName ? fullName : "First Available Print Offer"

		KeywordUtil.logInfo("Print offer to potentially click: " + person)

		fullNameLowerCase = fullName ? fullName.toLowerCase() : ""

		def allRows = returnAllRows()

		for (int i = 0 ; i < allRows.size() ; i++){

			allCells = findElements(By.cssSelector("td"), allRows[i])

			def fullNameFound = getText(allCells[1]).trim()

			KeywordUtil.logInfo("Full name found: " + fullNameFound as String)

			if(fullNameLowerCase == fullNameFound.toLowerCase() || fullName == null || fullName == ""){

				def printOfferLinks = findElements(By.cssSelector("a"), allCells[9], 1)

				if(printOfferLinks){

					successful = true

					break
				}
				else{

					if(fullNameLowerCase == fullNameFound.toLowerCase()){

						break
					}
				}
			}
		}

		message = successful ? "Found" : "Not Found"

		KeywordUtil.logInfo("All cells of a valid print offer row returned: " + message)

		allCells
	}

	//		

	@Keyword
	def cancelPrintDialog(){
		
		def logHeader

		try{
			
			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			JavascriptExecutor executor = (JavascriptExecutor)this.driver
	
			sleep(3000)
	
			def windowHandles = this.driver.getWindowHandles()
	
			KeywordUtil.logInfo(logHeader + "Number of window handles: " + windowHandles.size() as String)
	
			if (!windowHandles.isEmpty()) {
	
				this.driver.switchTo().window(windowHandles[windowHandles.size() - 1])
	
				executor.executeScript("document.querySelector('body > print-preview-app').shadowRoot.querySelector('#sidebar > print-preview-header').shadowRoot.querySelector('#button-strip > paper-button.cancel-button').click()")

				this.driver.switchTo().window(windowHandles[0])
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}
	}

	def returnAllRows(){

		def byRows = By.cssSelector("table tbody tr")

		def allRows = findElements(byRows)

		KeywordUtil.logInfo("Total rows found: " + allRows.size() as String)

		allRows
	}
}