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

package viewmodels

import config.FrontendAppConfig
import models.Section.SectionEnum
import play.api.i18n.Messages
import play.api.mvc.Request
import play.twirl.api.{Html, HtmlFormat}

case class Accordion(sections: Seq[AccordionSection]) {

  def html(implicit messages: Messages, request: Request[_], appConfig: FrontendAppConfig) =
    views.html.components.accordion.accordionNew(sections)
}

case class AccordionSection(section: SectionEnum,
                            headingKey: String,
                            body: Html,
                            expanded: Boolean = false) {

  def html(index: Int)(implicit messages: Messages, request: Request[_], appConfig: FrontendAppConfig): HtmlFormat.Appendable =
    views.html.components.accordion.accordion_sectionNew(section.toString, headingKey, body, expanded, index)
}

object Accordion {
  def apply(sections: Seq[AnswerSection], sectionToExpand: Option[SectionEnum])
           (implicit messages: Messages, request: Request[_], appConfig: FrontendAppConfig): Accordion = new Accordion(
    sections = sections.map(section => AccordionSection(
      section.section,
      section.headingKey,
      CheckYourAnswersSection(section.rows.map(_._1)).html,
      sectionToExpand.contains(section.section)
    ))
  )
}
