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

package uk.gov.hmrc.payedesstub

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}
import uk.gov.hmrc.payedesstub.models.ErrorResponse

package object controllers {

  implicit val doubleWrite = new Writes[Double] {
    def writes(value: Double): JsValue = JsNumber(
      BigDecimal(value).setScale(2, BigDecimal.RoundingMode.FLOOR)
    )
  }

  implicit val errorResponseWrites = new Writes[ErrorResponse] {
    def writes(e: ErrorResponse): JsValue = Json.obj("code" -> e.errorCode, "message" -> e.message)
  }

}
