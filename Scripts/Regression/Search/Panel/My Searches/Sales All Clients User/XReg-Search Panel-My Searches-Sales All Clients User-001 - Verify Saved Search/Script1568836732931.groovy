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
import general.Enums.Alerts
import search.Page.SearchPageSections

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.SEARCH
def overlayCleared
def testObjectUnderTest
def alert
def checkBoxSet
def alertUnChecked

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

/*
// Check and uncheck an opportunity
alert = Alerts.ALERT

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.FLEX

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.IN_MARKET

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.ENGAGED

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.SERVICE

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.PENDING_SERVICE

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.MILEAGE

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.WARRANTY

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Check and uncheck an opportunity
alert = Alerts.CONTRACT_END

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

checkBoxSet = CustomKeywords.'search.section.Search.uncheckAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully unchecked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Verify the opportunities popover message
testObjectUnderTest = findTestObject('Search Page/Section/Search/Opportunities/Search Section - Opportunities - Mouse Over - Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

def message = 'Select to show all clients or only specified opportunities'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', message, AfterFailure.CONTINUE)
*/

// Score
// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Score/Search Section - Score - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Score', AfterFailure.CONTINUE)

// Verify inputs
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Score/Search Section - Score - Minimum')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

// Verify separator
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Score/Search Section - Score - Dash Between Min and Max')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, '-', 'Separator between minimum and maximum score', AfterFailure.CONTINUE)

// Verify inputs
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Score/Search Section - Score - Maximum')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

// Verify mouse over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Score/Search Section - Score - Container')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

def message = 'Deal Sheet score range'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', message, AfterFailure.CONTINUE)


// Open dropdown
testObjectUnderTest = findTestObject('Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Get dropdown options and confirm top choices are 'Check All' and 'Uncheck All'
testObjectUnderTest = findTestObject('Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Dropdown List')

def options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyEqual'(options[0][0], 'Check All', 'First option on dropdown', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(options[1][0], 'Uncheck All', 'Second option on dropdown', AfterFailure.CONTINUE)

// Close dropdown
testObjectUnderTest = findTestObject('Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

