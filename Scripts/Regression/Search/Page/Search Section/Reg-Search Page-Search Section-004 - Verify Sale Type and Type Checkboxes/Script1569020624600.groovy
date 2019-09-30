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
def checked
def unchecked
def type

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Search Section - Sale Type - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Sale Type', 'Sale Type header label', AfterFailure.CONTINUE)

// Verify label
type = 'Cash'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Cash/Search Section - Sale Type - Cash - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Sale Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Cash/Search Section - Sale Type - Cash - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Sale Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Sale Type \'' + type + '\' checked successfully', AfterFailure.STOP)

// Verify label
type = 'Lease'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Lease/Search Section - Sale Type - Lease - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Sale Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Lease/Search Section - Sale Type - Lease - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Sale Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Sale Type \'' + type + '\' checked successfully', AfterFailure.STOP)

// Verify label
type = 'Retail'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Retail/Search Section - Sale Type - Retail - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Sale Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Retail/Search Section - Sale Type - Retail - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Sale Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Sale Type \'' + type + '\' checked successfully', AfterFailure.STOP)

// Verify label
type = 'Balloon'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Balloon/Search Section - Sale Type - Balloon - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Sale Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Sale Type/Balloon/Search Section - Sale Type - Balloon - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Sale Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Sale Type \'' + type + '\' checked successfully', AfterFailure.STOP)

// TYPE
// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Type/Search Section - Type - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Type', 'Type header label', AfterFailure.CONTINUE)

// Verify label
type = 'New'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Type/New/Search Section - Type - New - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Type/New/Search Section - Type - New - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Type \'' + type + '\' checked successfully', AfterFailure.STOP)

// Verify label
type = 'Used'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Type/Used/Search Section - Type - Used - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, type, 'Type \'' + type + '\' label', AfterFailure.CONTINUE)

// Check and uncheck checkbox
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Type/Used/Search Section - Type - Used - Checkbox')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

unchecked = CustomKeywords.'general.WrappedSelenium.uncheck'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(unchecked, 'Type \'' + type + '\' unchecked successfully', AfterFailure.STOP)

checked = CustomKeywords.'general.WrappedSelenium.check'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyTrue'(checked, 'Type \'' + type + '\' checked successfully', AfterFailure.STOP)

