package dev.krysztal.flowy.analyzer.node

import java.util.*

data class AnalysisDefineMethodNode(
    val access: Int,
    val name: String?,
    val descriptor: String?,
    val signature: String?,
    val analysisTree: MutableList<AnalysisNode>,
    override val uuid: String = UUID.randomUUID().toString(),
) : AnalysisNode {
    override fun tree(): MutableList<AnalysisNode> {
        return analysisTree
    }

    override fun clearTree() {
        analysisTree.clear()
    }

}