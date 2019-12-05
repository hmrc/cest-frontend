package controllers.setup

import helpers.IntegrationSpecBase
import models.NormalMode

class ContractStartedControllerISpec extends IntegrationSpecBase {

  s"Post or Get to /work-started" should {

    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/work-started")

      whenReady(res) { result =>
         result.status shouldBe OK
        titleOf(result) should include ("Have you already started working for this client?")
      }
    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/work-started")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }
    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/work-started", defaultValue)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        titleOf(result) should include ("Have you already started working for this client?")

      }
    }

    "Return a 303 on Successful post and redirect to the Office Holder page" in {

      lazy val res = postSessionRequest("/work-started", selectedNo)

      whenReady(res) { result =>
        result.status shouldBe SEE_OTHER
        redirectLocation(result) shouldBe Some(controllers.sections.exit.routes.OfficeHolderController.onPageLoad(NormalMode).url)
      }
    }
  }

  s"Post or Get to /work-started/change" should {

    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/work-started/change")

      whenReady(res) { result =>
        result.status shouldBe OK
        titleOf(result) should include ("Have you already started working for this client?")
      }
    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/work-started/change")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }
    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/work-started/change", defaultValue)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        titleOf(result) should include ("Have you already started working for this client?")

      }
    }

    "Return a 303 on Successful post and redirect to the Office Holder page" in {

      lazy val res = postSessionRequest("/work-started/change", selectedNo)

      whenReady(res) { result =>
        result.status shouldBe SEE_OTHER
        redirectLocation(result) shouldBe Some(controllers.sections.exit.routes.OfficeHolderController.onPageLoad(NormalMode).url)
      }
    }
  }
}
