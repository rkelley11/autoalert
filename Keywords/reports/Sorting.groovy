package reports

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

import java.time.*

import com.kms.katalon.core.util.KeywordUtil

import general.*
import general.Enums.*

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

public class Sorting extends WrappedSelenium{

	def desiredColumn, sortingArrow

	/**
	 * 
	 * @param testObject - The TestObject to be verified
	 * @param columnNumber - The column number that needs to have sorting verified
	 * @return returns true if toggle is successful
	 */
	@Keyword
	def verifySorting(TestObject testObject, Integer columnNumber){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def columnHeaders = getSortableColumns(testObject)

			if(columnHeaders.size() >= columnNumber){

				setDesiredColumn(columnHeaders, columnNumber)

				successful = verifySort(testObject, columnNumber, Sort.ASCENDING)

				if(successful) successful = verifySort(testObject, columnNumber, Sort.DESCENDING)
			}
			else{

				throw new Exception("Selected column number (" + (columnNumber as String) + ") is outside the expected range (up to " + (columnHeaders.size() as String) + ")")
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def setDesiredColumn(ArrayList columnHeaders, Integer columnNumber){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def index = columnNumber > 0 ? columnNumber - 1 : columnNumber

			this.desiredColumn = columnHeaders[index]

			successful = true
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "Setting desired column WAS" + (successful ? " " : " NOT ") + "successful")

		successful
	}

	def verifySort(TestObject testObject, Integer columnNumber, Sort sort){

		def logHeader

		def sortingSuccessful = true

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to verify " + (sort.name() as String) + " sorting")

			def by = By.cssSelector('tbody tr')

			def byCell = By.cssSelector('td')

			def isArrowCorrect = makeArrowCorrect(sort)

			if(isArrowCorrect){

				def rows = findElements(by, testObject)

				def columnList = getDesiredColumnList(rows, columnNumber)

				def rowSize = rows.size()

				def columnListSize = columnList.size()

				def sameSize = rowSize == columnListSize

				KeywordUtil.logInfo(logHeader + "Row count (" + (rowSize as String) + ") and column list count (" + (columnListSize as String) + ") ARE" + (sameSize ? " " : " NOT ") + "equal")

				if(rowSize == columnListSize && rowSize > 0){

					columnList.sort()

					if(sort == Sort.DESCENDING) Collections.reverse(columnList)

					KeywordUtil.logInfo(logHeader + "Column List: " + columnList as String)

					for(int i = 0 ; i < rows.size() ; i++){

						def cells = findElements(byCell, rows[i])

						def cellText = getText(cells[columnNumber]).trim().toUpperCase()

						KeywordUtil.logInfo(logHeader + "Expected text from column: " + columnList[i] as String)

						if(columnList[i] != cellText){

							KeywordUtil.logInfo(logHeader + "Actual and Expected text DO NOT match")

							sortingSuccessful = false

							break
						}
					}
				}
				else{

					sortingSuccessful = false
				}
			}

			KeywordUtil.logInfo(logHeader + "Sorting by " + (sort.name() as String) + " WAS" + (sortingSuccessful ? " " : " NOT ") + "successful")
		}
		catch (Exception e) {

			sortingSuccessful = false

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		sortingSuccessful
	}

	def getDesiredColumnList(ArrayList rows, Integer columnNumber){

		def logHeader

		def columnList = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('td')

			for(int i = 0 ; i < rows.size() ; i++){

				def cells = findElements(by, rows[i])

				def cellText = getText(cells[columnNumber]).toUpperCase()

				columnList.add(cellText)
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		columnList
	}

	def makeArrowCorrect(Sort sort){

		def logHeader

		def isCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to make the sorting arrow face in the " + (sort.name() as String) + " direction on the desired column")

			def attempts = 0

			def arrowFound = findSortingArrow(this.desiredColumn)

			if(!arrowFound){

				KeywordUtil.logInfo(logHeader + "Clicking on column header to make first attempt at sorting")

				clickDesiredColumn(this.desiredColumn)
			}

			isCorrect = isArrowCorrect(sort)

			while (!isCorrect && attempts < 2){

				attempts += 1

				clickDesiredColumn(this.desiredColumn)

				isCorrect = isArrowCorrect(sort)
			}

			KeywordUtil.logInfo(logHeader + "Sorting arrow WAS" + (isCorrect ? " " : " NOT ") + "made correct")
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		isCorrect
	}

	def clickDesiredColumn(WebElement element){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to click on desired column")

			successful = click(this.desiredColumn)

			KeywordUtil.logInfo(logHeader + "Clicking on desired column WAS" + (successful ? " " : " NOT ") + "successful")

			if(successful){

				KeywordUtil.logInfo(logHeader + "Attempting to re-find the sorting arrow")

				successful = findSortingArrow(element)

				KeywordUtil.logInfo(logHeader + "Finding the sorting arrow WAS" + (successful ? " " : " NOT ") + "successful")
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def isArrowCorrect(Sort sort){

		def logHeader

		def arrowCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to determine if the sorting arrow is pointing in the " + (sort.name() as String) + " direction")

			def classes = getAttribute(this.sortingArrow, 'class')

			arrowCorrect = (classes.contains('k-i-sort-asc') && sort == Sort.ASCENDING) || (classes.contains('k-i-sort-desc') && sort == Sort.DESCENDING)

			KeywordUtil.logInfo(logHeader + "Sorting arrow IS" + (arrowCorrect ? " " : " NOT ") + "pointing in the " + (sort.name() as String) + " direction")
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		arrowCorrect
	}

	def findSortingArrow(WebElement element){

		def logHeader

		def found = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempt to locate the sorting arrow on desired column header")

			def by = By.cssSelector('span[class*=\"k-i-sort\"]')

			def sortingArrow = findElements(by, element)

			if (sortingArrow.size() > 0){

				this.sortingArrow = sortingArrow[0]

				found = true
			}

			KeywordUtil.logInfo(logHeader + "Sorting arrow WAS" + (found ? " " : " NOT ") + "found")
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		found
	}

	def getSortableColumns(TestObject testObject){

		def logHeader, columnHeaders

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + "Attempting to find sortable columns")

			def cssTail = ' thead tr th[data-role=\"columnsorter\"]:not([style*=\"display: none\"]):not([style*=\"display:none\"])'

			def activeProperty = getActiveProperty(testObject)

			def updatedValue = activeProperty.value + cssTail

			def by = getByLocator(activeProperty.name, updatedValue)

			columnHeaders = findElements(by)
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		columnHeaders
	}
}
