package de.htwg.se.wordle.aview
import scala.swing._
import javax.swing.JTextPane


class JTextPaneWrapper(text: String) extends Component {
  override lazy val peer: JTextPane = new JTextPane {
    setContentType("text/html")
    setEditable(false)
    setBackground(new Color(0,0,0,0))
    setText(text)
    
  }

  
}
