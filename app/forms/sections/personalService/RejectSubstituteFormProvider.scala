/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package forms.sections.personalService

import config.FrontendAppConfig
import forms.mappings.Mappings
import javax.inject.Inject
import models.requests.DataRequest
import play.api.data.Form
import views.ViewUtils.tailorMsg

class RejectSubstituteFormProvider @Inject() extends Mappings {

  def apply()(implicit request: DataRequest[_], appConfig: FrontendAppConfig): Form[Boolean] =
    Form(
      "value" -> boolean(tailorMsg("rejectSubstitute.error.required"))
    )
}
