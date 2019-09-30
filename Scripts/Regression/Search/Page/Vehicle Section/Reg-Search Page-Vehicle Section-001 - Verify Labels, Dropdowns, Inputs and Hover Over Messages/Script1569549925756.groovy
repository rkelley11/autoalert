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
import general.TestObjects
import general.Urls
import begin.Initialize
import search.Page.SearchPageSections
import general.Enums.Alerts
import general.RandomGenerator
import org.openqa.selenium.Keys as Keys

// Login
new Initialize().readySetGo('automation10')

def section = SearchPageSections.VEHICLE
def testObjectUnderTest
def nameUnderTest
def actualOptions
def expectedOptions
def optionsDetails
def checked
def alert
def checkBoxSet
def expectedHoverOverMessage
def randomEntry
def randomGenerator = new RandomGenerator()
def tos = new TestObjects()

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.SEARCH

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the search page url', AfterFailure.STOP)

// Expand section
def sectionToggled = CustomKeywords.'search.Page.expandSection'(section)

CustomKeywords.'test.Assistant.verifyTrue'(sectionToggled, section.getValue()['header'] + ' section successfully expanded', AfterFailure.STOP)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Status'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Status/Vehicle Section - Status - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Status/Vehicle Section - Status - Dropdown')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

optionsDetails = CustomKeywords.'general.WrappedSelenium.getAllOptions2'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(optionsDetails, null, tos.getTestObjectName(testObjectUnderTest) + ' options are not null', AfterFailure.STOP)

expectedOptions = ['', 'Still Owns', 'Owns Paid Off', 'Owns Not Paid Off', 'No Longer Owns']

actualOptions = []

for(int i = 0 ; i < optionsDetails.size() ; i++){
	
	actualOptions.add(optionsDetails[i]['text'])
}

CustomKeywords.'test.Assistant.verifyEqual'(actualOptions, expectedOptions, tos.getTestObjectName(testObjectUnderTest) + ' options', AfterFailure.CONTINUE)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Status/Vehicle Section - Status - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Select if client still owns the vehicle and/or is still making payments on it'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Odometer'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Odometer/Vehicle Section - Odometer - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Odometer/Vehicle Section - Odometer - Mileage - From')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomNumber(1000, 9999) as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Odometer/Vehicle Section - Odometer - Mileage - Separator')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, '-', tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Odometer/Vehicle Section - Odometer - Mileage - To')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomNumber(10000, 99999) as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'VIN'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/VIN/Vehicle Section - VIN - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/VIN/Vehicle Section - VIN - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = (randomGenerator.generateRandomString(17, true, true, false) as String).toUpperCase()
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Ext. Color'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Ext Color/Vehicle Section - Ext Color - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Ext Color/Vehicle Section - Ext Color - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = (randomGenerator.generateRandomString(10, true, false, false) as String).toUpperCase()
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Ext Color/Vehicle Section - Ext Color - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Vehicle Exterior Color'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Int. Color'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Int Color/Vehicle Section - Int Color - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Int Color/Vehicle Section - Int Color - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = (randomGenerator.generateRandomString(10, true, false, false) as String).toUpperCase()
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Int Color/Vehicle Section - Int Color - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Vehicle Interior Color'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Warranty'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Warranty/Vehicle Section - Warranty - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Warranty/Vehicle Section - Warranty - Dropdown')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

optionsDetails = CustomKeywords.'general.WrappedSelenium.getAllOptions2'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(optionsDetails, null, tos.getTestObjectName(testObjectUnderTest) + ' options are not null', AfterFailure.STOP)

expectedOptions = ['', 'Has Extended Warranty', 'Has No Extended Warranty']

actualOptions = []

for(int i = 0 ; i < optionsDetails.size() ; i++){
	
	actualOptions.add(optionsDetails[i]['text'])
}

CustomKeywords.'test.Assistant.verifyEqual'(actualOptions, expectedOptions, 'Status dropdown options', AfterFailure.CONTINUE)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Warranty/Vehicle Section - Warranty - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Select if customer bought extended vehicle coverage at the time of sale'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Recent RO'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Recent RO/Vehicle Section - Recent RO - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify dropdown options
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Recent RO/Vehicle Section - Recent RO - Dropdown')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

optionsDetails = CustomKeywords.'general.WrappedSelenium.getAllOptions2'(testObjectUnderTest)

CustomKeywords.'test.Assistant.verifyNotEqual'(optionsDetails, null, tos.getTestObjectName(testObjectUnderTest) + ' options are not null', AfterFailure.STOP)

expectedOptions = ['', 'No', 'Has']

actualOptions = []

for(int i = 0 ; i < optionsDetails.size() ; i++){
	
	actualOptions.add(optionsDetails[i]['text'])
}

CustomKeywords.'test.Assistant.verifyEqual'(actualOptions, expectedOptions, tos.getTestObjectName(testObjectUnderTest) + ' options', AfterFailure.CONTINUE)

// Verify label
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Recent RO/Vehicle Section - Recent RO - In Separator')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'in', tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify input
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Recent RO/Vehicle Section - Recent RO - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

randomEntry = randomGenerator.generateRandomNumber(1, 99) as String
 
WebUI.sendKeys(testObjectUnderTest, randomEntry + Keys.TAB, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', randomEntry, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Recent RO/Vehicle Section - Recent RO - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Days since vehicle has/has not visited dealership service'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'RO Date'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'From'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - From - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify clicking input to open calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - From - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - From - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify clicking calendar button opens calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - From - Calendar Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - From - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify label
nameUnderTest = 'To'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - To - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify clicking input to open calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - To - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - To - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify clicking calendar button opens calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - To - Calendar Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - To - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/RO Date/Vehicle Section - RO Date - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Repair Order date range'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

//----------------------------------------------------------------------------------------------------------------------------------------------------------

// Verify label
nameUnderTest = 'Appt. Date'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify label
nameUnderTest = 'From'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - From - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify clicking input to open calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - From - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - From - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify clicking calendar button opens calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - From - Calendar Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - From - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify label
nameUnderTest = 'To'

testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - To - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, nameUnderTest, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)

// Verify clicking input to open calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - To - Input')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - To - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify clicking calendar button opens calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - To - Calendar Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify calendar displays
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - To - Calendar')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

// Close calendar
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Label')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify hover over message
testObjectUnderTest = findTestObject('Object Repository/Search Page/Section/Vehicle/Appt Date/Vehicle Section - Appt Date - Hover Over Message')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 1, AfterFailure.STOP)

expectedHoverOverMessage = 'Service Appointment date range'

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'uib-popover', expectedHoverOverMessage, tos.getTestObjectName(testObjectUnderTest), AfterFailure.CONTINUE)