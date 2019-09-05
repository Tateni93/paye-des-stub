/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.payedesstub.services

import javax.inject.{Inject, Singleton}

import uk.gov.hmrc.payedesstub.models.{IndividualBenefits, IndividualBenefitsResponse}
import uk.gov.hmrc.payedesstub.repositories.IndividualBenefitsRepository

import scala.concurrent.Future

@Singleton
class IndividualBenefitsSummaryService @Inject()(val repository: IndividualBenefitsRepository) {

  def create(utr: String, taxYear: String, individualBenefitsResponse: IndividualBenefitsResponse): Future[IndividualBenefits] = {
    repository.store(IndividualBenefits(utr, taxYear, individualBenefitsResponse))
  }

  def fetch(utr: String, taxYear: String): Future[Option[IndividualBenefits]] = {
    repository.fetch(utr, taxYear)
  }
}