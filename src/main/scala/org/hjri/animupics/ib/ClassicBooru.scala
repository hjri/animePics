package org.hjri.animupics.ib

import org.hjri.animupics.{ImageMeta, ImageBoard, Tag}
import java.net.URL
import xml.{NodeSeq, Elem}

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 2/8/13
 * Time: 8:41 PM
 */
class ClassicBooru(private val url:String, private val tags: String) extends ImageBoard(url) {

	def parseMD5(md5: String): ImageMeta = {
		val xml: Elem = scala.xml.XML.load(new URL(url + md5));
		val count = xml \\ "posts" \ "@count"
		if (count.text != "1") {
			throw new IllegalArgumentException("More than one image found for MD5 supplied")
		}
		val post: NodeSeq = xml \\ "posts" \\ "post" last
		val tags: String =  post \ "@tags" text
		val rating: String = post \ "@rating" text
		val tagList: Array[String] = tags.split(" ")
		def localRating(s: String): Int = s match {
			case "s" => -1
			case "q" => 0
			case "e" => 1
			case _ => Int.MinValue
		}
		val s: Array[Tag] = tagList.map(f => {
			new Tag(f, "none")
		})
		new ImageMeta(s, md5, localRating(rating));
	}
}
