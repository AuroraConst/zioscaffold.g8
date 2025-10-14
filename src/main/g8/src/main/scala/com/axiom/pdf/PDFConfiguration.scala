package com.axiom.pdf


import zio._
import zio.config._
import zio.config.magnolia._
import zio.config.typesafe._

object Configuration:

  final case class PdfConfig(adobereader: String,formsdir: String, orderspdf: String, stampedpdf: String)
  

  object PdfConfig:
    val layer: ZLayer[Any, Config.Error, PdfConfig] = 
      ZLayer.fromZIO(
        read(deriveConfig[PdfConfig].nested("pdf").from(ConfigProvider.fromResourcePath())) 
       
      )
