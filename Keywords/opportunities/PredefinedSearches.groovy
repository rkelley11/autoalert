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

import java.time.format.DateTimeFormatter as DateTimeFormatter
import groovy.time.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

import opportunities.alertdesk.*

public class PredefinedSearches extends WrappedSelenium {

	def defaultSearchName = 'Automation - Please do not delete'

	def defaultSharedSearchName = 'Automation Shared - Please do not delete'

	def defaultPredefinedCommonSearchName = 'Alerts with Negative Equity'

	@Keyword
	def openSearch(SearchGroups group, String search){

		if(!search) search = determineSearchName(group)

		KeywordUtil.logInfo("Searching for: " + search as String)

		def searchElement = returnSearchLinkElement(group, search)

		click(searchElement)

		(new Search()).waitForSearchingOverlayToFinish()
	}

	def determineSearchName(SearchGroups group){

		def searchName

		switch (group){

			case SearchGroups.MY_RECENTLY_SAVED_SEARCHES:

				searchName = defaultSearchName

				break

			case SearchGroups.DEALER_SHARED_SEARCHES:

				searchName = defaultSharedSearchName

				break

			case SearchGroups.PREDEFINED_COMMON_SEARCHES:

				searchName = defaultPredefinedCommonSearchName

				break

			default:

				break
		}

		searchName
	}

	def returnSearchLinkElement(SearchGroups group, String search){

		KeywordUtil.logInfo("Starting: returnSearchLinkElement(" + (group as String) + ", " + search + ")")

		def desiredSearch, index, message

		def byAllLists = By.cssSelector("search-presets ul")

		def allSearchLists = findElements(byAllLists)

		KeywordUtil.logInfo("Total search groups found: " + allSearchLists.size() as String)

		index = group.getValue()

		KeywordUtil.logInfo("Desired group number: " + index as String)

		if(allSearchLists.size() >= index){

			def chosenList = allSearchLists[index - 1]

			def allSearches = findElements(By.cssSelector("a"), chosenList)

			for (int i = 0; i < allSearches.size(); i++){

				def searchText = getText(allSearches[i])

				if (searchText == search){

					desiredSearch = allSearches[i]

					break
				}
			}
		}

		message = desiredSearch ? "Found" : "Not Found"

		KeywordUtil.logInfo("'" + search + "' in the " + (group as String) + " group: " + message)

		desiredSearch
	}

	enum SearchGroups{

		MY_RECENTLY_SAVED_SEARCHES(1),
		DEALER_SHARED_SEARCHES(2),
		PREDEFINED_COMMON_SEARCHES(3)

		def getValue

		SearchGroups(int getValue){

			this.getValue = getValue
		}

		def getValue(){

			return getValue
		}
	}
}
