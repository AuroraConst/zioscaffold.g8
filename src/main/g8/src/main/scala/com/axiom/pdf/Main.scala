package com.axiom.pdf

import zio._

object PdfServiceExample extends ZIOAppDefault {

  // Example program that uses PdfService
  val pdfProgram: ZIO[pdfutils.PdfService & Configuration.PdfConfig, Throwable, Unit] = for {
    config  <- ZIO.service[Configuration.PdfConfig]
    pdf <- ZIO.service[pdfutils.PdfService]
    
    // Log the Adobe Reader path from config
    _         <- Console.printLine(s"Adobe Reader path: \${config.adobereader}")
    // canvas    <- pdf.canvas(1)
    // _         <- pdf.drawRectangle(canvas)
    _         <- pdf.showAdobeReader()

    // Add some text to the fizrst page
    // _ <- service.addText("Hello World!", 100f, 700f)
    // _ <- service.addText("This is a ZIO PDF example", 100f, 680f)
    
    // Draw a line
    // _ <- service.drawLine(100f, 650f, 400f, 650f)
    
    // Create a new page
    // _ <- service.newPage()
    
    // Add content to the second page
    // _ <- service.addText("Page 2", 100f, 700f)
    // _ <- service.drawLine(50f, 680f, 500f, 680f)
    
    _ <- Console.printLine("PDF created successfully!")
  } yield ()

  // Run the program with the PdfService layer
  def run: ZIO[Any, Any, Any] = 
    pdfProgram.provide(
      Configuration.PdfConfig.layer,
      pdfutils.PdfService.live,
      Scope.default
    )
}


