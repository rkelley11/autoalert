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

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.SEARCH
def testObjectUnderTest
def dropdownUnderTest
def nameUnderTest
def options
def optionsList

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Confirm Model dropdown is disabled until Make is selected
dropdownUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Model/Search Section - Vehicle - Model - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyAttribute'(dropdownUnderTest, 'disabled', 'true', 'Model dropdown before Make is selected', AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Make'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
dropdownUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Make/Search Section - Vehicle - Make - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < options.size() ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

// Check first make option from dropdown
CustomKeywords.'test.Assistant.verifiedClick'(options[2][1], AfterFailure.STOP)

// Close dropdown
CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Year'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Year/Search Section - Vehicle - Year - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
dropdownUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Year/Search Section - Vehicle - Year - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Year/Search Section - Vehicle - Year - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < options.size() ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

// Check first year option from dropdown
CustomKeywords.'test.Assistant.verifiedClick'(options[2][1], AfterFailure.STOP)

// Close dropdown
CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Confirm Trim auto-fill options are not available until Model is selected
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Trim/Search Section - Vehicle - Trim - List Items')

WebUI.verifyElementNotPresent(testObjectUnderTest, 1, FailureHandling.STOP_ON_FAILURE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Model'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Model/Search Section - Vehicle - Model - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
dropdownUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Model/Search Section - Vehicle - Model - Dropdown Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

// Verify top dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Model/Search Section - Vehicle - Model - Dropdown List')

options = CustomKeywords.'search.section.Search.getDropdownOptions'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(options, null, AfterFailure.STOP)

optionsList = []

for(int i = 0 ; i < options.size() ; i++){
	
	optionsList.add(options[i][0])
}

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[0], 'Check All', 'First option', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyEqual'(optionsList[1], 'Uncheck All', 'Second option', AfterFailure.CONTINUE)

// Check first model option from dropdown
CustomKeywords.'test.Assistant.verifiedClick'(options[2][1], AfterFailure.STOP)

// Close dropdown
CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Trim'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Trim/Search Section - Vehicle - Trim - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Open dropdown
dropdownUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Trim/Search Section - Vehicle - Trim - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(dropdownUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(dropdownUnderTest, AfterFailure.STOP)

// Verify auto-fill dropdown options exist
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Vehicle/Trim/Search Section - Vehicle - Trim - List Items')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 3, AfterFailure.STOP)