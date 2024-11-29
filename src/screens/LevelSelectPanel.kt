package screens

import GameFrame
import engine.LevelManager
import java.awt.*
import javax.swing.*

class LevelSelectPanel(private val frame: GameFrame) : JPanel() {
    private val levelManager = LevelManager()

    init {
        layout = GridLayout(0, 3)
        background = Color.DARK_GRAY

        levelManager.getUnlockedLevels().forEachIndexed { index, _ ->
            val button = JButton("Уровень ${index + 1}")
            button.font = Font("Arial", Font.BOLD, 20)
            button.addActionListener {
                frame.contentPane = GamePanel(frame, index)
                frame.validate()
            }
            add(button)
        }

        val backButton = JButton("Назад")
        backButton.font = Font("Arial", Font.BOLD, 20)
        backButton.addActionListener {
            frame.contentPane = MenuPanel(frame)
            frame.validate()
        }
        add(backButton)
    }
}
