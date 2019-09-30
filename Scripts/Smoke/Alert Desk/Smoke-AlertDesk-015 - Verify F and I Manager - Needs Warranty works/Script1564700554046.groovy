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

// Login
new Initialize().readySetGo()

// Go to F&I Manager page
def fiManagerUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.FI_MANAGER

WebUI.navigateToUrl(fiManagerUrl)

def currentUrl = WebUI.getUrl()

CustomKeywords.'test.Assistant.verifyEqual'(currentUrl, fiManagerUrl, "F&I Manager page url check", AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/F and I Manager/F and I Manager Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'F&I Manager', 'header', AfterFailure.CONTINUE)

// Verify tab (text and is active)
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/F and I Manager/Needs Warranty/F and I Manager - Needs Warranty - Tab')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Needs Warranty', 'tab', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'ui-tabs-active', AfterFailure.STOP)

// Verify rows are present
def numberOfRowsShown = CustomKeywords.'opportunities.alertdesk.Common.returnNumberOfDataRows'()

CustomKeywords.'test.Assistant.verifyNotEqual'(numberOfRowsShown, null, "Rows displayed on Needs Warranty tab", AfterFailure.STOP)