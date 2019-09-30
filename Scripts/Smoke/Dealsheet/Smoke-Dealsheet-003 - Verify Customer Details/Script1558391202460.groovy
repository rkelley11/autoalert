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
import opportunities.alertdesk.dashboard.OpportunitiesDashboard.Widgets as Widgets
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import dealsheet.top.Icons as Icons
import org.openqa.selenium.Keys as Keys
import begin.Initialize
import test.Assistant.AfterFailure

// Login
new Initialize().readySetGo('automation5')

// Open Dealsheet
def dealsheetUrl = CustomKeywords.'dealsheet.Common.getRandomDealsheetURL'()

CustomKeywords.'test.Assistant.verifyNotEqual'(dealsheetUrl, null, 'Returned dealsheet url is not null', AfterFailure.STOP)

WebUI.navigateToUrl(dealsheetUrl, FailureHandling.STOP_ON_FAILURE)

def dealsheetLoaded = CustomKeywords.'dealsheet.Common.waitToLoad'()

assert dealsheetLoaded

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Top Section/Customer Profile/Customer Profile Name'), 
    10)

assert elementVisible

actualDisplayedCustomerName = WebUI.getText(findTestObject('Dealsheet/Top Section/Customer Profile/Customer Profile Name'))

// Icon
def iconClicked = CustomKeywords.'dealsheet.top.Icons.click'(Icons.Names.CUSTOMER_EDIT)

assert iconClicked

// Get customer name from client edit
def clientEditCustomerNameFirst = findTestObject('Object Repository/Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Name - First')
def clientEditCustomerNameMiddle = findTestObject('Object Repository/Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Name - Middle')
def clientEditCustomerNameLast = findTestObject('Object Repository/Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Name - Last')

def clientEditCustomerNameFirstVisible = WebUI.waitForElementVisible(clientEditCustomerNameFirst, 10)

WebUI.verifyEqual(clientEditCustomerNameFirstVisible, true, FailureHandling.STOP_ON_FAILURE)

def clientEditCustNameFirst = WebUI.getAttribute(clientEditCustomerNameFirst, 'value')
KeywordUtil.logInfo('First name: ' + (clientEditCustNameFirst as String) + ', size: ' + (clientEditCustNameFirst ? clientEditCustNameFirst.length() : 0))

def clientEditCustNameMiddle = WebUI.getAttribute(clientEditCustomerNameMiddle, 'value')
KeywordUtil.logInfo('Middle name: ' + (clientEditCustNameMiddle as String) + ', size: ' + (clientEditCustNameMiddle ? clientEditCustNameMiddle.length() : 0))

def clientEditCustNameLast = WebUI.getAttribute(clientEditCustomerNameLast, 'value')
KeywordUtil.logInfo('Last name: ' + (clientEditCustNameLast as String) + ', size: ' + (clientEditCustNameLast ? clientEditCustNameLast.length() : 0))

// Determining how to space the full name may already be done in the framework
def firstMiddleNameSpace = clientEditCustNameFirst != '' && clientEditCustNameMiddle != '' ? ' ' : ''
KeywordUtil.logInfo('First/Middle name space: ' + (firstMiddleNameSpace ? firstMiddleNameSpace.length() : 0))
	
def middleLastNameSpace = (clientEditCustNameMiddle != '' && clientEditCustNameLast != '') || clientEditCustNameFirst != '' ? ' ' : ''
KeywordUtil.logInfo('Middle/Last name space: ' + (middleLastNameSpace ? middleLastNameSpace.length() : 0))

def fullClientEditName = clientEditCustNameFirst.toUpperCase() + firstMiddleNameSpace + clientEditCustNameMiddle.toUpperCase() + middleLastNameSpace + clientEditCustNameLast.toUpperCase()

KeywordUtil.logInfo('Client edit customer name: ' + fullClientEditName)

WebUI.verifyEqual(actualDisplayedCustomerName, fullClientEditName, FailureHandling.CONTINUE_ON_FAILURE)

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Title'), 
    10)

def title = WebUI.getText(findTestObject('Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Title'))

WebUI.verifyEqual(title, 'Client Edit', FailureHandling.CONTINUE_ON_FAILURE)

elementVisible = WebUI.waitForElementVisible(findTestObject('Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Button - Cancel'), 
    10)

CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Button - Cancel'))

sleep(2000) // Wait for client edit popup to close

def elementNotPresent = WebUI.waitForElementNotPresent(findTestObject('Dealsheet/Top Section/Customer Profile/Client Edit/Client Edit - Button - Cancel'), 5)

WebUI.verifyEqual(elementNotPresent, true, FailureHandling.CONTINUE_ON_FAILURE)