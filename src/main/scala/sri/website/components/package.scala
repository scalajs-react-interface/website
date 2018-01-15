package sri.website

import sri.core.ReactElement
import sri.web.router
import sri.web.router.{
  BrowserHistoryOptions,
  History,
  HistoryFactory,
  RouteNotFound,
  Router,
  RouterConfig,
  RouterCtrl
}

package object components {

  object AppRoutes extends RouterConfig {
    override val history: History =
      HistoryFactory.browserHistory(BrowserHistoryOptions(basename = "sri"))

    registerScreen[HomeScreen]("/")

    registerScreen[TeamScreen]("team")

    override val notFound: RouteNotFound = RouteNotFound(
      router.getRouterScreenKey[HomeScreen])

    override def renderScene(navigation: RouterCtrl): ReactElement = {
      AppFrame(super.renderScene(navigation))
    }
  }

  val root = Router(AppRoutes)

}
