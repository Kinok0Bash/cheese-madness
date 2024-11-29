package screens

import GameFrame
import engine.LevelManager
import engine.SpriteLoader
import java.awt.*
import javax.swing.*

class MenuPanel(private val frame: GameFrame) : JPanel() {

    private val backgroundBlocks = mutableListOf<Rectangle>()
    private val blockImage: Image = SpriteLoader.load("resources/sprites/background.png")

    init {
        println(LevelManager.unlockedLevels)
        layout = null
        background = Color.DARK_GRAY
        createBackgroundBlocks()

        // Заголовок
        val titleLabel = JLabel("СЫРНОЕ БЕЗУМИЕ", JLabel.CENTER)
        titleLabel.font = Font("Arial", Font.BOLD, 36)
        titleLabel.foreground = Color.YELLOW
        titleLabel.setBounds(0, 50, 1280, 50)

        // Иконка
        val iconLabel = JLabel(ImageIcon("resources/sprites/cheese.png")) // Убедитесь, что "icon.png" находится в корневой папке проекта
        iconLabel.setBounds(560, 120, 160, 160)

        // Кнопки
        val startButton = createMenuButton("Начать игру")
        val levelSelectButton = createMenuButton("Выбрать уровень")

        // Устанавливаем положение кнопок
        startButton.setBounds(540, 300, 200, 50)
        levelSelectButton.setBounds(540, 370, 200, 50)

        // Добавляем обработчики действий
        startButton.addActionListener {
            val gamePanel = GamePanel(frame, 0)
            frame.contentPane = gamePanel
            gamePanel.requestFocusInWindow()
            frame.validate()
        }

        levelSelectButton.addActionListener {
            frame.contentPane = LevelSelectPanel(frame) // Переход к выбору уровня
            frame.validate()
        }

        // Добавляем элементы на панель
        add(titleLabel)
        add(iconLabel)
        add(startButton)
        add(levelSelectButton)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g as Graphics2D

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

    private fun createMenuButton(text: String): JButton {
        return JButton(text).apply {
            font = Font("Arial", Font.PLAIN, 18)
            isFocusPainted = false
            isContentAreaFilled = false
            border = BorderFactory.createLineBorder(Color.YELLOW, 2)
            foreground = Color.YELLOW
            background = Color.DARK_GRAY
            cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

            addMouseListener(object : java.awt.event.MouseAdapter() {
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
}
