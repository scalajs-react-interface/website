package sri.website.components

import sri.web.router.{RouterAwareComponentNoPS, RouterScreenClass, WithRouter}
import sri.web.vdom.tagsPrefix_<._

import scala.reflect.ClassTag
import scala.scalajs.js.ConstructorTag
import scalajscss.{CSSStyleSheet, CSSStyleSheetRegistry}

class TopNav extends RouterAwareComponentNoPS {

  import TopNav._

  override def componentWillMount(): Unit = {
    CSSStyleSheetRegistry.addToDocument(styles)
  }

  def render() = {
    <.div(className = styles.topNav)(
      <.div(className = styles.leftMenu)(
        getStaticMenuItem[HomeScreen]("శ్రీ"),
        <.a(
          className = styles.docs,
          target = "blank",
          href = "https://github.com/scalajs-react-interface/sri#sri")("Docs"),
        getStaticMenuItem[TeamScreen]("Team")
      ),
      <.a(className = styles.github,
          target = "blank",
          href = "https://github.com/scalajs-react-interface")("Github")
    )
  }

  def getStaticMenuItem[C <: RouterScreenClass { type Params = Null }: ConstructorTag](
      text: String,
      module: Boolean = false)(implicit ctag: ClassTag[C]) = {
    val currentKey = navigation.currentRoute.screenKey.toString
    val pageKey = sri.web.router.getRouterScreenKey[C].toString

    val isSelected =
      if (!module) pageKey == currentKey
      else {
        (pageKey == currentKey) || (pageKey
          .split("\\.")
          .init
          .mkString(".") == currentKey.split("\\.").init.mkString("."))
      }
    val class1 = if (isSelected) {
      if (text == "శ్రీ") {
        styles.logoSelected
      } else styles.buttonSelected
    } else {
      if (text == "శ్రీ") {
        styles.logo
      } else styles.button
    }
    <.div(className = class1, onClick = (e: ReactEventH) => {
      navigation.navigate[C]()
    })(text)

  }
}

object TopNav {

  object styles extends CSSStyleSheet {
    import dsl._

    import scalajscss.units._

    val topNav =
      style(display.flex,
            zIndex := 100,
            width := 100.%%,
            paddingLeft := 20.px,
            position.fixed,
            alignItems.center,
            height := 64.px,
            backgroundColor := "#1976D2")

    val leftMenu = style(display.flex, flex := "1")

    val button = style(cursor.pointer,
                       color.white,
                       display.flex,
                       alignItems.center,
                       fontSize := 18.px,
                       paddingLeft := 13.px,
                       paddingRight := 13.px,
                       height := 64.px,
                       marginLeft := 20.px)

    val buttonSelected = styleExtend(button)(backgroundColor := "#dc5c1d")

    val logo = style(
      cursor.pointer,
      color.white,
      display.flex,
      alignItems.center,
      fontSize := 28.px,
      fontWeight.bold,
      paddingLeft := 30.px,
      paddingRight := 30.px,
      height := 64.px,
      marginRight := 20.px,
      marginLeft := 20.px
    )

    val logoSelected = styleExtend(logo)(backgroundColor := "#dc5c1d")

    val docs = styleExtend(button)(
      textDecoration.none
    )

    val github = style(cursor.pointer,
                       color.white,
                       display.flex,
                       alignItems.center,
                       textDecoration.none,
                       marginRight := 80.px,
                       fontSize := 22.px)

  }

  def apply() = WithRouter[TopNav]()
}
