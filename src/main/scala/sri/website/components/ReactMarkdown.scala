package sri.website.components

import org.scalajs.dom.ext.Ajax
import sri.core.{
  Component,
  CreateElement,
  CreateElementJS,
  JSComponent,
  ReactElement,
  ReactNode
}
import sri.web.vdom.DOMProps
import sri.web.vdom.tagsPrefix_<._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|
import scala.util.{Failure, Success}

class MarkdownLoader
    extends Component[MarkdownLoader.Props, MarkdownLoader.State] {

  import MarkdownLoader._

  initialState(State())

  def render() = {
    if (state.content.isEmpty) <.spanC("Loading ...")
    else
      <.div(
        extraProps = js.Dynamic
          .literal(dangerouslySetInnerHTML =
            js.Dynamic.literal(__html = Marked(state.content)))
          .asInstanceOf[DOMProps])()
  }

  override def componentDidMount(): Unit = {
    Ajax
      .get(props.url)
      .onComplete {

        case Success(resp) =>
          setState((state: State) => state.copy(content = resp.responseText))

        case Failure(ex) => println(s"Failed with exception : ${ex.getCause}")
      }
  }
}

object MarkdownLoader {
  case class Props(url: String)
  case class State(loaded: Boolean = false, content: String = "")
  def apply(props: Props,
            key: String | Int = null,
            ref: js.Function1[MarkdownLoader, Unit] = null) =
    CreateElement[MarkdownLoader](props = props, key = key, ref = ref)
}
@js.native
@JSImport("marked", JSImport.Default)
object MarkedJS extends js.Object {

  def apply(text: String): String = js.native

  def setOptions(options: js.Object): Unit = js.native
}

object Marked {

  val m = MarkedJS

  m.setOptions(
    js.Dynamic.literal(gfm = true,
                       tables = true,
                       breaks = false,
                       pedantic = false,
                       sanitize = false,
                       smartLists = true,
                       smartypants = false))

  def apply(text: String) = {
    m(text)
  }

}
