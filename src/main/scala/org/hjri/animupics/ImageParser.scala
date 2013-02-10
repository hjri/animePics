package org.hjri.animupics

import ib.ClassicBooru
import java.nio.file.{Files, Path}
import java.io.File
import javax.swing.text.html.HTML.Tag

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 1/30/13
 * Time: 2:06 AM
 */
class ImageParser(val path: Path) {

	val sourcesList:List[ImageBoard] = List(new ClassicBooru("http://gelbooru.com/index.php?page=dapi&s=post&q=index&tags=md5:", "http://www.gelbooru.com/index.php?page=dapi&s=tag&q=index&name="))

	val filename = path.getFileName.toString

	System.out.println("GOT NEW FILE: " + filename)
	if (filename.matches("^[0-9a-f]{32}\\.(jpe?g|png|gif)$")) {
		val md5 = filename.substring(0, filename.indexOf('.'))
		sourcesList foreach {
			booru => {
				val i: ImageMeta = booru.parseMD5(md5)
				i.tags foreach {
					tag =>{
						System.out.println(tag.prettyName)
						System.out.println("MAIN : " + tag.mainTag)
						System.out.println("SUB  : " + tag.subTag)
					}
				}
			}
		}
	}
}
