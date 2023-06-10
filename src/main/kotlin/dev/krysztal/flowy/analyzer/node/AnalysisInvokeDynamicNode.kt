package dev.krysztal.flowy.analyzer.node

import org.objectweb.asm.Handle
import java.util.*

data class AnalysisInvokeDynamicNode(
    val name: String?,
    val descriptor: String?,
    val bootstrapMethodHandle: Handle?,
    val bootstrapMethodArguments: List<Any?>,
    var analysisTree: MutableList<AnalysisNode>? = null,
    override val uuid: String = UUID.randomUUID().toString(),
) : AnalysisNode {
    override fun tree(): MutableList<AnalysisNode>? {
        return analysisTree
    }

    override fun clearTree() {
        analysisTree = null
    }
}
