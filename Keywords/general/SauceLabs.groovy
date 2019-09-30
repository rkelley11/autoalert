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

import com.kms.katalon.core.util.KeywordUtil

public class SauceLabs {

	def updateSauceLabs(Boolean didTestPass){

		def url, basicAuth, http, out, result

		url = new URL(Urls.SAUCE_LABS + GlobalVariable.SessionId)
		basicAuth = "Basic " + new String(Base64.getEncoder().encode(GlobalVariable.SauceLabs_Credentials.getBytes()))
		http = url.openConnection()

		http.setDoOutput(true)
		http.setRequestMethod('PUT')
		http.setRequestProperty("Authorization", basicAuth);
		http.setRequestProperty('Content-Type', 'application/json')

		out = new OutputStreamWriter(http.outputStream)
		result = '{"passed": ' + (didTestPass as String) + '}'
		out.write(result)
		out.close()

		http.inputStream
	}
}
