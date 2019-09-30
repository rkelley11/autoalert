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

// Login
new Initialize().readySetGo()

def testObjectUnderTest

// Verify Search Quick Link
testObjectUnderTest = findTestObject('Opportunities/Alert Desk/Dashboard/Quick Links/Quick Links - Search')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Search', AfterFailure.CONTINUE)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Search - Left Frame - Search Menu is collapsed
testObjectUnderTest = findTestObject('Search Page/Left Frame/Search - Left Frame - Search Menu')

CustomKeywords.'test.Assistant.waitForElementPresent'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyAttributeContains'(testObjectUnderTest, 'class', 'ng-hide', AfterFailure.CONTINUE)

// Verify Search page Heading
testObjectUnderTest = findTestObject('Search Page/Section/Search/Search Heading')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Search', AfterFailure.CONTINUE)

// Search - Buttons - Search
testObjectUnderTest = findTestObject('Search Page/Buttons/Search - Buttons - Search')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify that there is a total found from search
def isNotNull = (CustomKeywords.'opportunities.alertdesk.Search.returnTotalFoundFromSearch'()) != null

CustomKeywords.'test.Assistant.verifyTrue'(isNotNull, 'Found total from search results', AfterFailure.STOP)