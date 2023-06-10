package dev.krysztal.flowy.analyzer.node

sealed interface AnalysisNode {

    val uuid: String
    fun tree(): MutableList<AnalysisNode>? = null
    fun clearTree()
}
