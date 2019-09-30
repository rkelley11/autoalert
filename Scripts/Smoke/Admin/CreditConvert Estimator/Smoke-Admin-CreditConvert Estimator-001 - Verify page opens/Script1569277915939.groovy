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

// Login
new Initialize().readySetGo('automation1')

def testObjectUnderTest

// Go to the Search page
def searchUrl = GlobalVariable.Url_Login + Urls.Opportunities.Admin.CREDITCONVERT_ESTIMATOR

WebUI.navigateToUrl(searchUrl)

CustomKeywords.'test.Assistant.verifyEqual'(WebUI.getUrl(), searchUrl, 'Navigated successfully to the Admin/CreditConvert Estimator page url', AfterFailure.STOP)

// CreditConvert Header
testObjectUnderTest = findTestObject('Opportunities/Admin/CreditConvert Estimator/CreditConvert Header')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'CreditConvert Estimator', AfterFailure.CONTINUE)

// CreditConvert Estimator - Radio Button - Manual
testObjectUnderTest = findTestObject('Opportunities/Admin/CreditConvert Estimator/CreditConvert Estimator - Radio Button - Manual')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

// CreditConvert Estimator - Radio Button - Automated
testObjectUnderTest = findTestObject('Opportunities/Admin/CreditConvert Estimator/CreditConvert Estimator - Radio Button - Automated')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.CONTINUE)

// CreditConvert Calculate Button
testObjectUnderTest = findTestObject('Opportunities/Admin/CreditConvert Estimator/CreditConvert Calculate Button')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyAttribute'(testObjectUnderTest, 'value', 'Calculate', AfterFailure.CONTINUE)