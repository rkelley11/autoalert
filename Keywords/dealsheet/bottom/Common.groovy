package dealsheet.bottom

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
import general.Enums.Alerts

import sql.*
import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Common extends WrappedSelenium{

	@Keyword
	def openTab(BottomSectionTabs2 tab){

		def logHeader, tabInfo

		def tabOpened = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			tabInfo = tab.getValue()

			tabOpened = isTabOpen(tab)

			if(!tabOpened){

				KeywordUtil.logInfo(logHeader + "Attempting to open the " + tabInfo['name'] + " tab")

				def testObject = returnTestObject(tabInfo['testObject'])

				click(testObject)

				tabOpened = isTabOpen(tab)

				KeywordUtil.logInfo(logHeader + "Opening the " + tabInfo['name'] + " tab WAS" + (tabOpened ? " " : " NOT ") + "successful")
			}
			else{

				KeywordUtil.logInfo(logHeader + tabInfo['name'] + " tab was already open, so no action taken")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		tabOpened
	}

	def isTabOpen(BottomSectionTabs2 tab){

		def logHeader

		def tabOpen = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('a')

			def tabInfo = tab.getValue()

			def testObject = returnTestObject(tabInfo['testObject'])

			def anchors = findElements(by, testObject)

			if(anchors){

				def classes = getAttribute(anchors[0], 'class')

				if(classes){

					tabOpen = classes.contains('tab-active')
				}

				def overlayBy = By.cssSelector('.ds-tab-area .cg-busy-backdrop-animation:nth-child(10)')

				new Overlays().waitForOverlayToClear(overlayBy)
			}

			KeywordUtil.logInfo(logHeader + tabInfo['name'] + " tab IS" + (tabOpen ? " " : " NOT ") + "open")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		tabOpen
	}

	// Need to refactor to get rid of one of the BottomSectionTabs enums.  Other is in dealsheet.Common
	enum BottomSectionTabs2{
		ALERTS_AND_SCRIPTS(['testObject': TestObjectNames.Tabs.ALERTS_AND_SCRIPTS, 'name':'Alerts & Scripts']),
		EXISTING_VEHICLE_DETAILS(['testObject': TestObjectNames.Tabs.EXISTING_VEHICLE_DETAILS, 'name': 'Existing Vehicle Details']),
		REPLACEMENT_DETAILS(['testObject': TestObjectNames.Tabs.REPLACEMENT_DETAILS, 'name': 'Replacement Details']),
		FEATURE_COMPARISON(['testObject': TestObjectNames.Tabs.FEATURE_COMPARISON, 'name': 'Feature Comparison']),
		ACTIVITY_AND_NOTES(['testObject': TestObjectNames.Tabs.ACTIVITY_AND_NOTES, 'name': 'Activity & Notes']),
		SERVICE_HISTORY(['testObject': TestObjectNames.Tabs.SERVICE_HISTORY, 'name': 'Service History']),
		OTHER_VEHICLES_OWNED(['testObject': TestObjectNames.Tabs.OTHER_VEHICLES_OWNED, 'name': 'Other Vehicles Owned']),
		DEAL_HISTORY(['testObject': TestObjectNames.Tabs.DEAL_HISTORY, 'name': 'Deal History']),
		CHANGE_LOG(['testObject': TestObjectNames.Tabs.CHANGE_LOG, 'name': 'Change Log'])

		def getValue

		BottomSectionTabs2(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}
