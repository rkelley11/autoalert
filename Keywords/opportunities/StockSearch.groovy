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

import com.kms.katalon.core.util.KeywordUtil

public class StockSearch extends WrappedSelenium{

	def pandoProfileContainerObjName = TestObjectNames.StockSearch.PANDO_PROFILE_CONTAINER

	def pandoProfileCustomerNameObjName = TestObjectNames.StockSearch.PANDO_PROFILE_CUSTOMER_NAME

	def resultsListBy = By.cssSelector('stock-number-search .dropdown-menu li')

	def stockNumberBy = By.cssSelector('.stock-no')

	def stockFullTrimBy = By.cssSelector('.stock-full-trim')

	def stockNewUsedCPOBy = By.cssSelector('.stock-new-used-cpo')

	@Keyword
	def selectFirstResult(){

		def resultsList = findElements(resultsListBy)

		def stockNumberSelected = (findElements(stockNumberBy, resultsList[0]))[0].getText()

		click(resultsList[0])

		stockNumberSelected
	}

	@Keyword
	def resultsDisplayProperly(){

		def resultsDisplayedProperly

		def resultsList = findElements(resultsListBy)

		if(resultsList){

			def stockNumberDisplayed = (findElements(stockNumberBy, resultsList[0])).size() > 0

			def stockFullTrimDisplayed = (findElements(stockFullTrimBy, resultsList[0])).size() > 0

			def stockNewUsedCPODisplayed = (findElements(stockNewUsedCPOBy, resultsList[0])).size() > 0

			resultsDisplayedProperly = stockNumberDisplayed && stockFullTrimDisplayed && stockNewUsedCPODisplayed
		}
		else{

			resultsDisplayedProperly = false
		}

		resultsDisplayedProperly
	}
}
