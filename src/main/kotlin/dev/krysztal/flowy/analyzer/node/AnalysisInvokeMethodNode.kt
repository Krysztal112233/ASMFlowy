package dev.krysztal.flowy.analyzer.node

import java.util.*

data class AnalysisInvokeMethodNode(
    val opcode: Int,
    val owner: String?,
    val name: String?,
    val descriptor: String?,
    val isInterface: Boolean,
    var analysisTree: MutableList<AnalysisNode>? = null,
    override val uuid: String = UUID.randomUUID().toString()
) : AnalysisNode {
    override fun tree(): MutableList<AnalysisNode>? {
        return analysisTree
    }

    override fun clearTree() {
        analysisTree = null
    }
}