package sri.website.components

import sri.web.router.RouterScreenComponentNoPSLS
import sri.web.vdom.tagsPrefix_<._

import scala.scalajs.js.{UndefOr => U}
import scalajscss.{CSSStyleSheet, CSSStyleSheetRegistry}

class HomeScreen extends RouterScreenComponentNoPSLS {
  import HomeScreen._

  CSSStyleSheetRegistry.addToDocument(styles)

  def render() = {
    <.div(className = styles.container)(
      <.h3C(
        "Build Truly Native Cross Platform(Web,iOS,Android,..) apps using scala.js"),
      <.div(className = styles.buttons)(
        <.a(className = styles.button,
            href = "https://github.com/scalajs-react-interface/discuss/issues",
            target = "blank")("Discuss"),
        <.a(className = styles.button,
            href = "https://gitter.im/scalajs-react-interface/sri",
            target = "blank")("Chat"),
        <.a(className = styles.button,
            href = "https://www.patreon.com/chandu0101",
            target = "blank")("Sponsor")
      )
    )
  }

}

object HomeScreen {

  object styles extends CSSStyleSheet {

    import dsl._
    import scalajscss.units._

    val container =
      style(display.flex,
            alignItems.center,
            flexDirection.column,
            justifyContent.center,
            marginTop := 120.px)

    val buttons = style(marginTop := 20.px)

    val button = style(
      padding := 10.px,
      boxShadow := "0 2px 4px grey",
      background := "#F75B0C",
      color.white,
      marginLeft := 20.px,
      marginRight := 10.px,
      textDecoration.none,
      fontWeight.bold,
      borderRadius := 10.px
    )

  }

}
