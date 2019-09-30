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
import begin.Initialize
import test.Assistant.AfterFailure
import general.Urls

// Login
new Initialize().readySetGo()

// Navigate to page
def intendedUrl = GlobalVariable.Url_Login + Urls.Opportunities.DataEntry.REPLACEMENT_OFFERS

WebUI.navigateToUrl(intendedUrl, FailureHandling.STOP_ON_FAILURE)

def currentUrl = WebUI.getUrl()

CustomKeywords.'test.Assistant.verifyEqual'(currentUrl, intendedUrl, "Replacements page url check", AfterFailure.STOP)

// Verify header
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Replacement Offers/Replacement Offers Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Replacement Offers', 'header', AfterFailure.CONTINUE)

// Verify tab
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Replacement Offers/Advanced Tab/Advanced Tab Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'ADVANCED', 'tab', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

// Verify replacements container is present
testObjectUnderTest = findTestObject('Opportunities/Data Entry/Replacement Offers/Advanced Tab/Advanced Tab Container')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)