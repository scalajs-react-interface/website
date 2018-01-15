package sri.website.components

import org.scalajs.dom.ext.Ajax
import sri.core.ReactNode
import sri.web.router.{RouterScreenComponentNoPSLS, RouterScreenComponentS}
import sri.web.vdom.tagsPrefix_<._
import sri.universal._

import scala.concurrent.Future
import scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSON
import scalajscss.{CSSStyleSheet, CSSStyleSheetRegistry}

class TeamScreen extends RouterScreenComponentS[TeamScreen.State] {

  import TeamScreen._
  initialState(State())

  CSSStyleSheetRegistry.addToDocument(styles)

  def render() = {
    <.div(className = styles.container)(
      <.div(className = styles.usersBlock)(
        <.h3C("Core Team:"),
        if (state.coreTeam.isEmpty) <.divC("Loading")
        else state.coreTeam.map(t => getUserProfile(t)).asInstanceOf[ReactNode]
      ),
      <.div(className = styles.usersBlock)(
        <.h3C("Contributors:"),
        if (state.contributors.isEmpty) <.divC("Loading..")
        else
          state.contributors.map(t => getUserProfile(t)).asInstanceOf[ReactNode]
      )
    )
  }

  override def componentDidMount(): Unit = {
    Ajax
      .get("https://api.github.com/orgs/scalajs-react-interface/members")
      .foreach(xhr => {
        setState(
          (state: State) =>
            state.copy(coreTeam =
              JSON.parse(xhr.responseText).asInstanceOf[js.Array[GithubLogin]]))
      })

    Ajax
      .get("https://api.github.com/orgs/scalajs-react-interface/repos")
      .foreach(xhr => {

        val repos =
          JSON.parse(xhr.responseText).asInstanceOf[js.Array[GithubRepo]].toList

        Future
          .sequence(
            repos.map(
              r =>
                Ajax
                  .get(
                    s"https://api.github.com/repos/${r.full_name}/contributors")
                  .map(xhr =>
                    JSON
                      .parse(xhr.responseText)
                      .asInstanceOf[js.Array[GithubLogin]]
                      .toList)))
          .foreach(a => {
            val finalList: js.Array[GithubLogin] = js.Array()
            a.flatMap(f => f)
              .foreach(f => {
                if (!finalList.map(_.login).contains(f.login)) {
                  finalList.push(f)
                }
              })
            setState((state: State) => state.copy(contributors = finalList))
          })
      })
  }

  def getUserProfile(in: GithubLogin) = {
    <.a(href = in.html_url,
        target = "_blank",
        className = styles.userBlock,
        key = in.login)(
      <.img(src = in.avatar_url, className = styles.userImage),
      <.span(className = styles.userName)(in.login)
    )
  }
}

object TeamScreen {

  case class State(coreTeam: js.Array[GithubLogin] = js.Array(),
                   contributors: js.Array[GithubLogin] = js.Array())

  object styles extends CSSStyleSheet {

    import dsl._
    import scalajscss.units._

    val container =
      style(
        marginTop := 70.px,
      )

    val usersBlock = style(margin := 40.px)

    val userBlock =
      style(textDecoration.none,
            textAlign := "center",
            display.inlineBlock,
            width := 100.px,
            margin := 20.px,
            color.black)
    val userImage = style(margin := 10.px,
                          width := 100.px,
                          height := 100.px,
                          borderRadius := "50%")
    val userName = style(fontSize := 18.px, fontWeight := "500")
  }

}
@js.native
trait GithubLogin extends js.Object {
  val login: String = js.native
  val avatar_url: String = js.native
  val html_url: String = js.native
}

@js.native
trait GithubRepo extends js.Object {
  val full_name: String = js.native
}
