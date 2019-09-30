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
import opportunities.alertdesk.dashboard.opportunities.OpportunitySearches.OppSearchColumn
import begin.Initialize
import general.Urls

def testObjectUnderTest
def searchMenuTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu')
def hamburgerIconTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Hamburger Icon')

// Login
new Initialize().readySetGo()

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, AfterFailure.STOP)

// Verify search menu
CustomKeywords.'test.Assistant.waitForElementPresent'(searchMenuTestObject, 10, AfterFailure.STOP)

// Get whether search menu is currently collapsed or expanded
def classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def originallyCollapsed = classes.contains('ng-hide')

// Click the hamburger icon
CustomKeywords.'test.Assistant.verifiedClick'(hamburgerIconTestObject, AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu - Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'SEARCHES', 'header', AfterFailure.CONTINUE)

// Verify search menu has expanded or collapsed depending on ORIGINAL state
classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def firstClickCollapsed = classes.contains('ng-hide')

CustomKeywords.'test.Assistant.verifyNotEqual'(originallyCollapsed, firstClickCollapsed, 'Original vs First Click state', AfterFailure.STOP)

// Click the hamburger icon
CustomKeywords.'test.Assistant.verifiedClick'(hamburgerIconTestObject, AfterFailure.STOP)

// Verify search menu has expanded or collapsed depending on AFTER FIRST CLICK state
classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def secondClickCollapsed = classes.contains('ng-hide')

CustomKeywords.'test.Assistant.verifyNotEqual'(firstClickCollapsed, secondClickCollapsed, 'First vs Second Click state', AfterFailure.STOP)