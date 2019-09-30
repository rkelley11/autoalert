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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import dealsheet.bottom.Common.BottomSectionTabs2 as BottomSectionTabs2
import begin.Initialize

// Login
new Initialize().readySetGo('automation5')

def elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities by Alert/Opportunity - Alert'), 
    10)

CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Alert Desk/Dashboard/Opportunities Dashboard/Opportunities by Alert/Opportunity - Alert'))

CustomKeywords.'opportunities.alertdesk.Opportunities.clickFirstRow'()

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

// Opening Tab
def tabOpened = CustomKeywords.'dealsheet.bottom.Common.openTab'(BottomSectionTabs2.EXISTING_VEHICLE_DETAILS)

WebUI.verifyEqual(tabOpened, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Heading
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Dealsheet - Tabs - Existing Vehicle Details - Heading'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

def elementText = WebUI.getText(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Dealsheet - Tabs - Existing Vehicle Details - Heading'))

WebUI.verifyEqual(elementText, 'Existing Vehicle Details', FailureHandling.CONTINUE_ON_FAILURE)

// Verify VIN is 17 digits
def vinField = findTestObject('Dealsheet/Tabs/Existing Vehicle Details/VIN/Dealsheet - Bottom - Existing Vehicle Details - VIN')

elementVisible = WebUI.waitForElementVisible(vinField, 10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

elementText = CustomKeywords.'dealsheet.Common.determineVIN'(WebUI.getText(vinField))

KeywordUtil.logInfo(((('VIN(' + elementText) + ') is ') + ((elementText.size()) as String)) + ' digits')

WebUI.verifyEqual(elementText.size() == 17, true, FailureHandling.CONTINUE_ON_FAILURE)

// Verify Equity Available Heading
elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Equity Available/Dealsheet - Tabs - Existing Vehicle Details - Equity Available - Heading'), 
    10)

WebUI.verifyEqual(elementVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

elementText = WebUI.getText(findTestObject('Dealsheet/Tabs/Existing Vehicle Details/Equity Available/Dealsheet - Tabs - Existing Vehicle Details - Equity Available - Heading'))

WebUI.verifyEqual(elementText, 'EQUITY AVAILABLE', FailureHandling.CONTINUE_ON_FAILURE)

