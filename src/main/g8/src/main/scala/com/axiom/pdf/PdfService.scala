package com.axiom.pdf

import com.lowagie.text.pdf.PdfReader
import zio._
import com.axiom.pdf.Configuration.PdfConfig
import com.lowagie.text.pdf.{PdfStamper,PdfContentByte}
import java.io.FileOutputStream
import better.files._

object pdfutils :
  // Service Definition
  trait PdfService {
    // def canvas(pageNumber: Int): Task[com.lowagie.text.pdf.PdfContentByte]
    def showAdobeReader(): Task[Unit]
    def drawRectangle(canvas:PdfContentByte): Task[Unit]
    def addText(text: String, x: Float, y: Float): Task[Unit]
  }

  // Live Implementation
  final case class PdfServiceLive(
    pdfReader: PdfReader,
    config: PdfConfig
  ) extends PdfService :

    lazy val stampedPdfPath = (config.formsdir / config.stampedpdf).path.toAbsolutePath().toString()

    lazy val acqReleasePdfStamper = 
      ZIO.acquireRelease{
        
        ZIO.attempt {
          Console.printLine("Creating PDF stamper...")
          Console.printLine(s"output file for pdfstamper = \${(config.formsdir / config.stampedpdf).toJava.toString()}")
          new PdfStamper(pdfReader, new FileOutputStream((config.formsdir / config.stampedpdf).toJava))
          
        }
      }{ stamper =>  ZIO.succeed(stamper.close())}
      
    // def canvas(pageNumber: Int): Task[com.lowagie.text.pdf.PdfContentByte] =
    //   ZIO.scoped {
    //     for{ 
    //       pdfs  <- acqReleasePdfStamper
    //       c     <- ZIO.attempt {pdfs.getOverContent(pageNumber)}
    //     } yield c
    //   }
    def drawRectangle(canvas:PdfContentByte): Task[Unit] =  
      for {
        _ <- ZIO.succeed {
          canvas.setLineWidth(3f)
          canvas.rectangle(50, 50, 500, 500)
          canvas.stroke()
        }
      } yield ()


    def showAdobeReader(): Task[Unit] =
      import scala.sys.process._
      for {
        cmdString <- ZIO.succeed(s"\${config.adobereader}  \${stampedPdfPath}")
        _ <- Console.printLine(s"Running command: \$cmdString")
        r <- ZIO.attempt { s"\${config.adobereader}  \${stampedPdfPath}".! }
        _ <- Console.printLine(s"Adobe Reader process result: \$r")
      } yield ()

    def addText(text: String, x: Float, y: Float): Task[Unit] = ???
  

  object PdfService {

    def pdfReader (config:PdfConfig) = 
      import better.files._
      new PdfReader((config.formsdir / config.orderspdf).path.toAbsolutePath().toString())

    // Layer Definition
    val live: ZLayer[Configuration.PdfConfig & Scope, Throwable, PdfService] =
      ZLayer.fromZIO {
        for {
          config      <- ZIO.service[Configuration.PdfConfig]
          pdfReader   <- ZIO.attempt{pdfReader(config)}
          pdfService  <- ZIO.succeed(PdfServiceLive(pdfReader, config))
        } yield pdfService
      }
    }



