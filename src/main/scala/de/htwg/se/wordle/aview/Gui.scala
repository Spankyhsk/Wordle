package de.htwg.se.wordle.aview

import de.htwg.se.wordle.controller.controll
import de.htwg.se.wordle.util.Observer

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.{ReadOnlyStringWrapper, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TableColumn, TableView, TextField}
import scalafx.scene.layout.{BorderPane, HBox, StackPane, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight, Text}

class Gui(controller: controll) extends JFXApp3 with Observer {
  case class GeneratedItem(name: String)

  override def update: Unit = {}

  override def start(): Unit = {
    stage = new PrimaryStage {
      scene = new Scene {
        fill = LightGray

        content = new StackPane {
          padding = Insets(20)

          children = new VBox {
            spacing = 10

            // Überschrift
            val titleText = new Text("Wordle")
            titleText.font = Font.font("Arial", FontWeight.Bold, 24)
            titleText.underline = true

            // Kasten mit Schwierigkeitsgrad
            val difficultyBox = new HBox {
              spacing = 10
              children = Seq(
                new Text("Schwierigkeitsgrad:"),
                new Text("Leicht")
              )
            }

            // Buttons
            val buttonsBox = new HBox {
              spacing = 10
              children = Seq(
                new Button("Leicht"),
                new Button("Mittel"),
                new Button("Schwer")
              )
            }

            // Versuch: Texteingabefeld
            val inputTextField = new TextField {
              promptText = "Gib hier deinen Versuch ein und drücke Enter"
              /*onAction = () => {
                val inputText = text()
                // Hier kannst du die Aktion ausführen, wenn Enter gedrückt wird
                println(s"Versuch: $inputText")
              }*/
            }

            // TableView für individuell generierte Elemente
            val generatedTable = new TableView[GeneratedItem] {
              columns ++= List(
                new TableColumn[GeneratedItem, String] {
                  //text = "Name"
                  //cellValueFactory = _.value.name
                }
              )
              items = ObservableBuffer(
                GeneratedItem("Element 1"),
                GeneratedItem("Element 2"),
                GeneratedItem("Element 3")
              )
            }

            // Kasten für Textgenerierung
            val textGenerationBox = new BorderPane {
              padding = Insets(10)
              style = "-fx-border-color: black;" // Rahmen um den Kasten
              center = new Text("Hier wird der generierte Text stehen.")
            }

            // Füge alle Komponenten zur VBox hinzu
            children = Seq(titleText, difficultyBox, buttonsBox, new Text("Versuch:"), inputTextField, generatedTable, textGenerationBox)
          }
        }
      }
    }
    stage.title = "Wordle"
  }
}