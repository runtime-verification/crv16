
import scales.xml._
import ScalesXml._
import java.io.FileReader
import java.io.Reader
import java.io.Writer
import java.io.FileWriter
import spray.json._
import DefaultJsonProtocol._
import jawn.support.spray.Parser
import jawn.support.spray.Parser._
import jawn.AsyncParser
import com.github.tototoshi.csv._
import org.rogach.scallop._

object Main extends App {
  
  val inFormats = Set("xml", "csv", "json")
  val outFormats = Set("xml", "csv", "json")
  
  object Conf extends ScallopConf(args) {
    val inputFormat: ScallopOption[String] = trailArg[String]("input-format", descr = s"input format: ${inFormats.mkString(", ")}", required = true, validate = inFormats.contains)
    val outputFormat: ScallopOption[String] = trailArg[String]("output-format", descr = s"output format: ${outFormats.mkString(", ")}", required = true, validate = outFormats.contains)
    val inputFile: ScallopOption[String] = trailArg[String]("input-file", descr = s"input file", required = true)
    val outputFile: ScallopOption[String] = trailArg[String]("output-file", descr = s"output file", required = true)
    errorMessageHandler = (msg: String) => {
      println(msg)
      printHelp()
      sys.exit(2)
    }
    banner(s"Usage: java -jar traceconv.jar [input-format] [output-format] [input-file] [output-file]")
  }
  
  val parse = Conf.inputFormat() match {
    case "xml" => () => {
      parseXML(new FileReader(Conf.inputFile()))
    }
    case "json" => () => {
      parseJSON(new FileReader(Conf.inputFile()))
    }
    case "csv" => () => {
       parseCSV(CSVReader.open(Conf.inputFile()))
    }
    case _ => throw new Exception()
  }
  
  Conf.outputFormat() match {
    case "xml" =>
      val writer = new FileWriter(Conf.outputFile())
      writeXML(writer, parse())
      writer.close()
    case "json" =>
      val writer = new FileWriter(Conf.outputFile())
      writeJSON(writer, parse())
      writer.close()
    case "csv" =>
      val csvwriter = CSVWriter.open(Conf.outputFile())
      writeCSVBody(csvwriter, parse(), writeCSVHeader(csvwriter, parse()))
      csvwriter.close()
    case _ => throw new Exception()
  }
  
  def writeCSVHeader(out: CSVWriter, data: TraversableOnce[(String, Iterable[(String, String)])]): Map[String, Int] = {
    val list = (for {
      (_, fields) <- data
      (name, _) <- fields
    } yield name).toSet.toList
    out.writeRow("event" +: list)
    list.zipWithIndex.toMap
  }
  
  def writeCSVBody(out: CSVWriter, data: TraversableOnce[(String, Iterable[(String, String)])], name2index: Map[String, Int]) {
    for ((name, fields) <- data) {
      val map = fields.map{case (a, b) => (name2index(a), b)}.toMap
      val list = for (i <- 0 until name2index.size) yield map.getOrElse(i, "")
      out.writeRow(name +: list)
    }
  }
  
  def parseCSV(in: CSVReader): Iterator[(String, Iterable[(String, String)])] = {
    in.readNext() match {
      case Some(_ :: names) =>
        for (line <- in.iterator) yield {
          line match {
            case name :: values => (name, for {
              (value, index) <- values.zipWithIndex
              if !"".equals(value)
            } yield (names(index), value))
            case _ => throw new Exception()
          }
        }
      case _ => throw new Exception()
    }
  }
  
  def writeXML(out: Writer, data: TraversableOnce[(String, Iterable[(String, String)])]) {
      val temp: Iterator[PullType] = Iterator.single(Left(Elem("log"))) ++ (
        for ((name, fields) <- data) yield {
          Iterator(Left(Elem("event")), Left(Text("\n")), Left(Elem("name")), Left(Text(name)), Right(EndElem("name")), Left(Text("\n"))) ++ (
            for ((key, value) <- fields) yield {
              Iterator(Left(Elem("field")), Left(Elem("name")), Left(Text(key)), Right(EndElem("name")), Left(Elem("value")), Left(Text(value)), Right(EndElem("value")), Right(EndElem("field")), Left(Text("\n")))
            }
          ).flatten ++ Iterator(Right(EndElem("event")), Left(Text("\n")))
        }
      ).flatten ++ Iterator.single(Right(EndElem("log")))
      writeTo(temp, out)
  }

  def parseXML(in: Reader): TraversableOnce[(String, Iterable[(String, String)])] = {
    val pull = pullXml(in)
    for (event <- iterate(List("log".l, "event".l), pull)) yield {
      val name = string(event \* "name")
      val fields = for (field <- event \* "field") yield (string(field \* "name"), string(field \* "value"))
      (name, fields.toList)
    }
  }
 
  def writeJSON(out: Writer, data: TraversableOnce[(String, Iterable[(String, String)])]) {
    for ((name, fields) <- data) {
      out.write(Map("name" -> name.toJson, "fields" -> fields.toMap.toJson).toJson.toString())
      out.write("\n")
    }
  }
  
  def parseJSON(in: Reader): Iterator[(String, Iterable[(String, String)])] = {
    for (value <- parseJSON2AST(in)) yield {
      val map = value.convertTo[Map[String, JsValue]]
      val name = map("name").convertTo[String]
      val fields = map("fields").convertTo[Map[String, String]]
      (name, fields)
    }
  }
  
  def parseJSON2AST(in: Reader): Iterator[JsValue] = {
    val p = Parser.async(mode = AsyncParser.ValueStream)
    (
      for {
        buf <- new BufferedIterator(in, 2^20)
        js <- p.absorb(buf) match {
          case Left(e) => throw e
          case Right(seq) => seq
        }
      } yield js
    ) ++ (
        p.finish() match {
          case Left(e) => throw e
          case Right(seq) => seq
        }
    )
  }
  
  class BufferedIterator(in: Reader, bufferSize: Int) extends Iterator[String] {
    val buf = new Array[Char](bufferSize)
    var i = in.read(buf)
    def hasNext = i != -1
    def next() = {
      val s = new String(buf, 0, i)
      i = in.read(buf)
      s
    }
  }
}
