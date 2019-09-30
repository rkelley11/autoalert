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
import com.kms.katalon.core.util.KeywordUtil
import test.Assistant.AfterFailure
import begin.Initialize
import general.Urls

def testObjectUnderTest

// Login
new Initialize().readySetGo()

// Go to Inventory page
def inventoryUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.INVENTORY

WebUI.navigateToUrl(inventoryUrl)

def currentUrl = WebUI.getUrl()

CustomKeywords.'test.Assistant.verifyEqual'(currentUrl, inventoryUrl, "Inventory page url check", AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Inventory/Inventory Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Inventory', 'header', AfterFailure.CONTINUE)

// Verify tab (text and is active)
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Inventory/Find Pre-Owned Opportunities/Inventory - Find Inventory Opportunities - Tab')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Find Pre-Owned Opportunities', 'tab', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'ui-tabs-active', AfterFailure.STOP)

// Click search
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Inventory/Find Pre-Owned Opportunities/Inventory - Find Inventory Opportunities - Button - Search')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

// Verify rows are present
def numberOfRowsShown = CustomKeywords.'opportunities.alertdesk.Common.returnNumberOfDataRows'()

CustomKeywords.'test.Assistant.verifyNotEqual'(numberOfRowsShown, null, "Rows displayed on Inventory tab", AfterFailure.STOP)