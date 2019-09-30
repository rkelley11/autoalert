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

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

import groovy.time.*

public class UsedCarInventoryAndOpportunities extends WrappedSelenium{

	def driver

	UsedCarInventoryAndOpportunities(){

		this.driver = DriverFactory.getWebDriver()
	}

	def clickLink(LinkedHashMap allInfo){

		def successful

		try{

			allInfo["column"] = allInfo["column"] ? allInfo["column"] : Columns.ANY

			KeywordUtil.logInfo("Column: " + allInfo["column"] as String)

			def allColumns, allCells, desiredLink, cellIndex, columnArray

			cellIndex = allInfo["column"] ? allInfo["column"].getColumnNumbers() : 1

			def byColumn = By.cssSelector("svg g [text-anchor='middle'][font-size=\"14\"]:not([fill=\"#222222\"])")

			this.driver.switchTo().frame("autoalertiframe")

			allColumns = findElements(byColumn, 30)

			columnArray = allInfo["column"].getColumnNumbers()

			Actions builder = new Actions(this.driver)

			def testing = builder.click(allColumns[columnArray]).build().perform()

			successful = true
		}
		catch(Exception e){
			
			KeywordUtil.markWarning(e.getMessage())

			successful = false
		}

		KeywordUtil.logInfo("Clicking on " + (allInfo["column"] as String) + " link was" + (successful ? " " : " NOT ") + "successful")

		this.driver.switchTo().defaultContent()

		successful
	}

	def searchForLink(ArrayList allCells, Columns column){

		def desiredLink

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

		ANY([0]),
		LESS_THAN_30_DAYS([0]),
		BETWEEN_31_AND_44_DAYS([1]),
		BETWEEN_45_AND_60_DAYS([2]),
		MORE_THAN_60_DAYS([3])

		def ArrayList columnIndex

		Columns(ArrayList columnIndex){

			this.columnIndex = columnIndex
		}

		def getColumnNumbers(){

			return columnIndex
		}
	}
}
