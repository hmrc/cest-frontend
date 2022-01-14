/*
 * Copyright 2022 HM Revenue & Customs
 *
 */

package controllers.sections.businessOnOwnAccount

import config.FrontendAppConfig
import connectors.DataCacheConnector
import controllers.BaseNavigationController
import controllers.actions._
import forms.sections.businessOnOwnAccount.MajorityOfWorkingTimeFormProvider
import javax.inject.Inject
import models.Mode
import navigation.BusinessOnOwnAccountNavigator
import pages.sections.businessOnOwnAccount.MajorityOfWorkingTimePage
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import services.CompareAnswerService
import views.html.sections.businessOnOwnAccount.MajorityOfWorkingTimeView

import scala.concurrent.Future

class MajorityOfWorkingTimeController @Inject()(override val dataCacheConnector: DataCacheConnector,
                                                override val navigator: BusinessOnOwnAccountNavigator,
                                                identify: IdentifierAction,
                                                getData: DataRetrievalAction,
                                                requireData: DataRequiredAction,
                                                requireUserType: UserTypeRequiredAction,
                                                formProvider: MajorityOfWorkingTimeFormProvider,
                                                override val controllerComponents: MessagesControllerComponents,
                                                override val compareAnswerService: CompareAnswerService,
                                                view: MajorityOfWorkingTimeView,
                                                implicit val appConfig: FrontendAppConfig)
  extends BaseNavigationController {

  def onPageLoad(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData andThen requireUserType) { implicit request =>
    Ok(view(fillForm(MajorityOfWorkingTimePage, formProvider()), mode))
  }

  def onSubmit(mode: Mode): Action[AnyContent] = (identify andThen getData andThen requireData).async { implicit request =>
    formProvider().bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(view(formWithErrors, mode))),
      value => redirect(mode, value, MajorityOfWorkingTimePage)
    )
  }
}
