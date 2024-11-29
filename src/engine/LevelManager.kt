package engine

object LevelManager {
    val levels: MutableList<String> = mutableListOf()
    const val MAX_LEVELS = 20

    init {
        for (i in 1 .. MAX_LEVELS) {
            levels.add("resources/levels/$i.level")
        }
    }

    var unlockedLevels = 1

    fun getLevelPath(levelIndex: Int): String {
        return levels[levelIndex]
    }

    fun getUnlockedLevels(): List<String> {
        return levels.take(unlockedLevels)
    }

    fun unlockNextLevel(thisLevelIndex: Int) {
        if (unlockedLevels < MAX_LEVELS && thisLevelIndex == unlockedLevels - 1) {
            unlockedLevels++
        }
    }

    fun isLevelUnlocked(levelIndex: Int): Boolean {
        return levelIndex < unlockedLevels
    }
}
