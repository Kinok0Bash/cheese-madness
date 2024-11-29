package entities

import java.awt.Graphics
import java.awt.Rectangle

abstract class GameObject(var x: Int, var y: Int, val width: Int, val height: Int) {
    val bounds: Rectangle
        get() = Rectangle(x, y, width, height)

    abstract fun draw(g: Graphics)
}
