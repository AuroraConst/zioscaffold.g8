package com.axiom.pdf

import com.lowagie.text.pdf.{BaseFont, ColumnText}
import com.lowagie.text.{Font, Phrase}

object pdfrectangle:
  // Exported instances for convenience
  val bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)
  val bold = new Font(Font.HELVETICA, 12, Font.BOLD)
  val boldUnderline = new Font(Font.HELVETICA, 12, Font.UNDERLINE | Font.BOLD)
  val italic = new Font(Font.HELVETICA, 14, Font.ITALIC)

  trait Rect :
    val x: Int
    val y: Int
    val width: Int
    val height: Int
    def canvas: com.lowagie.text.pdf.PdfContentByte // Make it abstract
    def draw() = 
      canvas.rectangle(x, y, width, height)
      canvas.stroke()

  case class LineSummary(canvas: com.lowagie.text.pdf.PdfContentByte) extends Rect:
    val x = 12
    val y = 580
    val width = 200
    val height = 80

    lazy val columnText = new ColumnText(canvas);

    def writeText(text: String): Unit = {
        columnText.setSimpleColumn(
        x + 4, // x position (from left)
        y + 4, // y position (from bottom)
        x + width - 4, // width
        y + height - 4 // height
        )
        canvas.beginText()
        canvas.setFontAndSize(bf, 12)
        columnText.addElement(new Phrase(s"Line Summary: \$text", new Font(Font.HELVETICA, 12)))
        columnText.go()
    }
