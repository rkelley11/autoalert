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

public class Common extends WrappedSelenium{

	def driver

	Common(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def returnNumberOfDataRows(){
		// Might be a better way of doing this

		def numberOfRowsShowing1, numberOfRowsShowing2, numberOfRowsShowing3, numberOfRowsShowing4

		def listOfNumbers = []

		def noRecordsFoundText

		def looking = true

		def by = By.cssSelector("#GridData tr.PagerDataRow")

		def byFindABuyer = By.cssSelector("#Find_a_Buyer[aria-hidden=\"false\"] #Inventory_Listing tr.ui-widget-content")

		def byNoRecordsFound = By.cssSelector(".ui-table-pager-row-odd")

		def byWorkSheet = By.cssSelector("#Work_Sheet[aria-hidden=\"false\"] table tr[data-term-group][style*=\"display: table-row\"]")

		def byCompare = By.cssSelector("#Compare[aria-hidden=\"false\"] #worksheet-options-details table.ui-widget-table tbody tr")

		this.driver.switchTo().frame("autoalertiframe")

		def timeExpired = false

		def timeoutPeriod = new TimeDuration(0,0,45,0) // some page have taken a long time to even start loading

		def timeStart = new Date()

		while(looking && !timeExpired){

			//while(looking && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			numberOfRowsShowing1 = findElements(by, 1).size() //Find a Car & Find Pre-Owned Opportunities tabs

			listOfNumbers.add(numberOfRowsShowing1)

			numberOfRowsShowing2 = numberOfRowsShowing1 == 0 ? findElements(byFindABuyer, 1).size() : 0 //Find a Buyer tab

			listOfNumbers.add(numberOfRowsShowing2)

			numberOfRowsShowing3 = (numberOfRowsShowing1 == 0 && numberOfRowsShowing2 == 0) ? findElements(byWorkSheet, 1).size() : 0 //F&I Manager/Work Sheet

			listOfNumbers.add(numberOfRowsShowing3)

			numberOfRowsShowing4 = (numberOfRowsShowing1 == 0 && numberOfRowsShowing2 == 0 && numberOfRowsShowing3 == 0) ? findElements(byCompare, 1).size() : 0 //F&I Manager/Compare

			listOfNumbers.add(numberOfRowsShowing4)

			looking = (numberOfRowsShowing1 == 0 && numberOfRowsShowing2 == 0 && numberOfRowsShowing3 == 0 && numberOfRowsShowing4 == 0)

			if(looking){

				def possibleNoRecordsFoundList = findElements(byNoRecordsFound, 1)

				if(possibleNoRecordsFoundList){

					noRecordsFoundText = getText(possibleNoRecordsFoundList[0])

					KeywordUtil.logInfo("Possible error message: " + noRecordsFoundText as String)

					looking = noRecordsFoundText == null ? true : !noRecordsFoundText.contains("No records found")
				}
			}

			if (looking) listOfNumbers.clear()

			timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		KeywordUtil.logInfo("The numbers list: " + listOfNumbers as String)

		Collections.sort(listOfNumbers)

		this.driver.switchTo().defaultContent()

		looking ? -1 : listOfNumbers[listOfNumbers.size() - 1] //If completes while looking=true then timeout was reached before finding anything.
	}
}
