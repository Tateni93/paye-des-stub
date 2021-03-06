/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it

import akka.stream.Materializer
import common.LogSuppressing
import org.scalatest.TestData
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.http.Status.OK
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.{Application, Mode}
import uk.gov.hmrc.play.test.UnitSpec

import scala.util.Random

/**
  * Testcase to verify the capability of integration with the API platform.
  *
  * 1a, To expose API's to Third Party Developers, the service needs to make the API definition available under api/definition GET endpoint
  * 1b, The endpoints need to be defined in an application.raml file for all versions  For all of the endpoints defined documentation will be provided and
  * be available under api/documentation/[version]/[endpoint name] GET endpoint
  * Example: api/documentation/1.0/Fetch-Some-Data
  *
  * See: https://confluence.tools.tax.service.gov.uk/display/ApiPlatform/API+Platform+Architecture+with+Flows
  */
class PlatformIntegrationSpec extends UnitSpec with ScalaFutures with GuiceOneAppPerTest with LogSuppressing {
  implicit def mat = app.injector.instanceOf[Materializer]

  val stubHost = "example.com"
  val stubPort = Random.nextInt

  override def newAppForTest(testData: TestData): Application = GuiceApplicationBuilder()
    .configure("run.mode" -> "Test")
    .in(Mode.Test).build()

  "microservice" should {

    "provide definition endpoint and documentation endpoint for each api" in {
      val result = await(route(app, FakeRequest(GET, "/api/definition"))).get
      status(result) shouldBe OK
      bodyOf(result).futureValue should include("\"name\": \"Individual PAYE Test Support\"")
    }

    "provide raml documentation" in {
      val result = await(route(app, FakeRequest(GET, "/api/conf/1.0/application.raml"))).get
      status(result) shouldBe OK
      bodyOf(result).futureValue should startWith("#%RAML 1.0")
    }
  }
}
