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

// Go to Opportunities page
def opportunitiesUrl = GlobalVariable.Url_Login + Urls.Opportunities.AlertDesk.OPPORTUNITIES

WebUI.navigateToUrl(opportunitiesUrl)

def currentUrl = WebUI.getUrl()

CustomKeywords.'test.Assistant.verifyEqual'(currentUrl, opportunitiesUrl, "Opportunities page url check", AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Opportunities/Opportunities Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Opportunities', 'header', AfterFailure.CONTINUE)

// Verify a dealsheet can be opened from search results
CustomKeywords.'opportunities.alertdesk.Opportunities.clickFirstRow'()

CustomKeywords.'test.Assistant.verifyTrue'(CustomKeywords.'dealsheet.Common.waitToLoad'(), "Dealsheet successfully loaded", AfterFailure.STOP)