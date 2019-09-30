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
import general.Urls
import begin.Initialize

// Login
new Initialize().readySetGo()

def academyLink = findTestObject('Opportunities/Academy/Sub Menu - Academy')

def academyLinkVisible = WebUI.waitForElementVisible(academyLink, 30)

assert academyLinkVisible

def academyLinkText = WebUI.getText(academyLink)

WebUI.verifyEqual(academyLinkText, 'Academy', FailureHandling.CONTINUE_ON_FAILURE)

def academyLinkClicked = CustomKeywords.'general.WrappedSelenium.click'(academyLink)

assert academyLinkClicked

def webinarLink = findTestObject('Opportunities/Academy/Hover Over Dropdown List/Hover Over Webinars Link')
	
def webinarLinkVisible = WebUI.waitForElementVisible(webinarLink, 10)

assert webinarLinkVisible

def webinarLinkText = WebUI.getText(webinarLink)

WebUI.verifyEqual(webinarLinkText, 'Webinars', FailureHandling.CONTINUE_ON_FAILURE)

def webinarLinkClicked = CustomKeywords.'general.WrappedSelenium.click'(webinarLink)

assert webinarLinkClicked

def webinarHeader = findTestObject('Opportunities/Academy/Webinars/Webinars Header')

def webinarHeaderVisible = WebUI.waitForElementVisible(webinarHeader, 10)

WebUI.verifyEqual(webinarHeaderVisible, true, FailureHandling.CONTINUE_ON_FAILURE)

if(webinarHeaderVisible){
	def webinarHeaderText = WebUI.getText(webinarHeader)

	WebUI.verifyEqual(webinarHeaderText, '- Webinars', FailureHandling.CONTINUE_ON_FAILURE)
}

def pageUrl = WebUI.getUrl()

assert pageUrl == (GlobalVariable.Url_Login + Urls.Opportunities.Academy.WEBINARS)

