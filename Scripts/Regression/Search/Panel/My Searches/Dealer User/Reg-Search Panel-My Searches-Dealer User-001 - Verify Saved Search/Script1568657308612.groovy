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

def searchSection = ExpandableSearch.MY_SEARCHES
def overlayCleared
def testObjectUnderTest
def isSearchSectionGood
def searchMenuTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu')
def hamburgerIconTestObject = findTestObject('Search Page/Left Frame/Search - Left Frame - Hamburger Icon')

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Verify and click the search button
testObjectUnderTest = findTestObject('Search Page/Buttons/Search - Buttons - Search')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Wait for overlay to hide
testObjectUnderTest = findTestObject('Search Page/Search Overlay')

overlayCleared = CustomKeywords.'general.Overlays.waitForOverlayToClear'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(overlayCleared, 'Overlay is hidden', AfterFailure.STOP)

// Verify and click the save search button
testObjectUnderTest = findTestObject('Search Page/Buttons/Search - Buttons - Save Search')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify and send keys to the save as input
testObjectUnderTest = findTestObject('Search Page/Save Search Preset/Search - Save Search Preset - Save As - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

def searchName = LocalDateTime.now() as String

WebUI.sendKeys(testObjectUnderTest, searchName, FailureHandling.STOP_ON_FAILURE)

// Verify and click the submit button
testObjectUnderTest = findTestObject('Search Page/Save Search Preset/Buttons/Search - Save Search Preset - Buttons - Submit')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Wait for overlay to hide
testObjectUnderTest = findTestObject('Search Page/Search Overlay')

overlayCleared = CustomKeywords.'general.Overlays.waitForOverlayToClear'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(overlayCleared, 'Overlay is hidden', AfterFailure.STOP)

// Verify search menu
CustomKeywords.'test.Assistant.waitForElementPresent'(searchMenuTestObject, 10, AfterFailure.STOP)

// Expand left search frame
def classes = WebUI.getAttribute(searchMenuTestObject, 'class', FailureHandling.CONTINUE_ON_FAILURE)

def expanded = !classes.contains('ng-hide')

if(!expanded){
	// Click the hamburger icon
	CustomKeywords.'test.Assistant.verifiedClick'(hamburgerIconTestObject, AfterFailure.CONTINUE)	
}

// Verify My Searches exists, expands and contains individual searches
isSearchSectionGood = CustomKeywords.'search.Panel.verifyExpandableSearch'(searchSection)

CustomKeywords.'test.Assistant.verifyTrue'(isSearchSectionGood, '\'My Searches\' section passes', AfterFailure.CONTINUE)

// Verify if search is in search section
def search = CustomKeywords.'search.Panel.verifySectionContainsSearch'(searchSection, searchName)

def sectionHeader = searchSection.getValue()['header']

CustomKeywords.'test.Assistant.verifyTrue'(search['exists'], '\'' + searchName + '\' search was found in ' + sectionHeader, AfterFailure.STOP)

// Delete Search
def searchDeleted = CustomKeywords.'search.Panel.deleteSearch'(search['element'])

CustomKeywords.'test.Assistant.verifyTrue'(searchDeleted['deleted'], 'Search was successfully deleted', AfterFailure.STOP)