import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets
import test.Assistant.AfterFailure
import search.Panel.ExpandableSearch
import general.Urls
import begin.Initialize
import java.time.LocalDateTime

// Login
new Initialize().readySetGo('automation4')

def searchSection = ExpandableSearch.TAG_BASED_SEARCHES
def testObjectUnderTest
def isSearchSectionGood
def searchMenuTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu')
def hamburgerIconTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Hamburger Icon')

// Go to page
searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the Search page url', AfterFailure.STOP)

// Verify search menu
CustomKeywords.'test.Assistant.waitForElementPresent'(searchMenuTestObject, 10, AfterFailure.STOP)

// Expand left search frame
def classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def expanded = !classes.contains('ng-hide')

if(!expanded){
	// Click the hamburger icon
	CustomKeywords.'test.Assistant.verifiedClick'(hamburgerIconTestObject, AfterFailure.CONTINUE)	
}

// Verify Searches exists, expands and contains individual searches
isSearchSectionGood = CustomKeywords.'search.Panel.verifyExpandableSearch'(searchSection)

def sectionHeader = searchSection.getValue()['header']

CustomKeywords.'test.Assistant.verifyTrue'(isSearchSectionGood, '\'' + sectionHeader + '\' section passes', AfterFailure.CONTINUE)

// Expand section
isSearchSectionGood = CustomKeywords.'search.Panel.expandSection'(searchSection)

CustomKeywords.'test.Assistant.verifyTrue'(isSearchSectionGood, '\'' + sectionHeader + '\' section expanded successfully', AfterFailure.CONTINUE)

// Get Search Lists
def section = CustomKeywords.'search.Panel.getSection'(searchSection)

def sectionSearches = CustomKeywords.'search.Panel.getSectionSearches'(section)

def sectionSearchesList = new ArrayList<String>(sectionSearches.keySet())

// Go to page
searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.OPPORTUNITIES_DASHBOARD

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the Opportunities Dashboard', AfterFailure.STOP)

// Refresh Tagged Opportunities
testObjectUnderTest = findTestObject('Object Repository/Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Tagged Opportunities/Tagged Opportunities Refresh Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Get list of Tagged Opportunities
def taggedOpportunitiesList = CustomKeywords.'opportunities.alertdesk.dashboard.opportunities.TaggedOpportunities.returnList'()

Collections.sort(taggedOpportunitiesList)

CustomKeywords.'test.Assistant.verifyEqual'(sectionSearchesList, taggedOpportunitiesList, sectionHeader + ' list', AfterFailure.STOP)


