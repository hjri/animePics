package org.hjri.animupics

import java.nio.file._
import akka.actor.Props
import java.util.UUID
import akka.actor.Actor
import java.io.FileInputStream
import java.security.MessageDigest

/**
 * Created with IntelliJ IDEA.
 * User: jcd
 * Date: 2/1/13
 * Time: 2:14 AM
 */
class FilesystemWatcher {

	abstract class Message;

	case class Next() extends Message

	case class Process()

	case class ProcessImage(path: Path)

	case class Halt()

	import java.nio.file.StandardWatchEventKinds

	private val akkasys = akka.actor.ActorSystem.create()
	private val watcher = akkasys.actorOf(Props(new FilesystemWatcherActor("C:\\Users\\jcd\\Downloads")))

	def start(){
		watcher ! Next
	}

	class FilesystemWatcherActor(pathStr: String) extends Actor {
		val path: Path = FileSystems.getDefault.getPath(pathStr)
		val service: WatchService = path.getFileSystem.newWatchService
		path.register(service, StandardWatchEventKinds.ENTRY_CREATE)

		def receive = {
			case Halt => context.stop(self);
			case Process =>
				val key: WatchKey = service.take()
				val event = key.pollEvents().iterator();
				while (event.hasNext) {
					val e: WatchEvent[Path] = event.next.asInstanceOf[WatchEvent[Path]]
					val addedPath: Path = path.resolve(e.context())
					context.actorOf(Props(new ImageParserActor), name = UUID.randomUUID().toString) ! ProcessImage(addedPath)
				}
				if (!key.reset) {
					key.cancel()
					service.close()
					self ! Halt
				}
				self ! Next;
			case Next =>
				self ! Process;
		}
	}

	class ImageParserActor extends Actor {
		def receive = {
			case ProcessImage(path: Path) => {
				val l: ImageParser = new ImageParser(path)
			}
		}
	}

}
