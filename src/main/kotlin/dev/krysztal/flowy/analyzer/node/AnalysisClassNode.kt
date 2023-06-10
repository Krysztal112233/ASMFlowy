package dev.krysztal.flowy.analyzer.node

import java.util.*

data class AnalysisClassNode(
    val version: Int,
    val access: Int,
    val name: String?,
    val signature: String?,
    val superName: String?,
    val interfaces: Array<out String>?,
    var analysisTree: MutableList<AnalysisNode>,
    override val uuid: String = UUID.randomUUID().toString(),
) : AnalysisNode {
    override fun tree(): MutableList<AnalysisNode> {
        return analysisTree
    }

    override fun clearTree() {
        analysisTree.clear()
    }

}