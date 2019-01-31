#!/bin/sh
exec scala -J-Xmx6g "$0" "$@"
!#

object Main extends App {

  val trace  = scala.xml.XML.loadFile(args(0))

  trace.nonEmptyChildren.filter(_.label == "message").foreach{msg=>
    var timestamp = (msg \ "timestamp").text 
    (msg \ "characters" \ "character").foreach{ ch =>
      println( (List("character",timestamp,
         (ch \ "id").text,
         (ch \ "status").text,
         (ch \ "position" \ "x").text,
         (ch \ "position" \ "y").text,
         (ch \ "velocity" \ "x").text,
         (ch \ "velocity" \ "y").text
      )).mkString(","))
    }
  }

}
Main.main(args)
