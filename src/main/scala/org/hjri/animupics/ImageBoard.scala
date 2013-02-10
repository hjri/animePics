package org.hjri.animupics

import java.net.URL

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 2/8/13
 * Time: 8:33 PM
 */
abstract class ImageBoard(private val url: String) {
	def parseMD5(md5: String): ImageMeta
}
