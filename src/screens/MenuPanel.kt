package screens

import GameFrame
import java.awt.*
import javax.swing.*

class MenuPanel(private val frame: GameFrame) : JPanel() {
    init {
        layout = BorderLayout()
        val titleLabel = JLabel("СЫРНОЕ БЕЗУМИЕ", JLabel.CENTER)
        titleLabel.font = Font("Arial", Font.BOLD, 36)
        titleLabel.foreground = Color.RED

        val startButton = JButton("Начать игру")
        val levelSelectButton = JButton("Выбрать уровень")
        startButton.font = Font("Arial", Font.BOLD, 20)
        levelSelectButton.font = Font("Arial", Font.BOLD, 20)

        val buttonsPanel = JPanel()
        buttonsPanel.layout = BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)
        buttonsPanel.add(startButton)
        buttonsPanel.add(Box.createRigidArea(Dimension(0, 20)))
        buttonsPanel.add(levelSelectButton)
        buttonsPanel.layout = GridLayout().apply {
            rows = 2
            columns = 1
        }

        startButton.addActionListener {
            frame.contentPane = GamePanel(frame, 0) // Начинаем с первого уровня
            frame.validate()
        }

        levelSelectButton.addActionListener {
            frame.contentPane = LevelSelectPanel(frame) // Переходим к выбору уровня
            frame.validate()
        }

        add(titleLabel, BorderLayout.NORTH)
        add(buttonsPanel, BorderLayout.CENTER)
    }
}

