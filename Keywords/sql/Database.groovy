package sql

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

import java.sql.*

import com.kms.katalon.core.util.KeywordUtil

public class Database {

	def executeQuery(String query) {
		
		def logHeader

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def connectionString =
					"jdbc:sqlserver://" + GlobalVariable.DB_IP +
					";databaseName=" + GlobalVariable.DB_Database +
					";user=" + GlobalVariable.DB_Username +
					";password=" + GlobalVariable.DB_Password
					
			KeywordUtil.logInfo(logHeader + 'Connection string: ' + connectionString)

			def connection = DriverManager.getConnection(connectionString)

			def statement = connection.createStatement()

			def resultSet = statement.executeQuery(query)
		}
		catch (SQLException e) {

			e.printStackTrace()
		}
	}

	/**
	 * Runs a query and then places results into a hashmap for each column
	 * @param query - The query to be executed
	 * @param columnNames - The column names in the results which will be used as the keys in the returned hash map
	 * @return A hash map containing the results.  Key=column name
	 */
	def executeQuery(String query, ArrayList columnNames) {

		def values = [:]

		def result = executeQuery(query)

		result.next()

		for(int i=0; i<columnNames.size();i++){

			values[columnNames[i]] = result.getObject(i + 1)
		}

		values
	}
}