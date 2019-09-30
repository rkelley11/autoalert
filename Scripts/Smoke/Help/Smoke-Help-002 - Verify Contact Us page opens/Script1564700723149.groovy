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
import general.Urls

// Login
new Initialize().readySetGo()

// Verify & Select Help Link
def helpLink = findTestObject('Opportunities/Help/Sub Menu - Help Link')

def helpLinkVisible = WebUI.waitForElementVisible(helpLink, 10)

WebUI.verifyEqual(helpLinkVisible, true, FailureHandling.STOP_ON_FAILURE)

if (helpLinkVisible) {
	def helpLinkText = WebUI.getText(helpLink)
	
	WebUI.verifyEqual(helpLinkText, 'Help', FailureHandling.CONTINUE_ON_FAILURE)

	def helpLinkClicked = CustomKeywords.'general.WrappedSelenium.click'(helpLink)
	
	WebUI.verifyEqual(helpLinkClicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Verify & Select Contact Us Link
def contactUsLink = findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over Contact Us Link')

def contactUsLinkVisible = WebUI.waitForElementVisible(contactUsLink, 10)

WebUI.verifyEqual(contactUsLinkVisible, true, FailureHandling.STOP_ON_FAILURE)

if (contactUsLinkVisible) {
	def contactUsLinkText = WebUI.getText(contactUsLink)
	
	WebUI.verifyEqual(contactUsLinkText, 'Contact Us', FailureHandling.CONTINUE_ON_FAILURE)

	def contactUsLinkClicked = CustomKeywords.'general.WrappedSelenium.click'(contactUsLink)
	
	WebUI.verifyEqual(contactUsLinkClicked, true, FailureHandling.STOP_ON_FAILURE)
}

// Click to remove dropdown menu
CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Stock Number Search/Stock Number Search Textbox'))

// Verify Contact Us address
def contactUsAddress = findTestObject('Opportunities/Help/Contact Us/Contact Us Address')

def contactUsAddressVisible = WebUI.waitForElementVisible(contactUsAddress, 10)

WebUI.verifyEqual(contactUsLinkVisible, true, FailureHandling.STOP_ON_FAILURE)

if (contactUsLinkVisible) {
	def contactUsAddressText = WebUI.getText(contactUsAddress)
	
	WebUI.verifyEqual(contactUsAddressText.contains('AutoAlert, LLC'), true, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.verifyEqual(contactUsAddressText.contains('114 W. 11th St., Suite 700'), true, FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.verifyEqual(contactUsAddressText.contains('Kansas City, MO 64105'), true, FailureHandling.CONTINUE_ON_FAILURE)
}

// Verify page is on correct url
def actualUrl = WebUI.getUrl()

def expectedUrl = GlobalVariable.Url_Login + Urls.Opportunities.Help.CONTACT_US

WebUI.verifyEqual(actualUrl, expectedUrl, FailureHandling.STOP_ON_FAILURE)