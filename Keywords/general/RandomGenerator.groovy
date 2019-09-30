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

import org.apache.commons.lang3.RandomStringUtils

import com.kms.katalon.core.util.KeywordUtil

public class RandomGenerator {

	def generateRandomNumber(Integer minimum, Integer maximum){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		Random rand = new Random()

		def randomNum = minimum + rand.nextInt((maximum - minimum) + 1)

		KeywordUtil.logInfo("Random number generated between " + (minimum as String) + " & " + (maximum as String) + ": " + randomNum as String)

		randomNum
	}

	@Keyword
	def generateRandomString(int lengthOfString, Boolean alphaCharacters=true, Boolean numericCharacters=true, Boolean specialCharacters=true){
		
		def alphaChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
		
		def numericChars = '0123456789'

		def specialChars = '~`!@#$%^&*()-_=+{}\\|;:\'\",./?'

		def characters = (alphaCharacters ? alphaChars : '') + (numericCharacters ? numericChars : '') + (specialCharacters ? specialChars : '')

		RandomStringUtils.random(lengthOfString, characters)
	}

	def generateRandomPhoneNumber(){

		def areaCode, prefix, lineNumber
		def charactersAll = '0123456789'
		def charactersNoZero = '123456789'

		areaCode = RandomStringUtils.random(1, charactersNoZero) + RandomStringUtils.random(2, charactersAll)

		prefix = RandomStringUtils.random(1, charactersNoZero) + RandomStringUtils.random(2, charactersAll)

		lineNumber = RandomStringUtils.random(4, charactersAll)

		KeywordUtil.logInfo("areaCode: " + (areaCode as String) + ", prefix: " + (prefix as String) + ", lineNumber: " + (lineNumber as String))

		areaCode + prefix + lineNumber
	}

	def generateRandomEmailAddress(){

		def leftSide, domainName, domainType
		def charactersAll = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
		def charactersNoNumbers = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
		def domainTypes = [
			'.com',
			'.net',
			'.org',
			'.tv',
			'.info'
		]

		leftSide = RandomStringUtils.random(1, charactersNoNumbers) + RandomStringUtils.random(9, charactersAll)

		domainName = RandomStringUtils.random(1, charactersNoNumbers) + RandomStringUtils.random(5, charactersAll)

		domainType = domainTypes[new Random().nextInt(domainTypes.size())]

		KeywordUtil.logInfo("leftSide: " + (leftSide as String) + ", domainName: " + domainName + ", domainType: " + domainType)

		leftSide + "@" + domainName + domainType
	}

	def generateRandomStreetAddress(){

		def streetNumber, streetName, streetType
		def numbersAll = '0123456789'
		def numbersNoZero = '123456789'
		def charactersNoNumbers = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
		def streetTypes = [
			'Street',
			'Road',
			'Pkwy',
			'Avenue',
			'Hwy'
		]

		streetNumber = RandomStringUtils.random(1, numbersNoZero) + RandomStringUtils.random(new Random().nextInt(5), numbersAll)

		streetName = RandomStringUtils.random(new Random().nextInt(10) + 3, charactersNoNumbers)

		streetType = streetTypes[new Random().nextInt(streetTypes.size())]

		KeywordUtil.logInfo("streetNumber: " + (streetNumber as String) + ", streetName: " + streetName + ", streetType: " + streetType)

		streetNumber + " " + streetName + " " + streetType
	}

	@Keyword
	def getRandomKeyFromMap(Map map){

		def random = new Random()

		def keys = new ArrayList<String>(map.keySet())

		keys.get(random.nextInt(keys.size()))
	}
}
