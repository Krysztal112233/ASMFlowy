package dev.krysztal.flowy.analysizer.node

sealed interface AnalysisNode {

    val uuid: String
    fun tree(): MutableList<AnalysisNode>? = null
    fun clearTree()
}
