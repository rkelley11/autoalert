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
import search.Panel.SearchIcon
import search.Panel
import general.Urls
import begin.Initialize
import java.time.LocalDateTime
import general.WrappedSelenium
import org.openqa.selenium.By

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

// Share Search
def shareSearchClicked = CustomKeywords.'search.Panel.clickSearchIcon'(SearchIcon.SHARE, search['element'])

CustomKeywords.'test.Assistant.verifyTrue'(shareSearchClicked, 'Share Search was successfully clicked', AfterFailure.STOP)

// Verify if search is in shared search section
searchSection = ExpandableSearch.SHARED_SEARCHES

search = CustomKeywords.'search.Panel.verifySectionContainsSearch'(searchSection, searchName)

sectionHeader = searchSection.getValue()['header']

CustomKeywords.'test.Assistant.verifyTrue'(search['exists'], '\'' + searchName + '\' search was found in ' + sectionHeader, AfterFailure.STOP)

// Click the delete icon
def panel = new Panel()

def wrappedSelenium = new WrappedSelenium()

def by = panel.getDeleteBy()

def deleteIcon = wrappedSelenium.findElements(by, search['element'])

CustomKeywords.'test.Assistant.verifyNotEqual'(deleteIcon, null, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(deleteIcon[0], AfterFailure.STOP)

// Verify the X closes the Confirm Delete dialog and does not delete search
testObjectUnderTest = findTestObject('Search Page/Modals/Buttons/Search - Modals - Buttons - X to Close')

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify if search is still in search section
search = CustomKeywords.'search.Panel.verifySectionContainsSearch'(searchSection, searchName)

CustomKeywords.'test.Assistant.verifyTrue'(search['exists'], '\'' + searchName + '\' search was found in ' + sectionHeader, AfterFailure.STOP)

// Click the delete icon
deleteIcon = wrappedSelenium.findElements(by, search['element'])

CustomKeywords.'test.Assistant.verifyNotEqual'(deleteIcon, null, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(deleteIcon[0], AfterFailure.STOP)

// Verify the Cancel button closes the Confirm Delete dialog and does not delete search
testObjectUnderTest = findTestObject('Search Page/Modals/Buttons/Search - Modals - Buttons - Cancel Ok')

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify if search is still in search section
search = CustomKeywords.'search.Panel.verifySectionContainsSearch'(searchSection, searchName)

CustomKeywords.'test.Assistant.verifyTrue'(search['exists'], '\'' + searchName + '\' search was found in ' + sectionHeader, AfterFailure.STOP)

// Verify Confirm Delete dialog and Delete Search
def searchDeleted = CustomKeywords.'search.Panel.deleteSearch'(search['element'])

CustomKeywords.'test.Assistant.verifyTrue'(searchDeleted['deleted'], 'Search was successfully deleted', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(searchDeleted['title'], 'Confirm Delete', 'Title', AfterFailure.CONTINUE)

def expectedMessage = 'Are you sure you want to delete this shared search? This action cannot be undone.'

CustomKeywords.'test.Assistant.verifyEqual'(searchDeleted['message'], expectedMessage, 'Message', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(searchDeleted['cancelButtonText'], 'Cancel', 'Cancel button', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(searchDeleted['continueButtonText'], 'Continue', 'Continue button', AfterFailure.CONTINUE)


