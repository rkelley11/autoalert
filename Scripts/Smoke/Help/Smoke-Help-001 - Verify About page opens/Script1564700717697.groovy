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

WebUI.waitForElementVisible(findTestObject('Opportunities/Help/Sub Menu - Help Link'), 
    30)

elementText = WebUI.getText(findTestObject('Opportunities/Help/Sub Menu - Help Link'))

assert elementText == 'Help'

elementIsClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Help/Sub Menu - Help Link'))

elementIsPresentAndVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over About Link'), 
    10)

assert elementIsPresentAndVisible

elementText = WebUI.getText(findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over About Link'))

assert elementText == 'About'

elementWasClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over About Link'))

assert elementWasClicked

'Click to remove dropdown menu'
CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Stock Number Search/Stock Number Search Textbox'))

elementIsPresentAndVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Help/About/Portfolio Management Header'), 
    10)

assert elementIsPresentAndVisible

elementText = WebUI.getText(findTestObject('Opportunities/Help/About/Portfolio Management Header'))

assert elementText == 'AutoAlert Client Portfolio Management'

pageUrl = WebUI.getUrl()

assert pageUrl == (GlobalVariable.Url_Login + Urls.Opportunities.Help.ABOUT)

