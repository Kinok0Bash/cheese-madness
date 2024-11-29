package screens

import GameFrame
import engine.LevelManager
import entities.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.swing.*

class GamePanel(private val frame: GameFrame, private val levelIndex: Int) : JPanel(), Runnable, KeyListener {
    private val player = Player(64, 64)
    private var gameOver = false
    private var keysCollected = 0
    private var doorOpen = false

    private val blocks = mutableListOf<GameObject>()
    private val keys = mutableListOf<Key>()
    private val door = Door(18 * 64, 10 * 64)
    private val gameBackground = mutableListOf<Background>()

    init {
        layout = null
        background = Color.DARK_GRAY
        loadLevel(LevelManager.getLevelPath(levelIndex))
        isFocusable = true
        requestFocusInWindow()
        addKeyListener(player)
        addKeyListener(this)
        Thread(this).start()
    }

    override fun run() {
        while (!gameOver) {
            updateGame()
            repaint()
            Thread.sleep(16)
        }
        frame.contentPane = GameOverPanel(frame)
        frame.validate()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        // Заливаем пробелы серым цветом
        g.color = Color.GRAY
        for (x in 0..<width step 64) {
            for (y in 0..<height step 64) {
                g.fillRect(x, y, 64, 64)
            }
        }

        gameBackground.forEach { it.draw(g) }
        blocks.forEach { it.draw(g) }
        keys.forEach { it.draw(g) }
        door.draw(g)
        player.draw(g)
    }

    private fun updateGame() {
        player.update(blocks)

        // Проверяем сбор ключей
        keys.removeIf {
            if (player.bounds.intersects(it.bounds)) {
                keysCollected++
                true
            } else {
                false
            }
        }

        // Проверяем открытие двери
        doorOpen = keysCollected >= 3
        if (doorOpen && player.bounds.intersects(door.bounds)) {
            keysCollected = 0
            LevelManager.unlockNextLevel(levelIndex)
            println(LevelManager.unlockedLevels)

            if (levelIndex + 1 < LevelManager.MAX_LEVELS) {
                val nextPanel = GamePanel(frame, levelIndex + 1)
                frame.contentPane = nextPanel
                nextPanel.requestFocusInWindow()
            } else {
                val menuPanel = MenuPanel(frame)
                frame.contentPane = menuPanel
                menuPanel.requestFocusInWindow()
            }

            frame.validate()
        }
    }

    private fun loadLevel(levelPath: String) {
        blocks.clear()
        keys.clear()
        gameBackground.clear()
        keysCollected = 0
        var playerPositionSet = false

        val levelLines = File(levelPath).readLines()
        for ((y, line) in levelLines.withIndex()) {
            for ((x, char) in line.withIndex()) {
                when (char) {
                    '#' -> blocks.add(Block(x * 64, y * 64))
                    'K' -> keys.add(Key(x * 64 + 8, y * 64 + 8))
                    'D' -> {door.x = x * 64; door.y = y * 64 }
                    'P' -> { player.x = x * 64; player.y = y * 64; playerPositionSet = true }
                }
                if (char != '#') gameBackground.add(Background(x * 64, y * 64))
            }
        }

        if (!playerPositionSet) {
            throw IllegalArgumentException("Ошибка загрузки уровня: начальная позиция игрока ('P') не найдена!")
        }
    }

    override fun keyTyped(e: KeyEvent) {}

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ESCAPE) {
            frame.contentPane = MenuPanel(frame)
            frame.validate()
        }
    }

    override fun keyReleased(e: KeyEvent?) {}
}
