package general

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
import java.time.LocalDate as LocalDate
import java.time.LocalDateTime as LocalDateTime

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

import general.Utilities
import general.WrappedSelenium

public class Calendar extends WrappedSelenium {

	def driver

	def byAvailable = By.cssSelector("[role=\"gridcell\"] button:not([disabled=\"disabled\"])")

	def byNotAvailable = By.cssSelector("[role=\"gridcell\"] button[disabled=\"disabled\"]")

	def byAllDates = By.cssSelector("[role=\"gridcell\"] button")

	def byMonthYearHeading = By.cssSelector("[role=\"presentation\"] thead [role=\"heading\"]")

	def byPreviousMonthArrow = By.cssSelector("[role=\"presentation\"] thead [ng-click=\"move(-1)\"]")

	def byNextMonthArrow = By.cssSelector("[role=\"presentation\"] thead [ng-click=\"move(1)\"]")

	Calendar(){

		this.driver = DriverFactory.getWebDriver()
	}

	@Keyword
	def clickDate(LocalDate date){

		def dateInfo = [:]

		def dateElement

		if(date){

			dateElement = findDate(date)

			dateInfo["date"] = date
		}
		else{

			dateElement = findAvailableDate(date)

			dateInfo["date"] = getText(dateElement)
		}

		dateInfo["available"] = dateElement ? isDateAvailable(dateElement) : false

		if(dateInfo["available"]){

			click(dateElement)

			KeywordUtil.logInfo("Clicked on selected available date: " + dateInfo["date"] as String)
		}
		else{

			KeywordUtil.logInfo("Unable to click on selected unavailable date: " + dateInfo["date"] as String)
		}

		dateInfo
	}

	def findAvailableDate(LocalDate date){

		def availableDates, availableDateText, selectedDate, message, day

		def startOfMonthFound = false

		def isFutureDate = LocalDate.now() < date

		KeywordUtil.logInfo("Date to be searched: " + date as String)

		date ? beOnCorrectMonth(date) : null

		availableDates = findElements(this.byAvailable)

		if(date){

			KeywordUtil.logInfo("Attempting to find desired date (" + (date as String) + ") in available date selection")

			for (int i = 0; i < availableDates.size(); i++){

				availableDateText = getText(availableDates[i])

				if(!startOfMonthFound){

					startOfMonthFound = availableDateText == "01" ? true : false
				}

				if(startOfMonthFound || isFutureDate){

					day = (DateTimeFormatter.ofPattern('dd')).format(date)

					if(availableDateText == day){

						selectedDate = availableDates[i]

						break
					}
				}
			}

			message = (selectedDate) ? "Date found: " : "Date not found: "

			KeywordUtil.logInfo(message + date)
		}
		else{

			KeywordUtil.logInfo("Selecting a random date in available date selection")

			def randomNum = (new Random()).nextInt(availableDates.size())

			selectedDate = availableDates[randomNum]
		}

		selectedDate
	}

	def findDate(LocalDate date){

		def allDates, dateText, selectedDate, message, day, successfullyOnCorrectCalendar

		def startOfMonthFound = false

		KeywordUtil.logInfo("Date to be searched: " + date as String)

		successfullyOnCorrectCalendar = date ? beOnCorrectMonth(date) : true

		if (successfullyOnCorrectCalendar){

			allDates = findElements(this.byAllDates)

			KeywordUtil.logInfo("Attempting to find desired date (" + (date as String) + ") in available date selection")

			for (int i = 0; i < allDates.size(); i++){

				dateText = getText(allDates[i])

				if(!startOfMonthFound){

					startOfMonthFound = dateText == "01" ? true : false
				}

				if(startOfMonthFound){

					day = (DateTimeFormatter.ofPattern('dd')).format(date)

					if(dateText == day){

						selectedDate = allDates[i]

						break
					}
				}
			}
		}

		message = (selectedDate) ? "Date found: " : "Date not found: "

		KeywordUtil.logInfo(message + date)

		selectedDate
	}

