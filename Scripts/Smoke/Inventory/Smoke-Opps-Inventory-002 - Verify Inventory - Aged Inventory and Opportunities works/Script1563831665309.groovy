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
import opportunities.preowned.Common as Common
import opportunities.preowned.UsedCarInventoryAndOpportunities as UsedCarInventoryAndOpportunities
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import begin.Initialize
import general.Urls

// Login
new Initialize().readySetGo()

// Select dealers
def dealersSelected = CustomKeywords.'opportunities.DealerSelect.selectDealer'(['CHDEMO','MBDEMO'])

assert dealersSelected

// Go to inventory dashboard
def inventoryDashboardUrl = GlobalVariable.Url_Login + Urls.Opportunities.INVENTORY

WebUI.navigateToUrl(inventoryDashboardUrl)

def currentUrl = WebUI.getUrl()

assert currentUrl == inventoryDashboardUrl

// Verify header
def header = findTestObject('Opportunities/Inventory/Inventory Dashboard Header')

def headerVisible = WebUI.waitForElementVisible(header, 10)

assert headerVisible

def headerText = WebUI.getText(header)

WebUI.verifyEqual(headerText, 'Inventory Dashboard', FailureHandling.CONTINUE_ON_FAILURE)

// Verify widget heading
def widgetHeading = findTestObject('Opportunities/Inventory/Aged Inventory and Opportunities/Inventory - Aged Inventory and Opportunities - Heading')

def widgetHeadingVisible = WebUI.waitForElementVisible(widgetHeading, 10)

assert widgetHeadingVisible

def widgetHeadingText = WebUI.getText(widgetHeading)

WebUI.verifyEqual(widgetHeadingText, 'Aged Inventory and Opportunities', FailureHandling.CONTINUE_ON_FAILURE)

// Select colum
def selectionInfo = [('column') : UsedCarInventoryAndOpportunities.Columns.MORE_THAN_60_DAYS]

def successfullyClickedColumnInWidget = CustomKeywords.'opportunities.preowned.Common.clickLinkInWidget'(Common.PreownedDashboardWidgets.USED_CAR_INVENTORY_AND_OPPORTUNITIES, 
    selectionInfo)

assert successfullyClickedColumnInWidget

// Check the Find a Buyer tab
def findABuyerTab = findTestObject('Opportunities/Alert Desk/Inventory/Find a Buyer/Inventory - Find a Buyer - Tab')

// Had problems with stale element exceptions looking for tab, so trying this
def findABuyerTabVisible = false

def attempts = 0

while(!findABuyerTabVisible && attempts < 10){
	
	try{
		
		attempts += 1
		
		KeywordUtil.logInfo('Attempt #' + (attempts as String) + ' at finding the "Find a Buyer" tab')
		
		//throw new Exception('Forcing exception to test - Attempt #' + (attempts as String))
		
		findABuyerTabVisible = WebUI.waitForElementVisible(findABuyerTab, 20)
	}
	catch (Exception e){

		KeywordUtil.markWarning(e.getMessage())
		
		WebUI.delay(1) // Wait 1 second before making next attempt
	}	
}

assert findABuyerTabVisible

def findABuyerTabClasses = WebUI.getAttribute(findABuyerTab, 'class')

KeywordUtil.logInfo('Classes: ' + findABuyerTabClasses)

assert findABuyerTabClasses.contains('ui-tabs-active')

