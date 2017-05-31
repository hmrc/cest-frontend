/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.offpayroll.util

import org.scalatest.{FlatSpec, Matchers}
import uk.gov.hmrc.offpayroll.connectors.DimensionValue

class AnalyticsHelperSpec extends FlatSpec with Matchers {

  "AnalyticsHelper" should "build an AnalyticsRequest" in {
    val decision = "OUT"
    val version = "1.3.7-final"
    val route = "esi"

    val dimensionValues = AnalyticsHelper.buildDimensionValues(route, version, decision, setUpInterview)

    dimensionValues.size shouldBe 23
    dimensionValues(0).value shouldBe "version: "+version
    dimensionValues(1).value shouldBe "route: "+route
    dimensionValues(2).value shouldBe "decision: "+decision
    dimensionValues.mkString.contains("personalService.workerPayActualSubstitute: No") shouldBe true
    dimensionValues.mkString.contains("setup.endUserRole: setup.endUserRole.personDoingWork") shouldBe true

  }


  private def setUpInterview(): List[(String, List[String])] = {

    DimensionValue(1, "version:")
    var interview: List[(String, List[String])] = List()

    interview = interview :+ ("setup.endUserRole", List("setup.endUserRole.personDoingWork"))
    interview = interview :+ ("setup.hasContractStarted", List("Yes"))
    interview = interview :+ ("setup.provideServices", List("setup.provideServices.limitedCompany"))
    interview = interview :+ ("exit.officeHolder", List("No"))
    interview = interview :+ ("personalService.workerSentActualSubstitute", List("personalService.workerSentActualSubstitute.noSubstitutionHappened"))
    interview = interview :+ ("personalService.workerPayActualSubstitute", List("No"))
    interview = interview :+ ("personalService.possibleSubstituteRejection", List("personalService.possibleSubstituteRejection.wouldNotReject"))
    interview = interview :+ ("personalService.possibleSubstituteWorkerPay", List("Yes"))
    interview = interview :+ ("personalService.wouldWorkerPayHelper", List("Yes"))
    interview = interview :+ ("control.engagerMovingWorker", List("control.engagerMovingWorker.canMoveWorkerWithoutPermission"))
    interview = interview :+ ("control.workerDecidingHowWorkIsDone", List("control.workerDecidingHowWorkIsDone.workerFollowStrictEmployeeProcedures"))
    interview = interview :+ ("control.whenWorkHasToBeDone", List("control.whenWorkHasToBeDone.workerAgreeSchedule"))
    interview = interview :+ ("control.workerDecideWhere", List("control.workerDecideWhere.workerAgreeWithOthers"))
    interview = interview :+ ("financialRisk.haveToPayButCannotClaim", List("financialRisk.workerProvidedMaterialsfinancialRisk.expensesAreNotRelevantForRole"))
    interview = interview :+ ("financialRisk.workerMainIncome", List("financialRisk.workerMainIncome.incomeCommission"))
    interview = interview :+ ("financialRisk.paidForSubstandardWork", List("financialRisk.paidForSubstandardWork.cannotBeCorrected"))
    interview = interview :+ ("partParcel.workerReceivesBenefits", List("Yes"))
    interview = interview :+ ("partParcel.workerAsLineManager", List("Yes"))
    interview = interview :+ ("partParcel.contactWithEngagerCustomer", List("Yes"))
    interview = interview :+ ("partParcel.workerRepresentsEngagerBusiness", List("partParcel.workerRepresentsEngagerBusiness.workAsBusiness"))

    interview
  }
}
