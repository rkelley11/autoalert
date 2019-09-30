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
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import begin.Initialize

// Login
new Initialize().readySetGo()

CustomKeywords.'opportunities.DealerSelect.selectDealer'('CHDEMO')

WebUI.waitForElementVisible(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 30)

WebUI.sendKeys(findTestObject('Opportunities/Customer Search/Customer Search Textbox'), 
    GlobalVariable.Dealsheet_Search + Keys.RETURN)

def customerNameFound = CustomKeywords.'opportunities.alertdesk.QuickSearch.getNameFromSearchResult'(1)

spaceIndex = GlobalVariable.Dealsheet_Search_Fullname.toLowerCase().indexOf(' ')

expectedFirstName = GlobalVariable.Dealsheet_Search_Fullname.toLowerCase().substring(0, spaceIndex)

expectedLastName = GlobalVariable.Dealsheet_Search_Fullname.toLowerCase().substring(spaceIndex + 1, GlobalVariable.Dealsheet_Search_Fullname.length())

KeywordUtil.logInfo("Customer Name: " + customerNameFound)

KeywordUtil.logInfo("Expected to include - First Name: " + expectedFirstName + " (Length: " + (expectedFirstName.length() as String) + ")")

KeywordUtil.logInfo("Expected to include - Last Name : " + expectedLastName + " (Length: " + (expectedLastName.length() as String) + ")")

assert customerNameFound.toLowerCase().contains(expectedFirstName) && customerNameFound.toLowerCase().contains(expectedLastName)