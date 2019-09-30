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
import sql.Queries

// Login
new Initialize().readySetGo('automation5')

// Open Dealsheet
def entityId = CustomKeywords.'dealsheet.Common.getRandomQueriedDealsheetData'(Queries.Dealsheet.HAS_ALERT_OPP, '_EntityID')

CustomKeywords.'test.Assistant.verifyNotEqual'(entityId, null, 'Entity ID & null', AfterFailure.STOP)

def dealsheetUrl = CustomKeywords.'dealsheet.Common.generateDealsheetUrl'(entityId)
	
WebUI.navigateToUrl(dealsheetUrl, FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'test.Assistant.verifyUrl'(dealsheetUrl, AfterFailure.STOP)

// Wait for the dealsheet to load
def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

CustomKeywords.'test.Assistant.verifyTrue'(dealsheetLoaded, 'Dealsheet successfully loaded', AfterFailure.STOP)

// Opening Tab
def tabOpened = CustomKeywords.'dealsheet.bottom.Common.openTab'(BottomSectionTabs2.ALERTS_AND_SCRIPTS)

CustomKeywords.'test.Assistant.verifyTrue'(tabOpened, 'Alerts & Scripts section successfully opened', AfterFailure.STOP)

// Verify Heading
def testObjectUnderTest = findTestObject('Dealsheet/Tabs/Alerts and Scripts/Opportunities/Dealsheet - Tabs - Alerts and Scripts - Opportunities - Heading')

CustomKeywords.'test.Assistant.waitForElementVisible'(testObjectUnderTest, 10, AfterFailure.STOP)

CustomKeywords.'test.Assistant.verifyText'(testObjectUnderTest, 'Opportunities', 'header', AfterFailure.CONTINUE)

// Verify each opportunity
CustomKeywords.'dealsheet.bottom.AlertsAndScripts.verifyEachOpportunity'()
