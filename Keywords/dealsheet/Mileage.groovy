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

import java.util.logging.*
import java.time.format.DateTimeFormatter as DateTimeFormatter
import java.text.DecimalFormat as DecimalFormat
import java.time.LocalDate as LocalDate
import java.time.LocalDateTime as LocalDateTime

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.*

public class Mileage extends WrappedSelenium{
	
	/*
	 * Many areas on this page refer to the TestObjectNames class which has been in the process of being re-factored out.
	 * Instead of using the TestObjectNames class to store the names and then using a custom returnTestObject() method, it is
	 * being replaced with the findTestObject() method from Katalon because Katalon will automatically update the objects path if changes
	 * in structure are made, but only if used with this method.  Is the path is stored in a string and used with findTestObject then
	 * it does not get updated which was the main reason why the original returnTestObject() method was created.
	 */

	def utils, driver, calendar

	def updateMileageInputObjName = TestObjectNames.Modals.UpdateMileage.MILEAGE_INPUT

	def updateMileageReadOnButtonObjName = TestObjectNames.Modals.UpdateMileage.CALENDAR_BUTTON

	def updateMileageSubmitButtonObjName = TestObjectNames.Modals.UpdateMileage.SUBMIT_BUTTON

	def updateMileageReadOnObjName = TestObjectNames.Modals.UpdateMileage.READ_ON

	Mileage(){

		this.utils = new Utilities()

		this.driver = DriverFactory.getWebDriver()

		this.calendar = new Calendar()
	}

	@Keyword
	def enterUpdatedMileage(mileage, date, InputMethod inputMethod){

		def mileageInfo = [:]

		def calendar = [:]

		def formatter = DateTimeFormatter.ofPattern('MM/dd/yyyy')

		mileageInfo["mileage"] = (mileage) ? mileage : getRandomMileage(999999)

		sendKeys(returnTestObject(updateMileageInputObjName), mileageInfo["mileage"] as String)

		switch (inputMethod){

			case InputMethod.TYPE:

				calendar["date"] = date

				calendar["available"] = 'unknown' //Placeholder.  Need to determine if available at some point.

				mileageInfo["calendar"] = calendar

				def testObject = returnTestObject(updateMileageReadOnObjName)

				WebUI.clearText(testObject)

				WebUI.sendKeys(testObject, (formatter.format(date) as String) + Keys.TAB)

				break

			case InputMethod.CALENDAR:

				click(returnTestObject(updateMileageReadOnButtonObjName))

				mileageInfo["calendar"] = this.calendar.clickDate(date)

				break

			default:

				break
		}

		KeywordUtil.logInfo("Mileage info being returned: " + mileageInfo as String)

		mileageInfo
	}

	/**
	 * Returns the date the mileage was last read on if found within the string sent.
	 * @param mileageReading - Expecting to receive a string such as "6,000 (4,973 read on 04/24/2018)"
	 * @return A string representing the date the mileage was last read will be returned if found
	 */
	@Keyword
	def getLastMileageUpdateReadOnDate(String mileageReading){

		def dateReadOn, lastSpaceIndex

		try{

			lastSpaceIndex = mileageReading.lastIndexOf(" ")

			dateReadOn = mileageReading.substring(lastSpaceIndex + 1, mileageReading.size() - 1)

			KeywordUtil.logInfo("Mileage update read on date found: " + dateReadOn)
		}
		catch (Exception e){

			KeywordUtil.markError("Exception thrown when attempting to get date from: " + mileageReading as String)

			dateReadOn = e
		}

		dateReadOn
	}

	def getRandomMileage(Integer maximum){

		(new Random()).nextInt(maximum)
	}

	def determineExpectedMileageInfo(LinkedHashMap mileageInfo){

		def mileageFormatter = new DecimalFormat('#,###')

		def formattedNumber = mileageFormatter.format(mileageInfo['mileage'])

		def expectedMileage = '(' + (formattedNumber as String) + ' read on ' // Omitting date due to production using future date (Richard ok'd not a defect)

		KeywordUtil.logInfo("Expected mileage: " + expectedMileage)

		expectedMileage
	}

	def submitButtonDisabledStatus(){

		def disabled

		try{

			def disabledAttribute = getAttribute(returnTestObject(updateMileageSubmitButtonObjName), "disabled")

			KeywordUtil.logInfo("Submit button disabled attribute: " + disabledAttribute as String)

			if(disabledAttribute){

				disabled = disabledAttribute == 'disabled'
			}
			else{

				disabled = false
			}
		}
		catch (Exception e){

			disabled = false
		}

		KeywordUtil.logInfo("Submit button disabled status: " + disabled as String)

		disabled
	}

	enum InputMethod{

		TYPE,
		CALENDAR
	}
}
