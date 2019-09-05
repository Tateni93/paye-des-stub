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

package uk.gov.hmrc.payedesstub.models

trait IndividualResponse

case class IndividualBenefitsResponse(employments: List[IndividualBenefitsEmployment]) extends IndividualResponse

case class IndividualEmploymentResponse(employments: List[IndividualEmploymentEmployment]) extends IndividualResponse

case class IndividualIncomeResponse(pensionsAnnuitiesAndOtherStateBenefits: ExtendedStateBenefits,
                                    employments: List[IndividualIncomeEmployment]) extends IndividualResponse

case class IndividualTaxResponse(pensionsAnnuitiesAndOtherStateBenefits: StateBenefits,
                                 refunds: Refund,
                                 employments: List[IndividualTaxEmployment]) extends IndividualResponse

case class IndividualBenefitsEmployment(employerPayeReference: String,
                                        companyCarsAndVansBenefit: Double,
                                        fuelForCompanyCarsAndVansBenefit: Double,
                                        privateMedicalDentalInsurance: Double,
                                        vouchersCreditCardsExcessMileageAllowance: Double,
                                        goodsEtcProvidedByEmployer: Double,
                                        accommodationProvidedByEmployer: Double,
                                        otherBenefits: Double,
                                        expensesPaymentsReceived: Double)

case class IndividualTaxEmployment(employerPayeReference: String,
                                   taxTakenOffPay: Double)

case class IndividualIncomeEmployment(employerPayeReference: String,
                                      payFromEmployment: Double)

case class IndividualEmploymentEmployment(employerPayeReference: String,
                                          employerName: String)

case class StateBenefits(otherPensionsAndRetirementAnnuities: Double,
                         incapacityBenefit: Double)

case class ExtendedStateBenefits(otherPensionsAndRetirementAnnuities: Double,
                                 incapacityBenefit: Double,
                                 jobseekersAllowance: Double)

case class Refund(taxRefundedOrSetOff: Double)