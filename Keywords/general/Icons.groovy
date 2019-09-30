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

public class Icons extends WrappedSelenium {

	def getIconType(WebElement activity){

		def logHeader

		def iconType

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def imgBy = By.cssSelector('img')

			def imgs = findElements(imgBy, activity, 1)

			if(imgs){

				def src = getAttribute(imgs[0], 'src')

				if(src.contains('icon-o2o')){

					iconType = 'o2o'
				}
				else if(src.contains('icon-sms')){

					iconType = 'text'
				}
				else if(src.contains('icon-Pando')){

					iconType = 'pando'
				}
				else{

					throw new Exception("Unable to determine icon from src(" + (src as String) + ")")
				}
			}
			else{

				def iconBy = By.cssSelector('icon')

				def icons = findElements(iconBy, activity, 1)

				if(icons){

					def webIcon = getAttribute(icons[0], 'icon')

					if(webIcon){

						iconType = webIcon
					}
					else{

						def attributeName = 'class'

						def attributeInfo = getAttribute(icons[0], attributeName).replace('icon-0', '')

						def marker = 'aa-icon-core-'

						def start = attributeInfo.lastIndexOf(marker) + marker.length()

						def end = attributeInfo.lastIndexOf('-')

						iconType = attributeInfo.substring(start, end)
					}
				}
				else{

					throw new Exception("Icon was not found")
				}
			}

			KeywordUtil.logInfo(logHeader + "Icon type found: " + iconType)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		iconType
	}

	def getIconTypeOLD(WebElement activity){

		def logHeader

		def iconType

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def imgBy = By.cssSelector('.activity-icon img')

			def imgs = findElements(imgBy, activity, 1)

			if(imgs){

				def src = getAttribute(imgs[0], 'src')

				if(src.contains('icon-o2o')){

					iconType = 'o2o'
				}
				else if(src.contains('icon-sms')){

					iconType = 'text'
				}
				else if(src.contains('icon-Pando')){

					iconType = 'pando'
				}
				else{

					throw new Exception("Unable to determine icon from src(" + (src as String) + ")")
				}
			}
			else{

				def iconBy = By.cssSelector('.activity-icon icon')

				def icons = findElements(iconBy, activity, 1)

				if(icons){

					def webIcon = getAttribute(icons[0], 'icon')

					if(webIcon){

						iconType = webIcon
					}
					else{

						def attributeName = 'class'

						def attributeInfo = getAttribute(icons[0], attributeName).replace('icon-0', '')

						def marker = 'aa-icon-core-'

						def start = attributeInfo.lastIndexOf(marker) + marker.length()

						def end = attributeInfo.lastIndexOf('-')

						iconType = attributeInfo.substring(start, end)
					}
				}
				else{

					throw new Exception("Icon was not found")
				}
			}

			KeywordUtil.logInfo(logHeader + "Icon type found: " + iconType)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		iconType
	}
}