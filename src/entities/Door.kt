package entities

import engine.SpriteLoader
import java.awt.Graphics

class Door(x: Int, y: Int) : GameObject(x, y, 64, 64) {
    private val sprite = SpriteLoader.load("resources/sprites/door.png")

    override fun draw(g: Graphics) {
        g.drawImage(sprite, x, y, width, height, null)
    }
}