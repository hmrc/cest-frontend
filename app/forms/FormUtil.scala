package forms

import config.{FrontendAppConfig, SessionKeys}
import config.featureSwitch.{FeatureSwitching, OptimisedFlow}
import models.UserType
import models.UserType.{Agency, Worker}
import play.api.mvc.Request
import utils.SessionUtils._

object FormUtil extends FeatureSwitching {

  def tailorErrorMessage(key: String)(implicit request: Request[_], appConfig: FrontendAppConfig): String = {

    val userType = (isEnabled(OptimisedFlow), request.session.getModel[UserType](SessionKeys.userType)) match {
      case (true, Some(Agency)) | (true, None) => s"${Worker.toString}."
      case (false, _) => ""
      case (true, Some(user)) => s"${user.toString}."
    }
    userType + key
  }
}
