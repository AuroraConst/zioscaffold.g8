package com.axiom.pdf

import zio.*
import zio.test._

import com.axiom.pdf.Configuration

object AdobeConfigSpec extends ZIOSpecDefault:

  val specs = suite("http")(
    suite("health check")(
      test("truth") {
        for {
          _ <- ZIO.succeed(println("Running AdobeConfigSpec..."))
        } yield
          assertTrue(true) // Replace with actual assertions as needed
        
      },
      test("PdfConfig services loads correctly"){
        for {
          config <- ZIO.service[Configuration.PdfConfig]
        } yield
          assertTrue(config.adobereader.nonEmpty) &&
          assertTrue(config.formsdir.nonEmpty) &&
          assertTrue(config.orderspdf == "Orders.pdf") &&
          assertTrue(config.stampedpdf == "StampedOrders.pdf")
      }
    )
  )

  override def spec = specs.provide(
    Configuration.PdfConfig.layer
  )
