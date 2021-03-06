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

package uk.gov.hmrc.payedesstub.controllers

import play.api.http.HeaderNames.ACCEPT
import play.api.mvc.{ActionBuilder, ActionFilter, Request, Results}
import uk.gov.hmrc.payedesstub.models.ErrorAcceptHeaderInvalid

import scala.concurrent.Future
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

trait HeaderValidator extends Results with ErrorConversion {

  private val validateContentType: String => Boolean = ct => ct == "json"

  private val matchHeader: String => Option[Match] = new Regex("""^application/vnd\.hmrc\.(.*?)\+(.*)$""", "version", "contenttype") findFirstMatchIn _

  def acceptHeaderValidationRules(versions: String*): Option[String] => Boolean =
    _ flatMap (a => matchHeader(a) map (res => validateContentType(res.group("contenttype")) && versions.contains(res.group("version")))) getOrElse false

  private def validateAction(rules: Option[String] => Boolean) = new ActionBuilder[Request] with ActionFilter[Request] {

    def filter[T](input: Request[T]) = Future.successful {
      implicit val r = input
      if (!rules(input.headers.get(ACCEPT))) {
        Some(ErrorAcceptHeaderInvalid)
      } else {
        None
      }
    }
  }

  def validateAcceptHeader(versions: String*) : ActionBuilder[Request] = {
    validateAction(acceptHeaderValidationRules(versions: _*))
  }
}
