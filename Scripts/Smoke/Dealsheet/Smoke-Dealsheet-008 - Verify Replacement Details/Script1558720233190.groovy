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
import test.Assistant.AfterFailure

def testObjectUnderTest

// Login
new Initialize().readySetGo('automation5')

// Open Dealsheet
def dealsheetUrl = CustomKeywords.'dealsheet.Common.getRandomDealsheetURL'()

WebUI.navigateToUrl(dealsheetUrl, FailureHandling.STOP_ON_FAILURE)

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

// Opening Tab
def tabOpened = CustomKeywords.'dealsheet.bottom.Common.openTab'(BottomSectionTabs2.REPLACEMENT_DETAILS)

CustomKeywords.'test.Assistant.verifyTrue'(tabOpened, 'Replacement Details tab opened successfully', AfterFailure.STOP)

// Verify Heading
testObjectUnderTest = findTestObject('Dealsheet/Tabs/Replacement Details/Dealsheet - Tabs - Replacement Details - Heading')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Replacement Vehicle Details', AfterFailure.CONTINUE)

// Verify payment grid has rows
def paymentGrid = CustomKeywords.'dealsheet.bottom.ReplacementDetails.getPaymentGrid'()

CustomKeywords.'test.Assistant.verifyTrue'(paymentGrid.size > 0, 'Payment grid contains rows', AfterFailure.STOP)

// Verify clicking In-Stock Match button opens popup
testObjectUnderTest = findTestObject('Dealsheet/Tabs/Replacement Details/Dealsheet - Tabs - Replacement Details - Buttons - In-Stock Match')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifiedClick'(testObjectUnderTest, AfterFailure.STOP)

// Verify In-Stock Match Heading
testObjectUnderTest = findTestObject('Dealsheet/Tabs/Replacement Details/In-Stock Match/Dealsheet - Bottom - Replacement Details - In-Stock Match - Heading')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'In-Stock Match', AfterFailure.CONTINUE)