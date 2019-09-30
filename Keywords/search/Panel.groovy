package search

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
import test.Assistant.AfterFailure
import dealsheet.bottom.TestObjects as DealsheetTestObjects

import sql.*
import java.time.*
import java.time.format.*
import java.util.concurrent.TimeUnit
import java.time.temporal.ChronoUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Panel extends WrappedSelenium{

	private By deleteBy = By.cssSelector('[icon="\'trash\'"]')

	private By shareSearchBy = By.cssSelector('[icon="\'share\'"]')

	private By unshareSearchBy = By.cssSelector('[icon="\'unshare\'"]')

	public By getDeleteBy(){
		return this.deleteBy
	}

	public By getShareSearchBy(){
		return this.shareSearchBy
	}

	public By getUnshareSearchBy(){
		return this.unshareSearchBy
	}

	@Keyword
	def verifyExpandableSearch(ExpandableSearch search){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def searchSectionHeader = search.getValue()['header']

			def searchSections = getSections()

			// This can be re-factored.  Added getSection() method that would eliminate much of this.

			if(searchSections){

				for(int i = 0 ; i < searchSections.size() ; i++){

					def headerFound = verifySearchSectionHeader(search, searchSections[i])

					if(headerFound){

						def togglesSuccessfully = verifySearchSectionToggles(searchSections[i])

						if(togglesSuccessfully){

							def individualSearchesFound = verifySectionContainsSearches(searchSections[i])

							if(individualSearchesFound){

								successful = true
							}
						}

						break
					}
				}
			}
			else{

				throw new Exception('No expandable searches found')
			}

			KeywordUtil.logInfo(logHeader + '\'' + searchSectionHeader + '\' section ' + (successful ? 'PASSED' : 'FAILED'))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def getSections(){

		def searchSectionsBy = By.cssSelector('div.search-menu [ng-if="($ctrl.searchPresets)"]')

		def searchSections = findElements(searchSectionsBy)

		searchSections
	}

	@Keyword
	def getSection(ExpandableSearch searchSection){

		def section, logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def searchSectionHeader = searchSection.getValue()['header']

			def searchSections = getSections()

			if(searchSections){

				for(int i = 0 ; i < searchSections.size() ; i++){

					def headerFound = verifySearchSectionHeader(searchSection, searchSections[i])

					if(headerFound){

						section = searchSections[i]

						break
					}
				}
			}

			KeywordUtil.logInfo(logHeader + '\'' + searchSectionHeader + '\' section WAS' + (section ? ' ' : ' NOT ') + 'found')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		section
	}

	def verifySearchSectionHeader(ExpandableSearch search, WebElement searchSection){

		def logHeader, headerText, expectedHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify header')

			def headersBy = By.cssSelector('h5')

			def header = findElements(headersBy, searchSection)

			if(header){

				headerText = getText(header[0]).trim()

				expectedHeader = search.getValue()['header']

				successful = headerText == expectedHeader
			}
			else{

				throw new Exception('No header found')
			}

			KeywordUtil.logInfo(logHeader + 'Correct header WAS' + (successful ? ' ' : ' NOT ') + 'found. ' + (headerText as String) + ' / ' + (expectedHeader as String))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def verifySearchSectionToggles(WebElement searchSection){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify section expands and collapses properly')

			def chevronBy = By.cssSelector('icon[class*="chevron"]')

			def chevron = findElements(chevronBy, searchSection)

			def chevronDown = 'chevron-down'

			def chevronRight = 'chevron-right'

			if(chevron){

				def classes = getAttribute(chevron[0], 'class')

				def originalChevronDirection = classes.contains(chevronRight) ? chevronRight : chevronDown

				def afterFirstClickExpectedChevronDirection = originalChevronDirection ==  chevronRight ? chevronDown: chevronRight

				click(chevron[0])

				chevron = findElements(chevronBy, searchSection)

				classes = getAttribute(chevron[0], 'class')

				def firstClickGoodState = classes.contains(afterFirstClickExpectedChevronDirection)

				KeywordUtil.logInfo(logHeader + 'After first click, chevron direction IS' + (firstClickGoodState ? ' ' : ' NOT ') +
						'correct. ' + classes + ' / Expected: ' + afterFirstClickExpectedChevronDirection)

				if(firstClickGoodState){

					click(chevron[0])

					chevron = findElements(chevronBy, searchSection)

					classes = getAttribute(chevron[0], 'class')

					def afterSecondClickExpectedChevronDirection = afterFirstClickExpectedChevronDirection == chevronDown ? chevronRight : chevronDown

					successful = classes.contains(afterSecondClickExpectedChevronDirection)

					KeywordUtil.logInfo(logHeader + 'After second click, chevron direction IS' + (successful ? ' ' : ' NOT ') +
							'correct. ' + classes + ' / Expected: ' + afterSecondClickExpectedChevronDirection)
				}
			}
			else{

				throw new Exception('No chevron found')
			}

			KeywordUtil.logInfo(logHeader + 'Search section WAS' + (successful ? ' ' : ' NOT ') + 'toggled successfully.')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def verifySectionContainsSearches(WebElement searchSection){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to verify section contains searches')

			def individualSearchesBy = By.cssSelector('li')

			def individualSearches = findElements(individualSearchesBy, searchSection)

			if(individualSearches){

				successful = individualSearches.size() > 0
			}
			else{

				throw new Exception('No individual searches found')
			}

			KeywordUtil.logInfo(logHeader + 'Individual searches WERE' + (successful ? ' ' : ' NOT ')
					+ 'found. ' + (successful ? (individualSearches.size() as String) + ' total.' : ''))
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def verifySectionContainsSearch(ExpandableSearch searchSection, String searchName){

		def logHeader

		def search = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			search['exists'] = false

			def sectionHeader = searchSection.getValue()['header']

			KeywordUtil.logInfo(logHeader + 'Attempting to verify that the \''+ sectionHeader + '\' section contains \'' + searchName + '\' search')

			def section = getSection(searchSection)

			def individualSearches = getSectionSearches(section)

			if(individualSearches.containsKey(searchName)){

				search['element'] = individualSearches[searchName]

				search['exists'] = true
			}
			else{

				search['element'] = null
			}

			KeywordUtil.logInfo(logHeader + '\'' + searchName + '\' search WAS' + (search['exists'] ? ' ' : ' NOT ') + 'found in \'' + sectionHeader + '\'')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		search
	}

	@Keyword
	def getSectionSearches(WebElement section){

		def logHeader

		def searchNamesList = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def individualSearchesBy = By.cssSelector('li')

			def individualSearches = findElements(individualSearchesBy, section, 2)

			if(individualSearches){

				for(int i = 0 ; i < individualSearches.size() ; i++){

					def searchNameBy = By.cssSelector('a')

					def individualSearchTextElement = findElements(searchNameBy, individualSearches[i])

					if(individualSearchTextElement){

						def test1 = individualSearchTextElement[0].getText()

						def test2 = getText(individualSearchTextElement[0])

						searchNamesList[individualSearchTextElement[0].getText()] = individualSearches[i]

						KeywordUtil.logInfo(logHeader + 'Search name found: ' + searchNamesList['name'] as String)
					}
				}
			}
			else{

				throw new Exception('No individual searches found')
			}

			KeywordUtil.logInfo(logHeader + 'Search names found: ' + searchNamesList as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		searchNamesList
	}

	@Keyword
	def deleteSearch(WebElement search){

		def logHeader

		def details = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def trashIcon = findElements(this.deleteBy, search)

			if(trashIcon){

				click(trashIcon[0])

				details['title'] = WebUI.getText(findTestObject('Search Page/Modals/Search - Modals - Title'))

				details['message'] = WebUI.getText(findTestObject('Search Page/Modals/Search - Modals - Message'))

				details['cancelButtonText'] = WebUI.getText(findTestObject('Search Page/Modals/Buttons/Search - Modals - Buttons - Cancel Ok'))

				def continueButton = findTestObject('Search Page/Modals/Buttons/Search - Modals - Buttons - Continue')

				details['continueButtonText'] = WebUI.getText(continueButton)

				click(continueButton)

				sleep(1000)

				details['deleted'] = !isElementActive(search)
			}
			else{

				throw new Exception('No trash icon found')
			}

			KeywordUtil.logInfo(logHeader + 'Search deletion details: ' + details as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		details
	}

	def clickSearchIcon(SearchIcon icon, WebElement search){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = icon.getValue()['by']

			def icons = findElements(by, search)

			if(icons){

				successful = click(icons[0])

				sleep(1000)
			}
			else{

				throw new Exception('No icon found')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def clickSearchIcon(By by, WebElement search){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def icon = findElements(by, search)

			if(icon){

				successful = click(icon[0])

				sleep(1000)
			}
			else{

				throw new Exception('No icon found')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def shareSearch(WebElement search){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			successful = clickSearchIcon(this.shareSearchBy, search)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def searchesAreAlphabetical(ExpandableSearch searchSection){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def sortedSearchNames = []

			def sectionHeader = searchSection.getValue()['header']

			KeywordUtil.logInfo(logHeader + 'Attempting to verify that searches in the \''+ sectionHeader + '\' section are alphabetical')

			def section = getSection(searchSection)

			def individualSearchesBy = By.cssSelector('li a')

			def individualSearches = findElements(individualSearchesBy, section)

			if(individualSearches){

				for(int i = 0 ; i < individualSearches.size() ; i++){

					def individualSearchText = individualSearches[i].getText().trim().toLowerCase()

					if(individualSearchText.contains('april')){
						def aslhdasd = ''
					}

					sortedSearchNames.add(individualSearchText)
				}

				def originalSearchNames = sortedSearchNames.clone()

				Collections.sort(sortedSearchNames)

				KeywordUtil.logInfo(logHeader + 'Original search name order: ' + originalSearchNames as String)

				KeywordUtil.logInfo(logHeader + 'Sorted search name order: ' + sortedSearchNames as String)

				successful = sortedSearchNames == originalSearchNames
			}
			else{

				throw new Exception('No individual searches found')
			}

			KeywordUtil.logInfo(logHeader + '\'' + sectionHeader + '\' searches ARE' + (successful ? ' ' : ' NOT ') + 'alphabetical')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	@Keyword
	def returnSearchTooltips(WebElement search){

		def logHeader

		def tooltips = [:]

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def sharedSearchBy = By.cssSelector('icon[icon="\'share\'"] title')

			def unsharedSearchBy = By.cssSelector('icon[icon="\'unshare\'"] title')

			def deleteBy = By.cssSelector('icon[icon="\'trash\'"] title')

			// Find Shared Search Tooltip
			def sharedSearchTooltip = findElements(sharedSearchBy, search, 1)

			if(sharedSearchTooltip){

				def tooltipText = getText(sharedSearchTooltip[0])

				tooltips['shareSearch'] = tooltipText
			}
			else{

				tooltips['shareSearch'] = null
			}

			// Find Unshared Search Tooltip
			def unsharedSearchTooltip = findElements(unsharedSearchBy, search, 1)

			if(unsharedSearchTooltip){

				def tooltipText = getText(unsharedSearchTooltip[0])

				tooltips['unshareSearch'] = tooltipText
			}
			else{

				tooltips['unshareSearch'] = null
			}

			// Find Delete Tooltip
			def deleteTooltip = findElements(deleteBy, search, 1)

			if(deleteTooltip){

				def tooltipText = getText(deleteTooltip[0])

				tooltips['delete'] = tooltipText
			}
			else{

				tooltips['delete'] = null
			}

			KeywordUtil.logInfo(logHeader + 'Tooltips found: ' + tooltips as String)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		tooltips
	}

	@Keyword
	def getRandomSearchName(ExpandableSearch searchSection){

		def logHeader

		def searchName

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def sectionHeader = searchSection.getValue()['header']

			KeywordUtil.logInfo(logHeader + 'Attempting to find a random search name in the \''+ sectionHeader + '\' section')

			def section = getSection(searchSection)

			def individualSearchesBy = By.cssSelector('li')

			def individualSearches = findElements(individualSearchesBy, section)

			def lookingForSearchName = true

			if(individualSearches){

				def attempts = 1

				def maxAttempts = 3

				while(lookingForSearchName && attempts < maxAttempts){

					def randomIndex = new RandomGenerator().generateRandomNumber(0, individualSearches.size() - 1)

					KeywordUtil.logInfo(logHeader + 'Looking at search #' + (randomIndex as String) + '\'s search name')

					def searchNameBy = By.cssSelector('a')

					def individualSearchTextElement = findElements(searchNameBy, individualSearches[randomIndex])

					if(individualSearchTextElement){

						searchName = individualSearchTextElement[0].getText()

						KeywordUtil.logInfo(logHeader + 'Search name found: ' + searchName as String)

						lookingForSearchName = false
					}
					else{

						attempts += 1

						if(attempts > maxAttempts){

							KeywordUtil.logInfo(logHeader + 'Max attempts ' + (maxAttempts as String) + ' looking for search name reached without success')
						}
					}
				}
			}
			else{

				throw new Exception('No individual searches found')
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		searchName
	}

	def expandSection(ExpandableSearch section){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def sectionHeader = section.getValue()['header']

			KeywordUtil.logInfo(logHeader + 'Attempting to expand the \'' + sectionHeader + '\' section')

			def sectionElement = getSection(section)

			successful = expandSection(sectionElement)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	def expandSection(WebElement searchSection){

		def logHeader

		def successful = false

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			KeywordUtil.logInfo(logHeader + 'Attempting to expand section expands and collapses properly')

			def chevronBy = By.cssSelector('icon[class*="chevron"]')

			def chevron = findElements(chevronBy, searchSection)

			def chevronDown = 'chevron-down'

			def chevronRight = 'chevron-right'

			if(chevron){

				def classes = getAttribute(chevron[0], 'class')

				def originalChevronDirection = classes.contains(chevronRight) ? chevronRight : chevronDown

				if(originalChevronDirection == chevronRight){

					successful = click(chevron[0])
				}
			}
			else{

				throw new Exception('No chevron found')
			}

			KeywordUtil.logInfo(logHeader + 'Search section WAS' + (successful ? ' ' : ' NOT ') + 'toggled successfully.')
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		successful
	}

	enum ExpandableSearch{
		MY_SEARCHES(['header':'My Searches']),
		SHARED_SEARCHES(['header':'Shared Searches']),
		TAG_BASED_SEARCHES(['header':'Tag-Based Searches']),
		PREDEFINED_SEARCHES(['header':'Predefined Searches']),

		def getValue

		ExpandableSearch(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}

	enum SearchIcon{
		SHARE(['by': By.cssSelector('[icon="\'share\'"]')]),
		UNSHARE(['by':By.cssSelector('[icon="\'unshare\'"]')]),
		DELETE(['by':By.cssSelector('[icon="\'trash\'"]')])

		def getValue

		SearchIcon(LinkedHashMap getValue){

			this.getValue = getValue
		}

		def getValue(){

			return this.getValue
		}
	}
}