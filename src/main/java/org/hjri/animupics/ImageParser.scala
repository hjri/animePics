package org.hjri.animupics

import ib.Gelbooru
import java.nio.file.{Files, Path}
import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 1/30/13
 * Time: 2:06 AM
 */
class ImageParser(val path: Path) {
	val gelbooru = new Gelbooru
	val filename = path.getFileName.toString
	System.out.println("GOT NEW FILE: " + filename)
	if (filename.matches("^[0-9a-f]{32}\\.(jpe?g|png|gif)$")) {
		val md5 = filename.substring(0, filename.indexOf('.'))
		gelbooru.parseMD5(md5).tags.foreach((tag: String) => {System.out.println(tag)})
	}
}
