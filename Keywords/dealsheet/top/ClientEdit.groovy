package dealsheet.top

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

import general.*
import dealsheet.TestObjectNames
import dealsheet.CustomerProfile.DealsheetContactFields

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class ClientEdit extends WrappedSelenium{

	@Keyword
	def getAllContactInfo(ClientEditContactFields contactField){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def allContactInfo = []

		def contactFieldInfo = contactField.getValue()

		try{

			def by = By.cssSelector(contactFieldInfo['allRows'] + ' ' + contactFieldInfo['contact'])

			def allRows = findElements(by)

			for (int i = 0 ; i < allRows.size() ; i++){

				def contactInfo = []

				def classes = getAttribute(allRows[i], 'class')

				def	text = getAttribute(allRows[i], 'value')

				contactInfo.add(text)

				def strikeThrough = classes.contains('strike-out')

				contactInfo.add(strikeThrough)

				allContactInfo.add(contactInfo)
			}

			KeywordUtil.logInfo(logHeader + "All contact info for " + (contactField.name()) + ": " + allContactInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allContactInfo
	}

	def getAllContactInfoHOLD(ClientEditContactFields contactField){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def allContactInfo = []

		try{

			def allRows = getAllRows(contactField)

			for (int i = 0 ; i < allRows.size() ; i++){

				def contactInfo = getAttribute(allRows[i], 'value')

				allContactInfo.add(contactInfo)
			}

			KeywordUtil.logInfo(logHeader + "All contact info for " + (contactField.name()) + ": " + allContactInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allContactInfo
	}

	def getAllRows(ClientEditContactFields contactField){

		def logHeader

		def allRows

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def fieldInfo = contactField.getValue()

			allRows = findElements(By.cssSelector(fieldInfo['allRows']))

			def allRowsSize = allRows.size()

			KeywordUtil.logInfo("Found " + (allRowsSize as String) + " row" + (allRowsSize == 1 ? "" : "s") + " for the " + contactField.name() + " section")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allRows
	}

	@Keyword
	def verifyStrikeThroughInEditWindow(){

		def logHeader, rowInfo

		def statusCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byRows = By.cssSelector('.form-inline edit-contact .row')

			def byCheckbox = By.cssSelector('[type="checkbox"]')

			def rows = findElements(byRows)

			if(rows){

				for (int i = 0 ; i < rows.size() ; i++){

					rowInfo = getRowInfo(rows[i])

					statusCorrect = checkStatus(rowInfo)

					if(!statusCorrect) throw new Exception(logHeader + "Initial status check failed")

					click(rowInfo['checkbox'])

					rowInfo = getRowInfo(rows[i])

					statusCorrect = checkStatus(rowInfo)

					KeywordUtil.logInfo(logHeader + 'Row ' + (i as String) + ': ' + (statusCorrect ? "Passed" : "Failed"))

					if(!statusCorrect) throw new Exception(logHeader + "After click status check failed")
				}
			}
			else{

				throw new Exception('No rows found')
			}

			KeywordUtil.logInfo(logHeader + 'Strikethroughs WERE' + (statusCorrect ? " " : " NOT ") + 'successful')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		statusCorrect
	}

	@Keyword
	def verifyStrikeThroughCancels(){

		def logHeader, rowInfo

		def statusCorrect = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def byRows = By.cssSelector('.form-inline edit-contact .row')

			def byCheckbox = By.cssSelector('[type="checkbox"]')

			def rows = findElements(byRows)

			if(rows){

				for (int i = 0 ; i < rows.size() ; i++){

					rowInfo = getRowInfo(rows[i])

					statusCorrect = checkStatus(rowInfo)

					if(!statusCorrect) throw new Exception(logHeader + "Initial status check failed")

					click(rowInfo['checkbox'])

					rowInfo = getRowInfo(rows[i])

					statusCorrect = checkStatus(rowInfo)

					KeywordUtil.logInfo(logHeader + 'Row ' + (i as String) + ': ' + (statusCorrect ? "Passed" : "Failed"))

					if(!statusCorrect) throw new Exception(logHeader + "After click status check failed")
				}
			}
			else{

				throw new Exception('No rows found')
			}

			KeywordUtil.logInfo(logHeader + 'Strikethroughs WERE' + (statusCorrect ? " " : " NOT ") + 'successful')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		statusCorrect
	}

	def checkStatus(LinkedHashMap status){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def goodStatus

		try{

			goodStatus = (status['strikethrough'] && status['checked']) || (!status['strikethrough'] && !status['checked'])

			KeywordUtil.logInfo(logHeader + "Status IS" + (goodStatus ? " " : " NOT ") + "good")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		goodStatus
	}

	def getRowInfo(WebElement row){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def rowInfo = [:]

		try{

			def byText = By.cssSelector('[type="text"]')

			def byCheckbox = By.cssSelector('[type="checkbox"]')

			def texts = findElements(byText, row, 1)

			if (texts){

				def checkboxes = findElements(byCheckbox, row)

				if(checkboxes){

					def textClasses = getAttribute(texts[0], 'class')

					def checkboxClasses = getAttribute(checkboxes[0], 'class')

					rowInfo['textbox'] = texts[0]

					rowInfo['strikethrough'] = textClasses.contains('strike-out')

					rowInfo['checkbox'] = checkboxes[0]

					rowInfo['checked'] = checkboxClasses.contains('ng-not-empty')
				}
			}

			KeywordUtil.logInfo(logHeader + "Row info found: " + rowInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		rowInfo
	}

	@Keyword
	def verifyPreferredChange(LinkedHashMap contactInfo){

		def logHeader

		def goodChange = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			goodChange = (contactInfo['originalPreferred'] == contactInfo['oldPreferred']) && (contactInfo['newPreferred'] == contactInfo['preferred'])

			KeywordUtil.logInfo("Preferred change WAS" + (goodChange ? " " : " NOT ") + "successful")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		goodChange
	}

	@Keyword
	def makeRandomPreferred(ClientEditContactFields contactField){

		def logHeader

		def contactInfo = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def allRows = getAllRows(contactField)

			if(allRows){

				def fieldInfo = contactField.getValue()

				def originalPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[0])

				if(!originalPreferredElements) throw new Exception("No original preferred contact found for " + contactField.name())

				contactInfo['originalPreferred'] = getAttribute(originalPreferredElements[0], 'value')

				def randomNumber, setPreferredElements

				def allRowsSize = allRows.size()

				if(allRowsSize > 1){

					def randomFound = false

					while(!randomFound){

						randomNumber = new RandomGenerator().generateRandomNumber(0, allRowsSize - 1)

						setPreferredElements = findElements(By.cssSelector('[title="Set Preferred"]'), allRows[randomNumber], 1)

						if(setPreferredElements){

							randomFound = true
						}
						else{

							Thread.sleep(1) // Wait 1 second before attempting to locate elements again
						}
					}

					def newPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[randomNumber])

					if(!newPreferredElements) throw new Exception("New preferred element not found even after 'set preferred' option was found")

					contactInfo['newPreferred'] = getAttribute(newPreferredElements[0], 'value')

					click(setPreferredElements[0])

					allRows = getAllRows(contactField)

					def oldPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[randomNumber])

					if(!oldPreferredElements) throw new Exception("Old preferred element not found in new position")

					contactInfo['oldPreferred'] = getAttribute(oldPreferredElements[0], 'value')

					def preferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[0])

					if(!preferredElements) throw new Exception("Preferred element not found in top position")

					contactInfo['preferred'] = getAttribute(preferredElements[0], 'value')
				}
				else{

					throw new Exception("Unable to set a different preferred " + contactField.name() + " because only main record found.")
				}
			}
			else{

				throw new Exception("No rows found for " + contactField.name())
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + contactField.name() + " contact info: " + contactInfo as String)

		contactInfo
	}

	@Keyword
	def makeRandomPreferredOLD(ClientEditContactFields contactField){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def contactInfo = [:]

		try{

			def allRows = getAllRows(contactField)

			def fieldInfo = contactField.getValue()

			if(!allRows) throw new Exception("No rows found for " + contactField.name())

			def originalPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[0])

			if(!originalPreferredElements) throw new Exception("No original preferred contact found for " + contactField.name())

			contactInfo['originalPreferred'] = getAttribute(originalPreferredElements[0], 'value')

			def randomFound = false

			def randomNumber, setPreferredElements

			while(!randomFound){

				randomNumber = new RandomGenerator().generateRandomNumber(1, allRows.size())

				setPreferredElements = findElements(By.cssSelector('[title="Set Preferred"]'), allRows[randomNumber])

				randomFound = setPreferredElements ? true : false
			}

			def newPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[randomNumber])

			if(!newPreferredElements) throw new Exception("New preferred element not found even after 'set preferred' option was found")

			contactInfo['newPreferred'] = getAttribute(newPreferredElements[0], 'value')

			click(setPreferredElements[0])

			allRows = getAllRows(contactField)

			def oldPreferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[randomNumber])

			if(!oldPreferredElements) throw new Exception("Old preferred element not found in new position")

			contactInfo['oldPreferred'] = getAttribute(oldPreferredElements[0], 'value')

			def preferredElements = findElements(By.cssSelector(fieldInfo['contact']), allRows[0])

			if(!preferredElements) throw new Exception("Preferred element not found in top position")

			contactInfo['preferred'] = getAttribute(preferredElements[0], 'value')

			KeywordUtil.logInfo(logHeader + "Contact info: " + contactInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		contactInfo
	}

	enum ClientEditContactFields{

		HOME_PHONE(['allRows':'edit-contact[on-change*="omePhone"]', 'contact': '[name="phoneNumber"]']),
		WORK_PHONE(['allRows':'edit-contact[on-change*="orkPhone"]', 'contact': '[name="phoneNumber"]']),
		CELL_PHONE(['allRows':'edit-contact[on-change*="ellPhone"]', 'contact': '[name="phoneNumber"]']),
		EMAIL(['allRows':'edit-contact[on-change*="Address"]', 'contact': '[name="emailAddress"]'])

		def getValue

		ClientEditContactFields(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}