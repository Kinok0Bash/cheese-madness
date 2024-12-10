package entities

import engine.SpriteLoader
import java.awt.Graphics

class Door(x: Int, y: Int) :
    GameObject(x, y, 64, 64),
    ReDrawable
{
    private val sprite = SpriteLoader.load("door")
    private val openSprite = SpriteLoader.load("door_opened")
    private var isOpen = false

    override fun draw(g: Graphics) {
        val currentSprite = if (isOpen) openSprite else sprite
        g.drawImage(currentSprite, x, y, width, height, null)
    }

    override fun reDraw() {
        isOpen = true
    }
}