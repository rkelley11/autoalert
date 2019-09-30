package dealsheet

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import java.time.format.DateTimeFormatter as DateTimeFormatter
import java.text.DecimalFormat as DecimalFormat
import java.time.LocalDate as LocalDate
import java.time.LocalDateTime as LocalDateTime
import java.util.concurrent.ThreadLocalRandom as ThreadLocalRandom

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class Payoff extends WrappedSelenium{

	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def driver, calendar

	def residualAmountObjName = TestObjectNames.BottomSection.ExistingVehicleDetails.Residual.AMOUNT

	Payoff(){

		this.driver = DriverFactory.getWebDriver()

		this.calendar = new Calendar()
	}

	/**
	 * Returns a random payoff amount to be entered after determining minimum payoff amount.
	 */
	@Keyword
	def getPayoff(){

		def residualAvailable, minimumPayoff, minimumPayoffInteger, payoff

		residualAvailable = waitForElementVisible(returnTestObject(residualAmountObjName), 2)

		minimumPayoff

		if (residualAvailable){

			//minimumPayoff = WebUI.getText(returnTestObject(residualAmountObjName))

			minimumPayoff = getText(returnTestObject(residualAmountObjName))
		}
		else{

			minimumPayoff = "1"
		}

		minimumPayoffInteger = ((minimumPayoff.replaceAll('[$,]', '')) as Integer)

		payoff = ThreadLocalRandom.current().nextInt(minimumPayoffInteger, minimumPayoffInteger + 99999)

		KeywordUtil.logInfo("Payoff: " + payoff as String)

		payoff
	}
}
