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
import java.time.LocalDateTime as LocalDateTime
import begin.Initialize

// Login
new Initialize().readySetGo()

def shouldBeAvailable = CustomKeywords.'opportunities.LiveSupport.shouldBeAvailable'()

def elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Help/Sub Menu - Help Link'), 
    30)

assert elementVisible

def elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Help/Sub Menu - Help Link'))

assert elementClicked

elementVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over Contact Us Link'), 
    10)

assert elementVisible

elementClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Help/Hover Over Dropdown List/Hover Over Contact Us Link'))

assert elementClicked

'Click to remove dropdown menu'
CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Stock Number Search/Stock Number Search Textbox'))

def linkVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Help/Contact Us/Contact Us - Live Chat Link'), 5)

def liveSupportIsGood

if (linkVisible) {
    liveSupportIsGood = CustomKeywords.'opportunities.LiveSupport.doesLiveSupportOpenProperly'(findTestObject('Opportunities/Help/Contact Us/Contact Us - Live Chat Link'))
}

assert (!(shouldBeAvailable) && !(linkVisible)) || liveSupportIsGood

