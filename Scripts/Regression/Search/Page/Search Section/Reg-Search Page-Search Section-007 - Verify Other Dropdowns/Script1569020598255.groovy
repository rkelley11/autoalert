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
import test.Assistant.AfterFailure
import general.Urls
import begin.Initialize
import search.Page.SearchPageSections
import general.Enums.Alerts

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.SEARCH
def testObjectUnderTest
def nameUnderTest
def options
def optionsList
def checked
def alert
def checkBoxSet

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Sold By'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Sold By/Search Section - Other - Sold By - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Sold By/Search Section - Other - Sold By - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Sold By/Search Section - Other - Sold By - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < 3 ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyTrue'(options.size() > 2, nameUnderTest + ' dropdown has at least one available option', AfterFailure.CONTINUE)

// Close dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Sold By/Search Section - Other - Sold By - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Owner'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Owner/Search Section - Other - Owner - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Owner/Search Section - Other - Owner - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Owner/Search Section - Other - Owner - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < 4 ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[2], '[Orphans]', 'First owner option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyTrue'(options.size() > 3, nameUnderTest + ' dropdown has at least one available option', AfterFailure.CONTINUE)

// Close dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Owner/Search Section - Other - Owner - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Alert Pmt'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Alert Payment/Search Section - Other - Alert Payment - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Confirm dropdown is disabled until the Alert opportunity is checked
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Alert Payment/Search Section - Other - Alert Payment - Dropdown Button')

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'disabled', 'true', nameUnderTest + ' dropdown', AfterFailure.CONTINUE)

// Check and uncheck an opportunity
alert = Alerts.ALERT

checkBoxSet = CustomKeywords.'search.section.Search.checkAlert'(alert)
	
CustomKeywords.'test.Assistant.verifyTrue'(checkBoxSet, 'Successfully checked the ' + alert.getValue()['description'] + ' opportunity', AfterFailure.STOP)

// Open dropdown
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Alert Payment/Search Section - Other - Alert Payment - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Other/Alert Payment/Search Section - Other - Alert Payment - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, 'Alert Payment dropdown options vs. null', AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < options.size() ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[2], 'Good (Below Alert Threshold)', 'First Alert Payment option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[3], 'Better', 'Second Alert Payment option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[4], 'Best (Below Current Pymt)', 'Third Alert Payment option', AfterFailure.CONTINUE)