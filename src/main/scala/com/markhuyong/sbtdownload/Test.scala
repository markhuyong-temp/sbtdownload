package com.markhuyong.sbtdownload

import scala.io.Source
import java.util.jar.{JarEntry, Manifest, JarVerifier}
import java.util.jar.{JarFile, JarInputStream}
import java.io.{BufferedInputStream, ByteArrayInputStream, FileInputStream, File}
import java.util.zip.{ZipEntry, ZipInputStream}
import scala.annotation.tailrec
import org.jsoup.Jsoup
import java.util.regex.Pattern
import scala.util.matching.Regex
import scala.collection.mutable.ListBuffer

/**
 * Created by mark on 4/18/14.
 */
object Test extends App{
  val base = "http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/"

  val location = "sbt-launch.jar"

  val jis = getZipInputStream(location)
  var locversion = "1.2.1"

  getManifest(jis)

println(locversion)

  getMaxVersion(base).map {case (url, version) =>
    if(isLastVerion(version, locversion))
      println("ok")
    else
      println(s"download latest version of sbt, with link: ${base + url + location}")
  }

  @tailrec
  def getManifest(in :ZipInputStream) :Option[String] = {
    in.getNextEntry match {
      case null => None
      case e:ZipEntry if(JarFile.MANIFEST_NAME.equalsIgnoreCase(e.getName())) =>{
        val man = new Manifest(in)
        val version = man.getMainAttributes.getValue("Implementation-Version")
        println(version)
        locversion = version
        Some(version)
      }
      case e:ZipEntry => getManifest(in)
    }
  }

  def getZipInputStream(name :String) = {
    new ZipInputStream(
      new FileInputStream (
        new File(getClass.getClassLoader.getResource(name).toURI())
      )
    )
  }

  def versionCompare(str1 :String,  str2 :String) =
  {
    val vals1 = str1.split("\\.")
    val vals2 = str2.split("\\.")
    var i = 0
    var ret =0
    // set index to first non-equal ordinal or length of shortest version string
    while (i < vals1.length && i < vals2.length && vals1(i).equals(vals2(i)))
    {
      i = i + 1
    }
    // compare first non-equal ordinal number
    if (i < vals1.length && i < vals2.length)
    {
      ret = vals1(i).toInt.compareTo(vals2(i).toInt)
//      Integer.signum(diff)
    }
    // the strings are equal or one string is a substring of the other
    // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
    else
    {
//      Integer.signum(vals1.length - vals2.length)
      ret = vals1.length - vals2.length
    }
   ret
  }

  def getMaxVersion(url :String) = {

    val lines = Jsoup.connect(url)
      .ignoreContentType(true)
      .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
      .header("Accept-Encoding", "gzip,deflate,sdch")
      .header("Accept-Language", "zh")
      .header("Cache-Control", "max-age=0")
      .header("Host", "zh.wikipedia.org")
      .header("Pragma:", "no-cache")
      //.header("Cookie", "centralnotice_bucket=1-4.2; uls-previous-languages=%5B%22zh%22%5D; mediaWiki.user.sessionId=ozfVvWSjftG4Z4obJaAPOdS8bPdTHrlb; centralnotice_bannercount_fr12=1")
      .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
      .referrer("http://zh.wikipedia.org/wiki/Main_Page")
      .followRedirects(true)
      .timeout(14000).get().body.select("a").iterator()


    val regx =  new Regex("(\\d+\\.\\d+\\.\\d+)")
//    val lines = Source.fromURL(url).getLines()
//    for(line <- lines){
//     line match {
//       case regx(version) => println(version)
//       case _ =>
//     }
//    }

      val lst = ListBuffer(("url","version"))

      while(lines.hasNext){
        val elm =lines.next()
        val url = elm.attr("href")
        url.init match {
          case regx(ver) =>  lst.append((url, ver))
          case _ => println(url + "wokao")
        }
        }
    val sorted = lst.drop(1).sortWith((t1,t2) => versionCompare(t1._2, t2._2) > 0).toList
    println(sorted.mkString(","))
    sorted.headOption
}

  def isLastVerion(max:String, cur:String) = {
    if(versionCompare(max, cur) < 0) true else false
  }

}
