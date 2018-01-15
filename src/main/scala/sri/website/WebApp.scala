package sri.website

import org.scalajs.dom
import sri.web.ReactDOM

import scala.scalajs.js
import scalajscss.CSSStyleSheetRegistry
import scala.scalajs.js.Dynamic.{global => g}
object WebApp {

  def main(args: Array[String]): Unit = {

    println(g.loadingElement)
    if (!js.isUndefined(g.loadingElement)) {
      g.document.body.removeChild(g.loadingElement)
      g.loadingElement = js.undefined
      dom.document.body.className.replace("pg-loading", "")
      dom.document.body.className = " pg-loaded"
      println(s"added successfully : ${dom.document.body.className}")
    }
    ReactDOM.render(
      components.root,
      dom.document.getElementById("app")
    )
  }

}