	def beOnCorrectMonth(date){

		def successful = true

		def desiredMonth = (DateTimeFormatter.ofPattern('MMMM')).format(date)

		def desiredYear4Digits = (DateTimeFormatter.ofPattern('yyyy')).format(date)

		def desiredYear2Digits = (DateTimeFormatter.ofPattern('yy')).format(date)

		def desiredMonthYear = (DateTimeFormatter.ofPattern('MMMM yyyy')).format(date)

		def currentlySelectedMonthYearHeading = findElement(byMonthYearHeading)

		def currentlySelectedMonthYear = getText(currentlySelectedMonthYearHeading)

		def spaceIndex = currentlySelectedMonthYear.indexOf(" ")

		def currentlySelectedMonth = currentlySelectedMonthYear.substring(0, spaceIndex)

		def currentlySelectedYear = currentlySelectedMonthYear.substring(spaceIndex + 1, currentlySelectedMonthYear.size())

		def availableYears, availableYearText, selectedYear, availableMonths, availableMonthText, selectedMonth, message

		KeywordUtil.logInfo("Desired month & year: " + desiredMonthYear)

		KeywordUtil.logInfo("Current month & year: " + currentlySelectedMonthYear)

		KeywordUtil.logInfo("Desired & Current month & year match: " + (desiredMonthYear == currentlySelectedMonthYear) as String)

		if (desiredMonthYear != currentlySelectedMonthYear){

			click(byMonthYearHeading)

			if(desiredYear4Digits != currentlySelectedYear){

				click(byMonthYearHeading)

				availableYears = findElements(this.byAvailable)

				KeywordUtil.logInfo("Attempting to find desired year (" + (desiredYear4Digits as String) + ") in available year selection")

				for (int i = 0; i < availableYears.size(); i++){

					availableYearText = getText(availableYears[i])

					KeywordUtil.logInfo("Desired year  : " + desiredYear4Digits)

					KeywordUtil.logInfo("Available year: " + availableYearText)

					KeywordUtil.logInfo("Match         : " + (availableYearText == desiredYear4Digits) as String)

					if(availableYearText == desiredYear4Digits){

						selectedYear = availableYears[i]

						click(selectedYear)

						break
					}
				}

				message = (selectedYear) ? "Year available: " : "Year not available: "

				KeywordUtil.logInfo(message + desiredYear4Digits as String)
			}

			availableMonths = findElements(this.byAvailable)

			KeywordUtil.logInfo("Attempting to find desired month (" + (desiredMonth as String) + ") in available month selection")

			for (int i = 0; i < availableMonths.size(); i++){

				availableMonthText = getText(availableMonths[i])

				if(availableMonthText == desiredMonth){

					selectedMonth = availableMonths[i]

					click(selectedMonth)

					break
				}
			}

			if (selectedMonth){

				message = "Month available: "

				successful = true
			}
			else{

				message = "Month not available: "

				successful = false

				// Attempting to reselect the current month
				for (int i = 0; i < availableMonths.size(); i++){

					availableMonthText = getText(availableMonths[i])

					if(availableMonthText == currentlySelectedMonth){

						click(availableMonths[i])

						break
					}
				}
			}

			KeywordUtil.logInfo(message + desiredMonth)
		}

		successful
	}

