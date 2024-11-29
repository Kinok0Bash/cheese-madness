package entities

import engine.SpriteLoader
import java.awt.Graphics

class Key(x: Int, y: Int) : GameObject(x, y, 48, 48) {
    private val sprite = SpriteLoader.load("resources/sprites/cheese.png")

    override fun draw(g: Graphics) {
        g.drawImage(sprite, x, y, width, height, null)
    }
}