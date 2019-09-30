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

// Go to Activities page
def activitiesUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.ACTIVITIES

WebUI.navigateToUrl(activitiesUrl)

def currentUrl = WebUI.getUrl()

CustomKeywords.'test.Assistant.verifyEqual'(currentUrl, activitiesUrl, "Activities page url check", AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Activities/Activities Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Activities', 'header', AfterFailure.CONTINUE)