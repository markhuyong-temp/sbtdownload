package com.markhuyong.sbtdownload

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, IOException}
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.jar
import java.util.jar.{JarFile, JarEntry, Manifest, JarVerifier}
import sun.security.util.ManifestEntryVerifier
import scala.collection.JavaConverters._

/**
 * reimplement with scala version
 */
class JarInputStream(in :InputStream) extends ZipInputStream(in) {
//  // Fields
//
//  /** The manifest for this file or null when there was no manifest. */
//  private var man: Manifest = null
//
//  /** The first real JarEntry for this file. Used by readManifest() to store
//       an entry that isn't the manifest but that should be returned by
//       getNextEntry next time it is called. Null when no firstEntry was read
//       while searching for the manifest entry, or when it has already been
//       returned by getNextEntry(). */
//  private var first: JarEntry = null
//
////  private var jv :Jar.JarVerifier = null
//  private var mev :ManifestEntryVerifier = null
//  private var doVerify :Boolean = false
//  private var tryManifest :Boolean =false
//
//  @throws(classOf[IOException])
//  def this(in: InputStream, verify: Boolean) = {
//    this(in)
//    this.doVerify = verify
//
//    // This implementation assumes the META-INF/MANIFEST.MF entry
//    // should be either the first or the second entry (when preceded
//    // by the dir META-INF/). It skips the META-INF/ and then
//    // "consumes" the MANIFEST.MF to initialize the Manifest object.
//    var e = super.getNextEntry.asInstanceOf[JarEntry]
//    if (e != null && e.getName().equalsIgnoreCase("META-INF/"))
//      e = super.getNextEntry.asInstanceOf[JarEntry]
//    first = checkManifest(e)
//  }
//
//  private def checkManifest(e :JarEntry) :JarEntry =
//    {
//      if (e != null && JarFile.MANIFEST_NAME.equalsIgnoreCase(e.getName())) {
//        man = new Manifest(this)
//        val bytes = Stream.continually(this.in.read).takeWhile(-1 !=).map(_.toByte).toArray
//        man.read(new ByteArrayInputStream(bytes))
//        closeEntry();
//        if (doVerify) {
////          jv = new JarVerifier(bytes)
//          mev = new ManifestEntryVerifier(man)
//        }
//        super.getNextEntry.asInstanceOf[JarEntry]
//      } else e
//    }
//
//  private def getBytes(is :InputStream) =
//    {
//      Stream.continually(is.read).takeWhile(-1 !=).map(_.toByte).toArray
//    }
//
//  /**
//   * Reads the next ZIP file entry and positions the stream at the
//   * beginning of the entry data. If verification has been enabled,
//   * any invalid signature detected while positioning the stream for
//   * the next entry will result in an exception.
//   * @exception ZipException if a ZIP file error has occurred
//   * @exception IOException if an I/O error has occurred
//   * @exception SecurityException if any of the jar file entries
//   *         are incorrectly signed.
//   */
//  def  getNextEntry = {
////    JarEntry e;
////    if (first == null) {
////      e = (JarEntry)super.getNextEntry();
////      if (tryManifest) {
////        e = checkManifest(e);
////        tryManifest = false;
////      }
////    } else {
////      e = first;
////      if (first.getName().equalsIgnoreCase(JarIndex.INDEX_NAME))
////        tryManifest = true;
////      first = null;
////    }
////    if (jv != null && e != null) {
////      // At this point, we might have parsed all the meta-inf
////      // entries and have nothing to verify. If we have
////      // nothing to verify, get rid of the JarVerifier object.
////      if (jv.nothingToVerify() == true) {
////        jv = null;
////        mev = null;
////      } else {
////        jv.beginEntry(e, mev);
////      }
////    }
////    return e;
//  }
//  /**
//   * Returns the <code>Manifest</code> for this JAR file, or
//   * <code>null</code> if none.
//   *
//   * @return the <code>Manifest</code> for this JAR file, or
//   *         <code>null</code> if none.
//   */
//  def  getManifest = man
//
//
//  /**
//   * Reads the next JAR file entry and positions the stream at the
//   * beginning of the entry data. If verification has been enabled,
//   * any invalid signature detected while positioning the stream for
//   * the next entry will result in an exception.
//   * @return the next JAR file entry, or null if there are no more entries
//   * @exception ZipException if a ZIP file error has occurred
//   * @exception IOException if an I/O error has occurred
//   * @exception SecurityException if any of the jar file entries
//   *         are incorrectly signed.
//   */
//  def  getNextJarEntry = getNextEntry.asInstanceOf[JarEntry]
//
//  /**
//   * Reads from the current JAR file entry into an array of bytes.
//   * If <code>len</code> is not zero, the method
//   * blocks until some input is available; otherwise, no
//   * bytes are read and <code>0</code> is returned.
//   * If verification has been enabled, any invalid signature
//   * on the current entry will be reported at some point before the
//   * end of the entry is reached.
//   * @param b the buffer into which the data is read
//   * @param off the start offset in the destination array <code>b</code>
//   * @param len the maximum number of bytes to read
//   * @return the actual number of bytes read, or -1 if the end of the
//   *         entry is reached
//   * @exception  NullPointerException If <code>b</code> is <code>null</code>.
//   * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
//   * <code>len</code> is negative, or <code>len</code> is greater than
//   * <code>b.length - off</code>
//   * @exception ZipException if a ZIP file error has occurred
//   * @exception IOException if an I/O error has occurred
//   * @exception SecurityException if any of the jar file entries
//   *         are incorrectly signed.
//   */
//   def read( b : Array[Byte], off :Int, len :Int) {
////    int n;
////    if (first == null) {
////      n = super.read(b, off, len);
////    } else {
////      n = -1;
////    }
////    if (jv != null) {
////      jv.update(n, b, off, len, mev);
////    }
////    return n;
//  }
//
//  /**
//   * Creates a new <code>JarEntry</code> (<code>ZipEntry</code>) for the
//   * specified JAR file entry name. The manifest attributes of
//   * the specified JAR file entry name will be copied to the new
//   * <CODE>JarEntry</CODE>.
//   *
//   * @param name the name of the JAR/ZIP file entry
//   * @return the <code>JarEntry</code> object just created
//   */
//  protected def createZipEntry(name :String) = {
//    val e = new JarEntry(name);
//    if (man != null) {
//      e.attr = man.getAttributes(name)
//    }
//   e
//  }
}


//}

object JarInputStream {
//  def apply(in :InputStream) =
//  {
//    new JarInputStream(in, true)
//  }
//
//  def apply(in :InputStream, verify :Boolean) = {
//    new JarInputStream(in, verify)
//  }
}



