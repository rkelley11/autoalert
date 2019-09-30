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
import search.LeftFrame.ExpandableSearch
import general.Urls
import begin.Initialize

// Login
new Initialize().readySetGo()

def testObjectUnderTest
def isSearchSectionGood
def searchMenuTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu')
def hamburgerIconTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Hamburger Icon')

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Verify search menu
CustomKeywords.'test.Assistant.waitForElementPresent'(searchMenuTestObject, 10, AfterFailure.STOP)

// Expand left search frame
def classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def expanded = !classes.contains('ng-hide')

if(!expanded){
	// Click the hamburger icon
	CustomKeywords.'test.Assistant.verifiedClick'(hamburgerIconTestObject, AfterFailure.CONTINUE)	
}

// Verify the Manage Search, Leads & Processes icon exists
testObjectUnderTest = findTestObject('Search Page/Left Frame/Search - Left Frame - Manage Searches Leads and Processes - Icon') 

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.CONTINUE)

// Verify the Manage Search, Leads & Processes link exists & text is correct
testObjectUnderTest = findTestObject('Search Page/Left Frame/Search - Left Frame - Manage Searches Leads and Processes - Link') 

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Manage Searches, Leads & Processes', 'link', AfterFailure.CONTINUE)

// Verify the Manage Search, Leads & Processes link navigates to the correct url when clicked
CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

def currentUrl = WebUI.getUrl(FailureHandling.CONTINUE_ON_FAILURE)

def expectedUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.MANAGE_LEADS_PROCESSES

def urlsMatch = currentUrl == expectedUrl

CustomKeywords.'test.Assistant.verifyTrue'(urlsMatch, 'Successfully navigated to the \'Manage Leads / Processes\' page', AfterFailure.STOP)

// Verify the Manage Leads / Processes header
testObjectUnderTest = findTestObject('Search Page/Manage Leads Processes/Search - Manage Leads Processes - Header') 

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Manage Leads / Processes', 'header', AfterFailure.CONTINUE)