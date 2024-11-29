package screens

import GameFrame
import java.awt.*
import javax.swing.*

class GameOverPanel(private val frame: GameFrame) : JPanel() {
    init {
        layout = BorderLayout()
        val label = JLabel("Игра окончена", JLabel.CENTER)
        label.font = Font("Arial", Font.BOLD, 36)
        label.foreground = Color.RED

        val menuButton = JButton("В меню")
        menuButton.font = Font("Arial", Font.BOLD, 20)
        menuButton.addActionListener {
            frame.contentPane = MenuPanel(frame)
            frame.validate()
        }

        add(label, BorderLayout.CENTER)
        add(menuButton, BorderLayout.SOUTH)
    }
}
