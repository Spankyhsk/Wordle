package de.htwg.se.wordle.util

trait Observer:
  def update(e:Event): Unit

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(e:Event) = subscribers.foreach(o => o.update(e))

enum Event:
  case Move
  case NEW
  case UNDO
  case LOSE
  case WIN
