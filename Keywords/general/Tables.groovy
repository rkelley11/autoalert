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

import groovy.time.*
import java.util.concurrent.TimeUnit

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil

public class Tables extends WrappedSelenium{

	@Keyword
	def returnRows(TestObject testObject){

		def logHeader

		def rows = []

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def by = By.cssSelector('tr')

			def table = findElement(testObject, 1)

			def count

			if(table){

				rows = findElements(by, table, 1)
			}

			if(rows){

				count = rows.size()
			}
			else{

				count = 0
			}

			KeywordUtil.logInfo(logHeader + "There "+ (rows.size() == 1 ? "was " : "were ") + (rows.size() as String) + " row" + (rows.size() == 1 ? "" : "s") + " found")
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		rows
	}
}
