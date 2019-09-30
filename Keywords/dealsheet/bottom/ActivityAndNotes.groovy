package dealsheet.bottom

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
import general.Enums.Alerts

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class ActivityAndNotes extends WrappedSelenium{

	def filtersContainerObjName = TestObjectNames.ActivityAndNotes.Filters.CONTAINER

	@Keyword
	def getFilters(){

		def logHeader

		def filters = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def container = returnTestObject(filtersContainerObjName)

			def by = By.cssSelector('filter-button button')

			def filterRows = findElements(by, container)

			if(filterRows){

				for(int i = 0 ; i < filterRows.size() ; i++){

					def filterDetails = getFilterDetails(filterRows[i])

					filters.add(filterDetails)
				}

				KeywordUtil.logInfo(logHeader + "Filters (found " + (filters.size() as String) + ") returned: " + filters as String)
			}
			else{

				throw new Exception("No filters found")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		filters
	}

	def getFilterDetails(WebElement element){

		def logHeader

		def filterDetails = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			filterDetails['element'] = element

			def textBy = By.cssSelector('.buttontext')

			def textElement = findElements(textBy, element)

			if(textElement){

				filterDetails['text'] = getText(textElement[0])
			}
			else{

				KeywordUtil.markWarning(logHeader + "Filter text not found")
			}

			// Icon information can be added at a future time

			KeywordUtil.logInfo(logHeader + "Filter details: " + filterDetails as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		filterDetails
	}

	def gatherAllActivities(){

		def logHeader

		def allActivities = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def allActivityContainers = getAllActivityContainers()

			for(int i = 0 ; i < allActivityContainers.size() ; i++){

				allActivities.add(getActivityDetails(allActivityContainers[i]))
			}

			KeywordUtil.logInfo(logHeader + "All activities: " + allActivities as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allActivities
	}

	def getAllActivityContainers(){

		def logHeader

		def allActivityContainers

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.activities .activity')

			allActivityContainers = findElements(by)

			def numberOfActivities = allActivityContainers ? allActivityContainers.size() : 0

			KeywordUtil.logInfo(logHeader + "There " + (numberOfActivities == 1 ? "is " : "are ") +
					(numberOfActivities as String) + " activit" + (numberOfActivities == 1 ? "y" : "ies") + " found")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allActivityContainers
	}

	@Keyword
	def getActivity(Integer index){

		def logHeader

		def activityInfo

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def allActivities = getAllActivityContainers()

			if(allActivities){

				def numberOfActivities = allActivities.size()

				if(numberOfActivities >= index && index > 0){

					def activity = allActivities[index - 1]

					activityInfo = getActivityDetails(activity)
				}
				else{

					throw new Exception("Index desired (" + (index as String) +
					") is outside the activity range (1 - " + (numberOfActivities as String) + ")")
				}
			}
			else{

				throw new Exception("No activities found")
			}

			KeywordUtil.logInfo(logHeader + "Activity details: " + activityInfo as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		activityInfo
	}

	@Keyword
	def getActivityDetails(WebElement activity){

		def logHeader

		def activityDetails = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			//activityDetails['container'] = activity

			activityDetails['icon'] = new Icons().getIconType(activity)

			if(activityDetails['icon'].toLowerCase() == 'phone'){

				activityDetails['typeOfCall'] = getTypeOfCall(activity)
			}

			activityDetails['dateTime'] = getDateTime(activity)

			activityDetails['submittedBy'] = getSubmittedBy(activity)

			activityDetails['details'] = getDetails(activity)

			KeywordUtil.logInfo(logHeader + "Activity details: " + activityDetails as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		activityDetails
	}

	def getTypeOfCall(WebElement activity){

		def logHeader

		def typeOfCall

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('[translate*="boundCall"]')

			def callTypeElements = findElements(by, activity, 1)

			typeOfCall = callTypeElements ? getText(callTypeElements[0]) : 'No call type found'

			KeywordUtil.logInfo(logHeader + "Type of Call found: " + typeOfCall)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		typeOfCall
	}

	def getDetails(WebElement activity){

		def logHeader

		def details = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.activity-data div[ng-if]')

			def detailElements = findElements(by, activity, 1)

			if(detailElements){

				for(int i = 0 ; i < detailElements.size() ; i++){

					def detailText = getText(detailElements[i])

					details.add(detailText)
				}
			}
			else{

				details.add('no details found')
			}

			KeywordUtil.logInfo(logHeader + "Details found: " + details as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		details
	}

	def getSubmittedBy(WebElement activity){

		def logHeader

		def submittedBy

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('[translate-value-submitted-by]')

			def submittedByElements = findElements(by, activity, 1)

			submittedBy = submittedByElements ? getText(submittedByElements[0]) : 'No submitted by found'

			KeywordUtil.logInfo(logHeader + "Submitted by found: " + submittedBy)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		submittedBy
	}

	def getDateTime(WebElement activity){

		def logHeader

		def normalizedDateTime

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('div strong span:nth-child(1)')

			def dateTimeElements = findElements(by, activity, 1)

			if(dateTimeElements){

				def rawDateTime = getText(dateTimeElements[0])

				KeywordUtil.logInfo(logHeader + "Attempting to normalize '" + (rawDateTime as String) + "' as local date/time")

				normalizedDateTime = new Utilities().normalizeDateTime(rawDateTime)
			}
			else{

				normalizedDateTime = 'No date/time found'
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		normalizedDateTime
	}

	def processCallActivity(ArrayList details){

		def logHeader, key

		def specifics = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			for(int i = 0 ; i < details.size() ; i++){

				def attributeValue = getAttribute(details[i], 'ng-if')

				if(attributeValue.contains('.typeOfCall')){

					key = 'typeOfCall'

					//def typeOfCall = getText(details[i])

					//specifics['typeOfCall'] = typeOfCall
				}
				else if(attributeValue.contains('.type') && !attributeValue.contains('.notes')){

					key = 'type'

					//specifics['type'] = getText(details[i])

					specifics['submittedBy'] = getAttribute(details[i], 'translate-value-submitted-by')

					specifics['typeTranslate'] = getAttribute(details[i], 'translate')

					specifics['typeTranslate2'] = new JavaScript().execute(details[i], "return arguments[0].translate")
				}
				else if(attributeValue.contains('.result')){

					key = 'result'

					//def result = getText(details[i])

					//specifics['result'] = result
				}
				else if(attributeValue.contains('.notes')){

					key = 'notes'

					//def notes = getText(details[i])

					//specifics['notes'] = notes
				}
				else{

					key = i as String
				}

				def text = getText(details[i])

				specifics[key] = text
			}

			KeywordUtil.logInfo(logHeader + "Activity details: " + specifics as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		specifics
	}

	def processCallActivityOLD(ArrayList details){

		def logHeader, key

		def specifics = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			for(int i = 0 ; i < details.size() ; i++){

				def attributeValue = getAttribute(details[i], 'ng-if')

				if(attributeValue.contains('.typeOfCall')){

					def typeOfCall = getText(details[i])

					specifics['typeOfCall'] = typeOfCall
				}
				else if(attributeValue.contains('.type') && !attributeValue.contains('.notes')){

					specifics['type'] = getText(details[i])

					specifics['submittedBy'] = getAttribute(details[i], 'translate-value-submitted-by')

					specifics['typeTranslate'] = getAttribute(details[i], 'translate')

					specifics['typeTranslate2'] = new JavaScript().execute(details[i], "return arguments[0].translate")
				}
				else if(attributeValue.contains('.result')){

					def result = getText(details[i])

					specifics['result'] = result
				}
				else if(attributeValue.contains('.notes')){

					def notes = getText(details[i])

					specifics['notes'] = notes
				}
				else{

					specifics[i as String] = 'Unknown'
				}
			}

			KeywordUtil.logInfo(logHeader + "Activity details: " + specifics as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		specifics
	}

	def processEmailActivity(ArrayList details){

		def logHeader, key

		def specifics = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			for(int i = 0 ; i < details.size() ; i++){

				def attributeValue = getAttribute(details[i], 'ng-if')

				if(attributeValue.contains('.typeOfCall')){

					key = 'typeOfCall'

					//def typeOfCall = getText(details[i])

					//specifics['typeOfCall'] = typeOfCall
				}
				else if(attributeValue.contains('.type') && !attributeValue.contains('.notes')){

					key = 'type'

					//specifics['type'] = getText(details[i])

					specifics['submittedBy'] = getAttribute(details[i], 'translate-value-submitted-by')

					specifics['typeTranslate'] = getAttribute(details[i], 'translate')

					specifics['typeTranslate2'] = new JavaScript().execute(details[i], "return arguments[0].translate")
				}
				else if(attributeValue.contains('.result')){

					key = 'result'

					//def result = getText(details[i])

					//specifics['result'] = result
				}
				else if(attributeValue.contains('.notes')){

					key = 'notes'

					//def notes = getText(details[i])

					//specifics['notes'] = notes
				}
				else{

					key = i as String
				}

				def text = getText(details[i])

				specifics[key] = text
			}

			KeywordUtil.logInfo(logHeader + "Activity details: " + specifics as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		specifics
	}

	def processTextActivity(ArrayList details){

		def logHeader, key

		def specifics = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			for(int i = 0 ; i < details.size() ; i++){

				def attributeValue = getAttribute(details[i], 'ng-if')

				if(attributeValue.contains('.typeOfCall')){

					key = 'typeOfCall'

					//def typeOfCall = getText(details[i])

					//specifics['typeOfCall'] = typeOfCall
				}
				else if(attributeValue.contains('.type') && !attributeValue.contains('.notes')){

					key = 'type'

					//specifics['type'] = getText(details[i])

					specifics['submittedBy'] = getAttribute(details[i], 'translate-value-submitted-by')

					specifics['typeTranslate'] = getAttribute(details[i], 'translate')

					specifics['typeTranslate2'] = new JavaScript().execute(details[i], "return arguments[0].translate")
				}
				else if(attributeValue.contains('.result')){

					key = 'result'

					//def result = getText(details[i])

					//specifics['result'] = result
				}
				else if(attributeValue.contains('.notes')){

					key = 'notes'

					//def notes = getText(details[i])

					//specifics['notes'] = notes
				}
				else{

					key = i as String
				}

				def text = getText(details[i])

				specifics[key] = text
			}

			KeywordUtil.logInfo(logHeader + "Activity details: " + specifics as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		specifics
	}

	def determineActivityType(WebElement activity){

		def logHeader

		def activityType

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def ngcontentBy = By.cssSelector('[_ngcontent-c12]')

			def ngifBy = By.cssSelector('[ng-if]')

			def details = findElements(ngifBy, activity, 1)

			if(details){

				def firstText = getText(details[0])

				if(firstText.contains('Call')){

					activityType = ActivityTypes.CALL
				}
				else{

					activityType = ActivityTypes.EMAIL
				}
			}
			else{

				activityType = ActivityTypes.TEXT

				// Need to figure out how to detect text activities
				/*details = findElements(ngcontentBy, activity, 1)
				 if(details){
				 activityType = ActivityTypes.TEXT
				 }
				 else{
				 throw new Exception('Cannot determine activity type')
				 }*/
			}

			KeywordUtil.logInfo(logHeader + "Activity type: " + activityType.name())
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		activityType
	}

	def getActivityDateTime(WebElement activity){

		def logHeader

		def dateTimeDetail

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def dateTimeBy = By.cssSelector('strong span:nth-child(1)')

			def strongs = findElements(dateTimeBy, activity)

			if(strongs){

				dateTimeDetail = getText(strongs[0])
			}

			KeywordUtil.logInfo(logHeader + "Date/Time found: " + dateTimeDetail as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		dateTimeDetail
	}

	def gatherAllActivities2(){

		def logHeader

		def allActivities = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('.activity .activity-data')

			def allBy = By.cssSelector('*')

			def dateTimeBy = By.cssSelector('strong span:nth-child(1)')

			def allActivityContainers = findElements(by)

			for(int i = 0 ; i < allActivityContainers.size() ; i++){

				def activity = [:]

				def activityDetails = findElements(allBy, allActivityContainers[i])

				for(int x = 0 ; x < activityDetails.size() ; x++){

					def textHolder = getText(activityDetails[x])

					activity[x as String] = textHolder as String
				}

				allActivities.add(activity)
			}

			KeywordUtil.logInfo(logHeader + "All activities: " + allActivities as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		allActivities
	}

	@Keyword
	def getUpcomingAppointment(){

		def logHeader

		def upcomingAppt

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def containerBy = By.cssSelector('.activity-scheduled')

			def container = findElements(containerBy, 3)

			if(container){

				def activityBy = By.cssSelector('activity')

				def activities = findElements(activityBy, container[0], 1)

				if(activities){

					upcomingAppt = [:]

					upcomingAppt['icon'] = new Icons().getIconType(container[0])

					def detailLinesBy = By.cssSelector('.activity-data div')

					def detailLines = findElements(detailLinesBy, container[0])

					for(int i = 0 ; i < detailLines.size() ; i++){

						def detailLineText = getText(detailLines[i])

						upcomingAppt['line' + ((i + 1) as String)] = detailLineText
					}
				}
				else{

					def noApptBy = By.cssSelector('[translate="noScheduledActivity"]')

					def noAppointmentsMessage = findElements(noApptBy, 3)

					if(noAppointmentsMessage){

						upcomingAppt = [:]

						upcomingAppt['message'] = getText(noAppointmentsMessage[0])
					}
				}
			}

			KeywordUtil.logInfo(logHeader + "Upcoming appointment details: " + upcomingAppt as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		upcomingAppt
	}

	@Keyword
	def cancelAllUpcomingAppointments(){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def upcomingAppointmentsExist = true

			while(upcomingAppointmentsExist){

				def cancelLink = findTestObject('Dealsheet/Tabs/Activity and Notes/Upcoming Appointments/Links/Dealsheet - Tabs - Activity and Notes - Upcoming Appts - Links - Cancel')

				def cancelLinkVisible = WebUI.waitForElementVisible(cancelLink, 1)

				if(cancelLinkVisible){

					cancelUpcomingAppointment()
				}
				else{

					upcomingAppointmentsExist = false

					successful = true
				}
			}

			KeywordUtil.logInfo(logHeader + "All upcoming appointments HAVE" + (successful ? " " : " NOT ") + "been removed successfully")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def cancelUpcomingAppointment(){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			// Click Cancel Appointment link
			def cancelLink = findTestObject('Dealsheet/Tabs/Activity and Notes/Upcoming Appointments/Links/Dealsheet - Tabs - Activity and Notes - Upcoming Appts - Links - Cancel')

			def cancelLinkVisible = WebUI.waitForElementVisible(cancelLink, 10)

			if(!cancelLinkVisible) throw new Exception('Unable to find Cancel link')

			def cancelLinkClicked = click(cancelLink)

			if(!cancelLinkClicked) throw new Exception('Unable to click Cancel link')

			// Click Submit button on Popup
			def popupSubmitButton = findTestObject('Object Repository/Dealsheet/Modals/Post Notes/Buttons/Dealsheet - Modals - Post Notes - Buttons - Submit')

			def popupSubmitButtonVisible = WebUI.waitForElementVisible(popupSubmitButton, 10)

			if(!popupSubmitButtonVisible) throw new Exception('Unable to find Submit button')

			def popupSubmitButtonClicked = click(popupSubmitButton)

			if(!popupSubmitButtonClicked) throw new Exception('Unable to click Submit button')

			WebUI.delay(2) // Bad way of waiting for appt to clear. Refactor at some point.

			// Wait for dealsheet to update
			def dealsheetLoaded = new dealsheet.Common().waitToLoad()

			KeywordUtil.logInfo(logHeader + "Upcoming appointment WAS canceled successfully")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	enum ActivityTypes{
		CALL([:]),
		TEXT([:]),
		EMAIL([:])

		def getValue

		ActivityTypes(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}