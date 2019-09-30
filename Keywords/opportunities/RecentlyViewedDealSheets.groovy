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

import groovy.time.*

import com.kms.katalon.core.util.KeywordUtil

public class RecentlyViewedDealSheets extends WrappedSelenium{

	@Keyword
	def isRecentlyViewedDealSheetsDisplayingProperly(TestObject testObject){

		def popupObjName = TestObjectNames.RecentlyViewedDealSheets.ICON

		def correctTitle = "Recently Viewed Deal Sheets"

		def listItemsBy = By.className("deal-sheet-item")

		KeywordUtil.logInfo("Validating popup title: " + correctTitle)

		def activeProperty = getActiveProperty(testObject)

		def headline = findElement(getByLocator(activeProperty.name, activeProperty.value + "/h5")).getText()

		def unorderedList = findElement(getByLocator(activeProperty.name, activeProperty.value + "/ul"))

		def numberOfListItems = unorderedList.findElements(listItemsBy).size()
		
		def timeExpired = false

		def timeoutPeriod = new TimeDuration(0,0,10,0)

		def timeStart = new Date()

		while(numberOfListItems == 0 && !timeExpired){

			numberOfListItems = unorderedList.findElements(listItemsBy).size()

			timeExpired = isTimeExpired(timeStart, timeoutPeriod)
		}

		KeywordUtil.logInfo("Title is correct: " + (correctTitle == headline) as String)

		KeywordUtil.logInfo("Number of list items displayed: " + numberOfListItems as String)

		(correctTitle == headline) && (numberOfListItems > 0)
	}

	/*@Keyword
	def isRecentlyViewedDealSheetsDisplayingProperly(TestObject testObject){

		def popupObjName = TestObjectNames.RecentlyViewedDealSheets.ICON

		def correctTitle = "Recently Viewed Deal Sheets"

		def listItemsBy = By.className("deal-sheet-item")

		KeywordUtil.logInfo("Validating popup title: " + correctTitle)

		def activeProperty = getActiveProperty(testObject)

		def headline = findElement(getByLocator(activeProperty.name, activeProperty.value + "/h5")).getText()

		def unorderedList = findElement(getByLocator(activeProperty.name, activeProperty.value + "/ul"))

		def numberOfListItems = unorderedList.findElements(listItemsBy).size()

		def timeoutPeriod = new TimeDuration(0,0,10,0)

		def timeStart = new Date()

		while(numberOfListItems == 0 && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			numberOfListItems = unorderedList.findElements(listItemsBy).size()
		}

		KeywordUtil.logInfo("Title is correct: " + (correctTitle == headline) as String)

		KeywordUtil.logInfo("Number of list items displayed: " + numberOfListItems as String)

		(correctTitle == headline) && (numberOfListItems > 0)
	}*/
}
