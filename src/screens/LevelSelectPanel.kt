package screens

import GameFrame
import engine.LevelManager
import engine.SpriteLoader
import java.awt.*
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class LevelSelectPanel(private val frame: GameFrame) : JPanel() {
    private val backgroundBlocks = mutableListOf<Rectangle>() // Для фона
    private val blockImage = SpriteLoader.load("resources/sprites/background.png") // Изображение блока

    init {
        // Настройка панели
        layout = null // Ручное управление позиционированием
        background = Color.DARK_GRAY
        createBackgroundBlocks()

        // Заголовок
        val titleLabel = JLabel("ВЫБОР УРОВНЯ", JLabel.CENTER)
        titleLabel.font = Font("Arial", Font.BOLD, 36)
        titleLabel.foreground = Color.YELLOW
        titleLabel.setBounds(0, 50, 1280, 50)

        // Кнопки выбора уровней
        val buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(0, 5, 20, 20)
        buttonPanel.isOpaque = false // Панель прозрачная
        buttonPanel.setBounds(240, 150, 800, 400) // Центрируем панель

        LevelManager.levels.forEachIndexed { index, _ ->
            val isUnlock = index < LevelManager.unlockedLevels
            val button = createLevelButton("Уровень ${index + 1}", isUnlock)
            if (isUnlock) button.addActionListener {
                val gamePanel = GamePanel(frame, index)
                frame.contentPane = gamePanel
                gamePanel.requestFocusInWindow()
                frame.validate()
            }
            buttonPanel.add(button)
        }

        // Кнопка "Назад"
        val backButton = createMenuButton("Назад")
        backButton.setBounds(540, 600, 200, 50)
        backButton.addActionListener {
            frame.contentPane = MenuPanel(frame)
            frame.validate()
        }

        // Добавляем элементы на панель
        add(titleLabel)
        add(buttonPanel)
        add(backButton)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

        // Рисуем фон из блоков
        backgroundBlocks.forEach { block ->
            g2d.drawImage(blockImage, block.x, block.y, block.width, block.height, null)
        }
    }

    private fun createBackgroundBlocks() {
        // Генерируем сетку блоков 64x64
        val rows = 15
        val cols = 20
        for (x in 0 until cols) {
            for (y in 0 until rows) {
                backgroundBlocks.add(Rectangle(x * 64, y * 64, 64, 64))
            }
        }
    }

    private fun createLevelButton(text: String, isUnlock: Boolean): JButton {
        return JButton(text).apply {
            font = Font("Arial", Font.PLAIN, 18)
            isFocusPainted = false
            isContentAreaFilled = false
            if (isUnlock) {
                border = BorderFactory.createLineBorder(Color.YELLOW, 2)
                foreground = Color.YELLOW
            } else {
                border = BorderFactory.createLineBorder(Color.BLACK, 2)
                foreground = Color.BLACK
            }
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

            if (isUnlock) addMouseListener(object : java.awt.event.MouseAdapter() {
                override fun mouseEntered(e: java.awt.event.MouseEvent?) {
                    foreground = Color.ORANGE
                    border = BorderFactory.createLineBorder(Color.ORANGE, 2)
                }

                override fun mouseExited(e: java.awt.event.MouseEvent?) {
                    foreground = Color.YELLOW
                    border = BorderFactory.createLineBorder(Color.YELLOW, 2)
                }
            })
        }
    }

    private fun createMenuButton(text: String): JButton {
        return createLevelButton(text, true) // Используем тот же стиль кнопок
    }
}

