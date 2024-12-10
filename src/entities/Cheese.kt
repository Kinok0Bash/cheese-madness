package entities

import engine.SpriteLoader
import java.awt.Graphics

class Cheese(x: Int, y: Int) : GameObject(x, y, 48, 48) {
    private val sprite = SpriteLoader.load("cheese")

    override fun draw(g: Graphics) {
        g.drawImage(sprite, x, y, width, height, null)
    }
}