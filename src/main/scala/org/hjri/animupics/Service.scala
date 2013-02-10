package org.hjri.animupics


/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 2/8/13
 * Time: 11:02 PM
 */
object Service extends App {
	val s = new FilesystemWatcher()
	s start()
	System.out.println("Started")
}