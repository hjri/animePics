package org.hjri.animupics
import java.util
import scala.util.matching.Regex
import java.lang.IllegalStateException

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 2/9/13
 * Time: 3:03 AM
 */



class Tag(val name: String, val tagType: String) {

	val CHARACTER = "character"
	val AUTHOR = "author"
	private val cosplayPattern: Regex = "(.*?)_(\\(\\w+\\))" r
	private val countPattern: Regex = "(\\d+)(\\w+)" r

	val mainTag: String = {
		try{
			cosplayPattern.findAllIn(name).group(1)
		}catch{
			case e:IllegalStateException => name
		}
	}

	val subTag: String = {
		try{
			cosplayPattern.findAllIn(name).group(2)
		}catch{
			case e:IllegalStateException => ""
		}
	}
	val prettyName: String = {
		var pretty: String = name
		pretty = countPattern.replaceAllIn(pretty, "$1 $2")
		pretty.split("_").map(_.capitalize).mkString(" ")
	}

}
