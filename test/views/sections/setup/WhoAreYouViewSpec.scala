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

package views.sections.setup

import assets.messages.WhoAreYouMessages
import config.featureSwitch.FeatureSwitching
import forms.sections.setup.WhoAreYouFormProvider
import models.NormalMode
import play.api.mvc.{Call, Request}
import views.behaviours.ViewBehavioursNew
import views.html.sections.setup.WhoAreYouView

class WhoAreYouViewSpec extends ViewBehavioursNew with FeatureSwitching {

  object Selectors extends BaseCSSSelectors

  val messageKeyPrefix = "whoAreYou"

  val form = new WhoAreYouFormProvider()()(fakeDataRequest)

  val view = injector.instanceOf[WhoAreYouView]

  val postAction = Call("POST", "/foo")

  def createViewWithAgency = () => view(postAction, form, NormalMode, showAgency = true)(fakeRequest, messages, frontendAppConfig)
  def createView = (req: Request[_], showAgency: Boolean) => view(postAction, form, NormalMode, showAgency)(req, messages, frontendAppConfig)

  "WhatDoYouWantToFindOut view" must {

    behave like normalPage(createViewWithAgency, messageKeyPrefix, hasSubheading = true)

    behave like pageWithBackLink(createViewWithAgency)

    "when showAgency is true" should {

      "when no user type" must {

        lazy val document = asDocument(createView(fakeRequest, true))

        "have the correct title" in {
          document.title mustBe title(WhoAreYouMessages.title, Some(WhoAreYouMessages.subHeading))
        }

        "have the correct heading" in {
          document.select(Selectors.heading).text must include(WhoAreYouMessages.heading)
        }

        "have the correct subheading" in {
          document.select(Selectors.subheading).text must include(WhoAreYouMessages.subHeading)
        }

        "when showAgency is true" should {

          "have the correct radio option messages" in {
            document.select(Selectors.multichoice(1)).text mustBe WhoAreYouMessages.worker
            document.select(Selectors.multichoice(2)).text mustBe WhoAreYouMessages.hirer
            document.select(Selectors.multichoice(3)).text mustBe WhoAreYouMessages.agency
          }
        }
      }

      "when user type is given" must {

        lazy val document = asDocument(createView(workerFakeRequest, true))

        "have the correct title" in {
          document.title mustBe title(WhoAreYouMessages.title, Some(WhoAreYouMessages.subHeading))
        }

        "have the correct heading" in {
          document.select(Selectors.heading).text must include(WhoAreYouMessages.heading)
        }

        "have the correct subheading" in {
          document.select(Selectors.subheading).text must include(WhoAreYouMessages.subHeading)
        }

        "when showAgency is true" should {

          "have the correct radio option messages" in {
            document.select(Selectors.multichoice(1)).text mustBe WhoAreYouMessages.worker
            document.select(Selectors.multichoice(2)).text mustBe WhoAreYouMessages.hirer
            document.select(Selectors.multichoice(3)).text mustBe WhoAreYouMessages.agency
          }
        }
      }
    }

    "when showAgency is false" should {

      "when no user type" should {

        lazy val document = asDocument(createView(fakeRequest, false))

        "have the correct radio option messages" in {
          document.select(Selectors.multichoice(1)).text mustBe WhoAreYouMessages.worker
          document.select(Selectors.multichoice(2)).text mustBe WhoAreYouMessages.hirer
          document.select(Selectors.multichoice(3)).isEmpty mustBe true
        }
      }

      "user type is worker" should {

        lazy val document = asDocument(createView(workerFakeRequest, false))

        "have the correct radio option messages" in {
          document.select(Selectors.multichoice(1)).text mustBe WhoAreYouMessages.worker
          document.select(Selectors.multichoice(2)).text mustBe WhoAreYouMessages.hirer
          document.select(Selectors.multichoice(3)).isEmpty mustBe true
        }
      }
    }
  }
}
