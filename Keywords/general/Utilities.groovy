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
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import groovy.io.FileType
import static groovy.io.FileType.FILES
import groovy.time.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.time.*
import java.time.format.*

import java.security.SecureRandom
import org.apache.commons.lang3.math.NumberUtils

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.*

import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

//import java.io.*;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Utilities extends TestObjects{

	def tempPath = "Images/Temp/"

	@Keyword
	def onExpectedUrl(String expectedUrl){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def currentUrl

		def timeStart = new Date()

		def timeoutPeriod = new TimeDuration(0,0,10,0)

		while (currentUrl != expectedUrl && (TimeCategory.minus(new Date(), timeStart) < timeoutPeriod)){

			currentUrl = WebUI.getUrl()

			KeywordUtil.logInfo(logHeader + "Current Url: " + currentUrl)
			KeywordUtil.logInfo(logHeader + "Expected Url: " + expectedUrl)
			KeywordUtil.logInfo(logHeader + "Current Url != Expected Url: " + (currentUrl != expectedUrl) as String)
		}

		def onExpectedUrl = currentUrl == expectedUrl

		KeywordUtil.logInfo(logHeader + "On expected Url: " + onExpectedUrl as String)

		onExpectedUrl
	}

	def isTimeExpired(Date timeStart, TimeDuration timeoutPeriod){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def timeExpired

		try {

			def duration = TimeCategory.minus(new Date(), timeStart)

			timeExpired = duration >= timeoutPeriod

			KeywordUtil.logInfo(logHeader + "Time Check - " + (duration.getSeconds() as String) + " of " +
					(timeoutPeriod.getSeconds() as String) + " seconds elapsed." + (timeExpired ? " TIME EXPIRED" : ""))
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		timeExpired
	}

	def trimList(ArrayList list){

		def holderList = []

		for (int i = 0 ; i < list.size() ; i++){

			holderList.add(list[i].trim())
		}

		holderList
	}

	@Keyword
	def getNumberFromString(String str){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def workingNumber

		try{

			KeywordUtil.logInfo(logHeader + "Attempting to convert '" + str + "' to a number")

			def workingString = str.replaceAll("[\$,%]+", "").trim()

			KeywordUtil.logInfo(logHeader + "Stripped out non-numeric values leaving: " + workingString)

			workingNumber = new NumberUtils().createNumber(workingString)
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "String '" + str + "' returns a '" + (workingNumber as String) + "' numeric value")

		workingNumber
	}

	@Keyword
	def isNotNullNotBlank(str){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def hasSomething = false

		try{

			hasSomething = str != null && !str.trim().isEmpty()
		}
		catch(Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		KeywordUtil.logInfo(logHeader + "'" + (str as String) + "' IS" + (hasSomething ? " NOT " : " ") + "null or blank")

		hasSomething
	}

	def getImage2(String imageUrl){
		//Step -1: We read the input SVG document into Transcoder Input
		//We use Java NIO for this purpose
		String svg_URI_input = Paths.get("chessboard.svg").toUri().toURL().toString();
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
		//Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
		OutputStream png_ostream = new FileOutputStream("chessboard.png");
		TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
		// Step-3: Create PNGTranscoder and define hints if required
		PNGTranscoder my_converter = new PNGTranscoder();
		// Step-4: Convert and Write output
		my_converter.transcode(input_svg_image, output_png_image);
		// Step 5- close / flush Output Stream
		png_ostream.flush();
		png_ostream.close();
	}

	def getImage(String imageUrl){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def img

		try {

			KeywordUtil.logInfo(logHeader + "Image url : " + imageUrl)

			def urlLength = imageUrl.length()

			if(urlLength > 4){

				def fileType = imageUrl.substring(urlLength - 4)

				if (fileType == '.svg'){

					//def fileName = getFileName(imageUrl)

					def fileName = convertFileName(getFileName(imageUrl), '_Url')

					//fileName = "Url - " + fileName.replace(".svg", ".png")

					//img = svg2png(imageUrl, fileName)

					svg2png(imageUrl, fileName)

					img = ImageIO.read(new File(this.tempPath + fileName))
				}
				else{

					def url = new URL(imageUrl)

					img = ImageIO.read(url)
				}
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())

		}

		KeywordUtil.logInfo(logHeader + "Image IS" + (img ? " " : " NOT ") + "being returned from Url")

		img
	}

	def getImage(TestObject testObject){

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def img

		try {

			def imagePath = testObject.getImagePath()

			KeywordUtil.logInfo(logHeader + "Image path: " + imagePath)

			def fileType = imagePath.substring(imagePath.length() - 4)

			if (fileType == '.svg'){

				//def fileName = getFileName(imagePath)

				def fileName = convertFileName(getFileName(imagePath), '_TO')

				//fileName = "TO - " + fileName.replace(".svg", ".png")

				//img = svg2png(imagePath, fileName)

				svg2png(imagePath, fileName)

				img = ImageIO.read(new File(this.tempPath + fileName))
			}
			else{

				img = ImageIO.read(new File(imagePath))
			}
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())

		}

		KeywordUtil.logInfo(logHeader + "Image IS" + (img ? " " : " NOT ") + "being returned from TestObject")

		img
	}

	def cleanUrl(String url){

		def cleanedUrl

		if(url.contains('url("')){

			def start = url.indexOf('"') + 1

			def end = url.lastIndexOf('"')

			cleanedUrl = url.substring(start, end)
		}
		else{

			cleanedUrl = url
		}

		cleanedUrl
	}

	@Keyword
	def compareImages(TestObject testObject, String imageUrl) {

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		def matches = false

		imageUrl = cleanUrl(imageUrl)

		try{

			def imgA = getImage(testObject)

			def imgB = getImage(imageUrl)

			matches = compareImages(imgA, imgB)
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())

		}

		KeywordUtil.logInfo(logHeader + "Images A & B" + (matches ? " " : " DO NOT ") + "match")

		matches
	}

	def compareImages(TranscoderInput imgA, TranscoderInput imgB) {

		imgA == imgB // Should fail, but is a placeholder
	}

	def compareImages(BufferedImage imgA, BufferedImage imgB) {

		// The images must be the same size.
		if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {

			return false
		}

		def width  = imgA.getWidth()

		def height = imgA.getHeight()

		// Loop over every pixel.
		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				// Compare the pixels for equality.
				if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {

					return false
				}
			}
		}

		return true
	}

	def svg2png(String imagePath, String tempFileName) {

		def directory = new File(this.tempPath)

		if (! directory.exists()){

			directory.mkdir()
		}

		TranscoderInput input_svg_image = new TranscoderInput(imagePath)

		OutputStream png_ostream = new FileOutputStream(this.tempPath + tempFileName)

		TranscoderOutput output_png_image = new TranscoderOutput(png_ostream)

		PNGTranscoder my_converter = new PNGTranscoder()

		my_converter.transcode(input_svg_image, output_png_image)

		png_ostream.flush()

		png_ostream.close()
	}

	/**
	 * Method for transforming SVG picture to PNG picture
	 *
	 * @param svgStream input stream of source SVG file
	 * @param pngStream output stream of target PNG file
	 * @param width     width of the target PNG file
	 * @param height    height of the target PNG file
	 */
	def convertSvgToPng(InputStream svgStream, OutputStream pngStream, Float width, Float height) {

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Width and height muset be bigger than zero");
		}

		try {
			TranscoderInput input = new TranscoderInput(svgStream);
			TranscoderOutput output = new TranscoderOutput(pngStream);

			PNGTranscoder converter = new PNGTranscoder();
			converter.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
			converter.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);

			converter.transcode(input, output);

		} catch (Exception ex) {

			throw new Exception("Exception during transforming SVG to PNG", ex);
		}
	}

	def getFileName(String filePath){

		def fileName

		def logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

		try{

			def start = filePath.lastIndexOf(GlobalVariable.GoodSlash) + 1

			fileName = filePath.substring(start, filePath.length())
		}
		catch (Exception e) {

			KeywordUtil.markWarning(logHeader + e.getMessage())

		}

		KeywordUtil.logInfo(logHeader + "File name: " + fileName as String)

		fileName
	}

	def convertFileName(String fileName, String modifier){

		def theDot = fileName.indexOf('.')

		def mainName = fileName.substring(0, theDot)

		def newName = mainName + modifier + ".png"

		newName
	}

	def normalizeDateTime(String dateTime){

		def logHeader

		def normalizedDateTime

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def dateTimeType, pattern

			if(dateTime.contains('AM') || dateTime.contains('PM')){

				dateTimeType = LocalDateTime

				pattern = "MM/dd/yyyy h:mm a"
			}
			else{

				dateTimeType = LocalDate

				pattern = "MM/dd/yyyy"
			}

			def formatter = DateTimeFormatter.ofPattern(pattern)

			normalizedDateTime = dateTimeType.parse(dateTime, formatter)

			KeywordUtil.logInfo(logHeader + "Normalized Date/Time: " + normalizedDateTime)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		normalizedDateTime
	}

	def normalizeDateTime(LocalDateTime dateTime){

		def logHeader

		def normalizedDateTime

		try{

			logHeader = this.class.getName() + "." + (new Object() {}.getClass().getEnclosingMethod().getName()) + " - "

			def pattern = "MM/dd/yyyy h:mm a"

			def formatter = DateTimeFormatter.ofPattern(pattern)

			normalizedDateTime = dateTime.format(formatter)

			KeywordUtil.logInfo(logHeader + "Normalized Date/Time: " + normalizedDateTime)
		}
		catch (Exception e){

			KeywordUtil.markWarning(logHeader + e.getMessage())
		}

		normalizedDateTime
	}
}