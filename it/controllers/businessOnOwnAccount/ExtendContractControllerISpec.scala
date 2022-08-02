package controllers.businessOnOwnAccount

import helpers.{CreateRequestHelper, IntegrationSpecBase, TestData}
import play.api.http.Status

class ExtendContractControllerISpec extends IntegrationSpecBase with CreateRequestHelper with Status with TestData{

  s"Post or Get to /extend-contract" should {

    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/extend-contract")

      whenReady(res) { result =>
        result.status shouldBe OK
        titleOf(result) should include ("Does the current contract allow for it to be extended?")
      }
    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/extend-contract")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }
    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/extend-contract", defaultValue)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        titleOf(result) should include ("Does the current contract allow for it to be extended?")

      }
    }

    "Return a 303 on Successful post and move onto the Majority of Working Time page" in {

      lazy val res = postSessionRequest("/extend-contract", selectedNo)

      whenReady(res) { result =>
        result.status shouldBe SEE_OTHER
        redirectLocation(result).get should include("/majority-of-working-time")

      }
    }
  }

  s"Post or Get to /extend-contract/change" should {

    "Return a 200 on successful get and should be on relevant page" in {

      lazy val res = getSessionRequest("/extend-contract/change")

      whenReady(res) { result =>
        result.status shouldBe OK
        titleOf(result) should include ("Does the current contract allow for it to be extended?")
      }
    }

    "Return a 404 on a post to unused method" in {

      lazy val res = optionsRequest("/extend-contract/change")

      whenReady(res) { result =>
        result.status shouldBe NOT_FOUND
      }
    }

    "Return a 400 on unsuccessful post and stay on the same page" in {

      lazy val res = postSessionRequest("/extend-contract/change", defaultValue)

      whenReady(res) { result =>
        result.status shouldBe BAD_REQUEST
        titleOf(result) should include ("Does the current contract allow for it to be extended?")

      }
    }

    "Return a 303 on Successful post and move onto the Majority of Working Time page" in {

      lazy val res = postSessionRequest("/extend-contract/change", selectedNo)

      whenReady(res) { result =>
        result.status shouldBe SEE_OTHER
        redirectLocation(result).get should include("/majority-of-working-time")

      }
    }
  }
}
