package opportunities.preowned

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

import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

import groovy.time.*

public class OneOnOne extends WrappedSelenium{

	def driver

	OneOnOne(){

		this.driver = DriverFactory.getWebDriver()
	}

	def clickLink(LinkedHashMap allInfo){

		KeywordUtil.logInfo("Sales person: " + allInfo["salesPerson"] as String)

		allInfo["column"] = allInfo["column"] ? allInfo["column"] : Columns.ANY

		KeywordUtil.logInfo("Column: " + allInfo["column"] as String)

		def allRows, allCells, desiredLink, cellIndex

		def messageSalesPerson = allInfo["salesPerson"] ? allInfo["salesPerson"] : "the first available sales person"

		def messageLink = allInfo["column"] ? (allInfo["column"] as String) : "first available"

		cellIndex = allInfo["column"] ? allInfo["column"].getCellIndex() : 1

		def successful = false

		def salesPersonLowerCase = allInfo["salesPerson"] ? allInfo["salesPerson"].toLowerCase() : ""

		def byRows = By.cssSelector("#LeadAssignments table tbody tr")

		def byCells = By.cssSelector("td")

		this.driver.switchTo().frame("autoalertiframe")

		allRows = findElements(byRows, 30)

		for(int i = 0 ; i < allRows.size() ; i++){

			allCells = findElements(byCells, allRows[i])

			if(allInfo["salesPerson"]){

				def foundSalesPerson = getText(allCells[0])

				KeywordUtil.logInfo("Found sales person: " + foundSalesPerson as String)

				if(salesPersonLowerCase == foundSalesPerson.toLowerCase()){

					desiredLink = searchForLink(allCells, allInfo["column"])

					break
				}
			}
			else{

				desiredLink = searchForLink(allCells, allInfo["column"])

				if(desiredLink) break
			}
		}

		if(desiredLink){

			KeywordUtil.logInfo("Searching for " + messageSalesPerson + " was successful")

			successful = click(desiredLink, 30)
		}

		KeywordUtil.logInfo("Clicking on " + messageSalesPerson + "'s " + messageLink + " link was" + (successful ? " " : " NOT ") + "successful")

		this.driver.switchTo().defaultContent()

		successful
	}

	def searchForLink(ArrayList allCells, Columns column){

		def desiredLink

		def byLinks = By.cssSelector("a")

		def columnArray = column.getColumnNumbers()

		for (int i = 0 ; i < columnArray.size() ; i++){

			def links = allCells.size() > columnArray[i] ? findElements(byLinks, allCells[columnArray[i]], 1): null

			if (links){

				desiredLink = links[0]

				break
			}
		}

		desiredLink
	}

	enum Columns{

		ANY([1, 2, 3, 4]),
		LESS_THAN_30_DAYS([1]),
		BETWEEN_31_AND_44_DAYS([2]),
		BETWEEN_45_AND_60_DAYS([3]),
		GREATER_THAN_60_DAYS([4]),
		MTD_SALES([5]),
		MTD_LAST_SOLD([6])

		def ArrayList cellIndex

		Columns(ArrayList cellIndex){

			this.cellIndex = cellIndex
		}

		def getColumnNumbers(){

			return cellIndex
		}
	}
}
