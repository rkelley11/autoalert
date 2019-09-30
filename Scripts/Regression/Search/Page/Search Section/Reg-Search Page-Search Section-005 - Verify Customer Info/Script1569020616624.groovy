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
import general.RandomGenerator
import org.openqa.selenium.Keys as Keys

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.SEARCH
def testObjectUnderTest
def checked
def unchecked
def nameUnderTest
def randomEntry
def randomGenerator = new RandomGenerator()

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

// Verify label
nameUnderTest = 'Name'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Name/Search Section - Customer - Name - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Name/Search Section - Customer - Name - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomString(10)
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'Address'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Address/Search Section - Customer - Address - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Address/Search Section - Customer - Address - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomStreetAddress()
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'Zip Code'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Zip Code/Search Section - Customer - Zip Code - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Zip Code/Search Section - Customer - Zip Code - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomNumber(10000, 99999) as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'in'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Zip Code/Search Section - Customer - Zip Code - In - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
nameUnderTest = 'Miles'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Zip Code/Search Section - Customer - Zip Code - In Miles - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomNumber(1, 100) as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'mi'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Zip Code/Search Section - Customer - Zip Code - Miles - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'Phone'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Phone/Search Section - Customer - Phone - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Phone/Search Section - Customer - Phone - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomPhoneNumber() as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'Email'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Email/Search Section - Customer - Email - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, nameUnderTest + ' label', AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Search/Lower/Customer/Email/Search Section - Customer - Email - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomEmailAddress() as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, nameUnderTest + ' input', AfterFailure.CONTINUE)