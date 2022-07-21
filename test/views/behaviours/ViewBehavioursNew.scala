/*
 * Copyright 2022 HM Revenue & Customs
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

package views.behaviours

import models.AdditionalPdfDetails
import play.twirl.api.HtmlFormat
import views.ViewSpecBaseNew

trait ViewBehavioursNew extends ViewSpecBaseNew {

  def normalPage(view: () => HtmlFormat.Appendable,
                 messageKeyPrefix: String,
                 hasSubheading: Boolean,
                 expectedGuidanceKeys: String*) = {

    "behave like a normal page" when {
      "rendered" must {
        "have the correct banner title" in {
          val doc = asDocument(view())
          val header = doc.select("a.hmrc-header__service-name")
          header.text mustBe messagesApi("site.service_name")
        }

        "display the correct browser title" in {
          val doc = asDocument(view())
          val expected = if(hasSubheading) {
            title(messages(s"$messageKeyPrefix.title"), Some(messages(s"$messageKeyPrefix.subheading")))
          } else {
            title(messages(s"$messageKeyPrefix.title"))
          }
          assertEqualsValue(doc, "title", expected)
        }

        "display the correct page title" in {
          val doc = asDocument(view())
          assertPageTitleEqualsMessage(doc, s"$messageKeyPrefix.heading")
        }

        "display the correct guidance" in {
          val doc = asDocument(view())
          for (key <- expectedGuidanceKeys) assertContainsText(doc, messages(s"$messageKeyPrefix.$key"))
        }

        "not include a timestamp" in {
          val doc = asDocument(view())
          doc.toString contains "Date of result:" mustBe false
        }
      }
    }
  }

  def printPage(view: () => HtmlFormat.Appendable,
                pdfDetails: AdditionalPdfDetails,
                timestamp: String,
                messageKeyPrefix: String,
                expectedGuidanceKeys: String*) = {
    "behave like a normal page" when {
      "rendered" must {

        "display the correct browser title" in {
          val doc = asDocument(view())
          val expected = title(messages(s"$messageKeyPrefix.title"))
          assertEqualsValue(doc, "title", expected)
        }

        "display the correct page title" in {
          val doc = asDocument(view())
          assertPageTitleEqualsMessage(doc, s"$messageKeyPrefix.heading")
        }

        "display the correct guidance" in {
          val doc = asDocument(view())
          for (key <- expectedGuidanceKeys) assertContainsText(doc, messages(s"$messageKeyPrefix.$key"))
        }

        "display the pdf content" in {
          val doc = asDocument(view())
          assertContainsText(doc, messages("customisePDF.customisedBy"))
          assertContainsText(doc, messages("customisePDF.client"))
          assertContainsText(doc, messages("customisePDF.job"))
          assertContainsText(doc, messages("customisePDF.reference"))

          if(pdfDetails.client.isDefined) assertContainsText(doc, pdfDetails.client.get)
          if(pdfDetails.completedBy.isDefined) assertContainsText(doc, pdfDetails.completedBy.get)
          if(pdfDetails.job.isDefined) assertContainsText(doc, pdfDetails.job.get)
          if(pdfDetails.reference.isDefined) assertContainsText(doc, pdfDetails.reference.get)

          assertContainsText(doc, timestamp)
          assertContainsText(doc, "Date of result: ")
          assertContainsText(doc, "(UTC)")

          for (key <- expectedGuidanceKeys) assertContainsText(doc, messages(s"$messageKeyPrefix.$key"))
        }
      }
    }
  }

  def pageWithBackLink(view: () => HtmlFormat.Appendable, url: String = "javascript:history.back()") = {

    "behave like a page with a back link" must {
      "have a back link" in {
        val doc = asDocument(view())
        assertRenderedByCssSelector(doc, "a.govuk-back-link")
        doc.select("a.govuk-back-link").attr("href") mustBe url
      }
    }
  }
}