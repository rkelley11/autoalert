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

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class Modals extends WrappedSelenium{
	
	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def driver, calendar

	def updateMileageSubmitButtonObjName = TestObjectNames.Modals.UpdateMileage.SUBMIT_BUTTON

	def updatePayoffSubmitButtonObjName = TestObjectNames.Modals.SaveAdjustPayoff.SUBMIT_BUTTON

	Modals(){

		this.driver = DriverFactory.getWebDriver()

		this.calendar = new Calendar()
	}

	def submitButtonDisabledStatus(Titles title){

		def disabled, testObjectName, message

		try{

			switch(title){

				case Titles.SAVE_ADJUST_PAYOFF:

					testObjectName = updatePayoffSubmitButtonObjName

					break

				case Titles.UPDATE_MILEAGE:

					testObjectName = updateMileageSubmitButtonObjName

					break

				default:

					throw new Exception("Title '" + (title as String) + ' is unknown.')
			}

			disabled = getAttribute(returnTestObject(testObjectName), "disabled")

			//message = (title as String) + " - Submit button disabled attribute: " + (disabledAttribute as String)

			message = (title as String) + " - Submit button IS" + (disabled ? " " : " NOT ") + "disabled"

			KeywordUtil.logInfo(message)

			/*if(disabledAttribute){
			 disabled = disabledAttribute == 'disabled'
			 }
			 else{
			 disabled = false
			 }*/
		}
		catch (Exception e){

			disabled = false
		}

		/*message = (title as String) + " - Submit button is disabled: " + (disabled as String)
		 KeywordUtil.logInfo(message)*/

		disabled
	}

	enum Titles{

		SAVE_ADJUST_PAYOFF,
		UPDATE_MILEAGE
	}
}
