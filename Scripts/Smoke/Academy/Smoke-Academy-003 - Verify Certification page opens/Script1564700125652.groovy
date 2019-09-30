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

WebUI.waitForElementVisible(findTestObject('Opportunities/Academy/Sub Menu - Academy'), 
    30)

elementText = WebUI.getText(findTestObject('Opportunities/Academy/Sub Menu - Academy'))

assert elementText == 'Academy'

elementIsClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Academy/Sub Menu - Academy'))

elementIsPresentAndVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Academy/Hover Over Dropdown List/Hover Over Certification Link'), 
    10)

assert elementIsPresentAndVisible

elementText = WebUI.getText(findTestObject('Opportunities/Academy/Hover Over Dropdown List/Hover Over Certification Link'))

assert elementText == 'Certification'

elementWasClicked = CustomKeywords.'general.WrappedSelenium.click'(findTestObject('Opportunities/Academy/Hover Over Dropdown List/Hover Over Certification Link'))

assert elementWasClicked

//'Used to retract mouse over popup menu'
//WebUI.mouseOver(findTestObject('Header/AutoAlert Logo/AutoAlert Logo Image'))
elementIsPresentAndVisible = WebUI.waitForElementVisible(findTestObject('Opportunities/Academy/Certification/Certification Header'), 
    10)

assert elementIsPresentAndVisible

elementText = WebUI.getText(findTestObject('Opportunities/Academy/Certification/Certification Header'))

assert elementText == '- Certification'

pageUrl = WebUI.getUrl()

assert pageUrl == (GlobalVariable.Url_Login + Urls.Opportunities.Academy.CERTIFICATION)