	def beOnCorrectMonthOLD(date){

		def successful = true

		def desiredMonth = (DateTimeFormatter.ofPattern('MMMM')).format(date)

		def desiredYear4Digits = (DateTimeFormatter.ofPattern('yyyy')).format(date)

		def desiredYear2Digits = (DateTimeFormatter.ofPattern('yy')).format(date)

		def desiredMonthYear = (DateTimeFormatter.ofPattern('MMMM yyyy')).format(date)

		def currentlySelectedMonthYearHeading = findElement(byMonthYearHeading)

		def currentlySelectedMonthYear = getText(currentlySelectedMonthYearHeading)

		def spaceIndex = currentlySelectedMonthYear.indexOf(" ")

		def currentlySelectedMonth = currentlySelectedMonthYear.substring(0, spaceIndex)

		def currentlySelectedYear = currentlySelectedMonthYear.substring(spaceIndex + 1, currentlySelectedMonthYear.size())

		def availableYears, availableYearText, selectedYear, availableMonths, availableMonthText, selectedMonth, message

		KeywordUtil.logInfo("Desired month & year: " + desiredMonthYear)

		KeywordUtil.logInfo("Current month & year: " + currentlySelectedMonthYear)

		KeywordUtil.logInfo("Desired & Current month & year match: " + (desiredMonthYear == currentlySelectedMonthYear) as String)

		if (desiredMonthYear != currentlySelectedMonthYear){

			click(byMonthYearHeading)

			if(desiredYear4Digits != currentlySelectedYear){

				click(byMonthYearHeading)

				availableYears = findElements(this.byAvailable)

				KeywordUtil.logInfo("Attempting to find desired year (" + (desiredYear4Digits as String) + ") in available year selection")

				for (int i = 0; i < availableYears.size(); i++){

					availableYearText = getText(availableYears[i])

					KeywordUtil.logInfo("Desired year  : " + desiredYear4Digits)

					KeywordUtil.logInfo("Available year: " + availableYearText)

					KeywordUtil.logInfo("Match         : " + (availableYearText == desiredYear4Digits) as String)

					if(availableYearText == desiredYear4Digits){

						selectedYear = availableYears[i]

						click(selectedYear)

						break
					}
				}

				message = (selectedYear) ? "Year available: " : "Year not available: "

				KeywordUtil.logInfo(message + desiredYear4Digits as String)
			}

			availableMonths = findElements(this.byAvailable)

			KeywordUtil.logInfo("Attempting to find desired month (" + (desiredMonth as String) + ") in available month selection")

			for (int i = 0; i < availableMonths.size(); i++){

				availableMonthText = getText(availableMonths[i])

				if(availableMonthText == desiredMonth){

					selectedMonth = availableMonths[i]

					click(selectedMonth)

					break
				}
			}

			if (selectedMonth){

				message = "Month available: "

				successful = true
			}
			else{

				message = "Month not available: "

				successful = false
			}

			KeywordUtil.logInfo(message + desiredMonth)
		}

		successful
	}

	@Keyword
	def isDateAvailable(LocalDate date){

		def logHeader

		def dateAvailable = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def foundDate = findDate(date)

			if(foundDate){

				dateAvailable = isDateAvailable(foundDate)
			}

			KeywordUtil.logInfo(logHeader + "The date, " + (date as String) + ", IS" + (dateAvailable ? " " : " NOT ") + "available")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		dateAvailable
	}

	def isDateAvailable(WebElement element){

		def logHeader

		def dateAvailable = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def attributeValue = getAttribute(element, "disabled")

			def status = attributeValue ? "is unavailable" : "is available"

			KeywordUtil.logInfo(logHeader + "The date " + status)

			dateAvailable = attributeValue ? false : true
		}
		catch (Exception e){

			KeywordUtil.logInfo("Exception thrown.  Assumption is date is available.")

			dateAvailable = true
		}

		dateAvailable
	}

	def isDateAvailableOLD(LocalDate date){

		def dateAvailable = false

		def foundDate = findDate(date)

		if(foundDate){

			dateAvailable = isDateAvailable(foundDate)
		}

		dateAvailable
	}

	def isDateAvailableOLD(WebElement element){

		def dateAvailable = false

		try{

			def attributeValue = getAttribute(element, "disabled")

			def status = attributeValue ? "is unavailable." : "is available."

			KeywordUtil.logInfo("The date " + status)

			dateAvailable = attributeValue ? false : true
		}
		catch (Exception e){

			KeywordUtil.logInfo("Exception thrown.  Assumption is date is available.")

			dateAvailable = true
		}

		dateAvailable
	}
}