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

import groovy.io.FileType
import static groovy.io.FileType.FILES
import groovy.time.*

import java.security.SecureRandom

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

public class TestObjects {

	def projectPath = System.getProperty("user.dir")

	def objRepoDirectoryName = TestObjectNames.Katalon.OBJECT_REPO_DIRECTORY_NAME

	def mapOfObjects = [:]

	def goodSlash, badSlash, message, driver

	TestObjects(){

		this.driver = DriverFactory.getWebDriver()

		determineGoodAndBadSlash()
	}

	def getActiveProperty(TestObject testObject){

		KeywordUtil.logInfo("Searching for active property for " + testObject as String)

		def propertiesList = testObject.getProperties()

		def isAnActiveProperty = false

		def numberOfProperties = propertiesList.size()

		for(int i = 0; i < numberOfProperties; i++){

			isAnActiveProperty = propertiesList[i].isActive()

			if (isAnActiveProperty){

				KeywordUtil.logInfo("Active property found")

				return propertiesList[i]

				break
			}
		}

		throw new Exception("Active property NOT found")
	}

	def returnTestObject(String testObjectName){

		def testObject

		def nameofCurrentMethod = new Object() {}.getClass().getEnclosingMethod().getName()

		try{

			if (!GlobalVariable.ObjectMap){

				KeywordUtil.logInfo("Good Slash: " + this.goodSlash + ", Bad Slash: " + this.badSlash)

				KeywordUtil.logInfo("Loading the object map")

				this.createTestObjectMap()
			}

			def testObjectRawPath = GlobalVariable.ObjectMap[testObjectName]

			if(testObjectRawPath){

				def testObjectPath = (testObjectRawPath).trim()

				def pathAndNameOfTestObject = returnScrubbedString(testObjectPath + testObjectName)

				KeywordUtil.logInfo("Path to test object: " + pathAndNameOfTestObject)

				testObject = findTestObject(pathAndNameOfTestObject)
			}
			else{

				throw new Exception(this.class.getName() + "." + nameofCurrentMethod + " - Test Object Name, " + testObjectName + ", does not exist")
			}
		}
		catch (Exception e){

			KeywordUtil.markWarning(e.getMessage())
		}

		testObject
	}

	def createTestObjectMap(){

		def objectRepositoryPath = returnScrubbedString(this.projectPath + "/" + this.objRepoDirectoryName + "/")

		KeywordUtil.logInfo("Object Repo Path: " + objectRepositoryPath)

		def currentDir = new File(objectRepositoryPath)

		def expectedFilePattern = /.*\.rs/

		def files = []

		currentDir.eachFileRecurse(groovy.io.FileType.FILES) {

			if (it.name ==~ expectedFilePattern) {

				files << it
			}
		}

		files.each {

			def absPath = it.absolutePath

			def testObjectName = absPath.substring(absPath.lastIndexOf(this.goodSlash) + 1, absPath.length() - 3)

			def pathToTestObject = (absPath.replace(objectRepositoryPath, "")).replace(testObjectName + ".rs", "")

			this.mapOfObjects[testObjectName] = pathToTestObject
		}

		if (!this.mapOfObjects){

			KeywordUtil.logInfo("Test Object Map was unable to be created from: " + objectRepositoryPath)

			throw new Exception()
		}
		else{

			GlobalVariable.ObjectMap = this.mapOfObjects
		}
	}

	def returnScrubbedString(String unscrubbedString){

		def scrubbedString = unscrubbedString.replace(this.badSlash,this.goodSlash).trim()
	}

	def determineGoodAndBadSlash(){

		def countSlash1 = this.projectPath.length() - this.projectPath.replace("\\", "").length();
		def countSlash2 = this.projectPath.length() - this.projectPath.replace("/", "").length();

		if (countSlash1 > countSlash2){
			this.badSlash = "/"
			this.goodSlash = "\\"
		}
		else{
			this.badSlash = "\\"
			this.goodSlash = "/"
		}

		GlobalVariable.GoodSlash = this.goodSlash
	}

	@Keyword
	def getTestObjectName(TestObject testObject){

		def logHeader

		def testObjectName

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def objectId = testObject.getObjectId()

			def start = objectId.lastIndexOf(GlobalVariable.GoodSlash) + 1

			testObjectName = objectId.substring(start, objectId.length())

			KeywordUtil.logInfo(logHeader + 'TestObject name found: ' + testObjectName)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		testObjectName
	}
}
