/*
 * Copyright 2023 HM Revenue & Customs
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

package models

import models.sections.setup.WhoAreYou
import play.api.data.Form
import viewmodels.{AnswerSection, ResultMode}

case class ResultsDetails(officeHolderAnswer: Boolean,
                          isMakingDetermination: Boolean,
                          usingIntermediary: Boolean,
                          userType: Option[WhoAreYou],
                          personalServiceOption: Option[WeightedAnswerEnum.Value] = None,
                          controlOption: Option[WeightedAnswerEnum.Value] = None,
                          financialRiskOption: Option[WeightedAnswerEnum.Value] = None,
                          boOAOption: Option[WeightedAnswerEnum.Value] = None,
                          workerKnown: Boolean,
                          form: Form[Boolean]) {

  def isAgent: Boolean = userType.contains(WhoAreYou.Agency)
}

case class PDFResultDetails(resultMode: ResultMode,
                            additionalPdfDetails: Option[AdditionalPdfDetails] = None,
                            timestamp: Option[String] = None,
                            answerSections: Seq[AnswerSection] = Seq())
