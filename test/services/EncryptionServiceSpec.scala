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

package services

import base.GuiceAppSpecBase
import models.AdditionalPdfDetails
import org.scalamock.scalatest.MockFactory

class EncryptionServiceSpec extends GuiceAppSpecBase with MockFactory {

  val service = new EncryptionService(app.configuration)

  val strToEncrypt = "EncryptThisPleaseKindSir-vice"
  val previouEncryptedStr = "Ve6PtNjJmYtYegZJDXDeXDXxYrMijtUVoeZhIFMbe0s="
  val encryptedStr = "tHyM5uD8oYHH5vepkywHprdqAe9XLtsCh17KZXl492M="

  "The encryption service" must {
    "encrypt values" in {
      service.encrypt(strToEncrypt) mustBe encryptedStr
    }

    "decrypt values" in {
      service.decrypt(encryptedStr) mustBe strToEncrypt
    }

    "decrypt values using previous key" in {
      service.decrypt(previouEncryptedStr) mustBe strToEncrypt
    }

    "encrypt the details model" in {
      service.encryptDetails(AdditionalPdfDetails(
        None, Some("Rick Owens"), Some("Raf Simons"), Some("Hedi Slimane"), Some("Rei Kawakubo")
      )) mustBe AdditionalPdfDetails(
        None, Some("/Aa9xvxegb0RN1Qmvff0HQ=="), Some("9hTCVAN4EqbkBOdumrtYAw=="), Some("JsBicCYZacLIDrY6xN21Bw=="), Some("HavGZXrrMH0bxg3zFr1PHw==")
      )
    }

    "decrypt the details model" in {
      service.decryptDetails(AdditionalPdfDetails(
        None, Some("/Aa9xvxegb0RN1Qmvff0HQ=="), Some("9hTCVAN4EqbkBOdumrtYAw=="), Some("JsBicCYZacLIDrY6xN21Bw=="), Some("HavGZXrrMH0bxg3zFr1PHw==")
      )) mustBe AdditionalPdfDetails(
        None, Some("Rick Owens"), Some("Raf Simons"), Some("Hedi Slimane"), Some("Rei Kawakubo")
      )
    }

    "decrypt the details model using previous keys" in {
      service.decryptDetails(AdditionalPdfDetails(
                None, Some("tjEQZVSmigsNSJcsI/Xy9A=="), Some("ZXJbXujAt/Lh+f0vayILrw=="), Some("rCISmCPHS+D5KPhUb3nPCQ=="), Some("yNeVHPtiCjVRn6CWIolgcg==")
      )) mustBe AdditionalPdfDetails(
        None, Some("Rick Owens"), Some("Raf Simons"), Some("Hedi Slimane"), Some("Rei Kawakubo")
      )
    }
  }
}
