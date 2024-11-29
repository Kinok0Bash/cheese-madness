package entities

import engine.SpriteLoader
import java.awt.Graphics

class Door(x: Int, y: Int) : GameObject(x, y, 64, 64) {
    private val sprite = SpriteLoader.load("resources/sprites/door.png")
    private val openSprite = SpriteLoader.load("resources/sprites/door_opened.png")
    private var isOpen = false

    override fun draw(g: Graphics) {
        val currentSprite = if (isOpen) openSprite else sprite
        g.drawImage(currentSprite, x, y, width, height, null)
    }

    fun open() {
        isOpen = true
    }
}