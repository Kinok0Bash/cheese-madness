package entities

import engine.SpriteLoader
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Player(x: Int, y: Int) : GameObject(x, y, 64, 64), KeyListener {
    private var dx = 0
    private var dy = 0
    private val gravity = 1 // Сила гравитации
    private val jumpStrength = -16 // Сила прыжка
    private val maxFallSpeed = 10 // Максимальная скорость падения
    private var isOnGround = false // Флаг, чтобы знать, находится ли игрок на земле

    private val sprite = SpriteLoader.load("resources/sprites/player.png")
    private val pressedKeys = mutableSetOf<Int>() // Хранение текущих нажатых клавиш

    override fun draw(g: Graphics) {
        g.drawImage(sprite, x, y, width, height, null)
    }

    fun update(blocks: List<GameObject>) {
        // Применение горизонтального движения
        x += dx

        // Проверка горизонтальных коллизий
        for (block in blocks) {
            if (bounds.intersects(block.bounds)) {
                if (dx > 0) { // Двигаемся вправо
                    x = block.bounds.x - width
                } else if (dx < 0) { // Двигаемся влево
                    x = block.bounds.x + block.bounds.width
                }
                break
            }
        }

        // Применение гравитации
        if (!isOnGround) {
            dy += gravity
            if (dy > maxFallSpeed) dy = maxFallSpeed
        }

        // Применение вертикального движения
        y += dy

        // Проверка вертикальных коллизий
        isOnGround = false
        for (block in blocks) {
            if (bounds.intersects(block.bounds)) {
                if (dy > 0) { // Падаем вниз
                    y = block.bounds.y - height
                    dy = 0
                    isOnGround = true
                } else if (dy < 0) { // Прыгаем вверх
                    y = block.bounds.y + block.bounds.height
                    dy = 0
                }
                break
            }
        }
        updateMovement()
    }

    override fun keyPressed(e: KeyEvent) {
        pressedKeys.add(e.keyCode)
    }

    override fun keyReleased(e: KeyEvent) {
        pressedKeys.remove(e.keyCode)
    }

    override fun keyTyped(e: KeyEvent?) {}

    private fun updateMovement() {
        dx = 0
        if (KeyEvent.VK_LEFT in pressedKeys) dx = -5
        if (KeyEvent.VK_RIGHT in pressedKeys) dx = 5
        if (KeyEvent.VK_Z in pressedKeys && isOnGround) dy = jumpStrength // Прыжок
    }
}