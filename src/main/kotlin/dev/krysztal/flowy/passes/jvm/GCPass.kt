package dev.krysztal.flowy.passes.jvm

import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analyzer.node.AnalysisInvokeMethodNode
import dev.krysztal.flowy.analyzer.node.AnalysisNode
import dev.krysztal.flowy.passes.FlowyPass
import dev.krysztal.flowy.passes.FlowyReport

class GCPass : FlowyPass {
    override fun trigger(node: AnalysisNode): Boolean {
        return when (node) {
            is AnalysisInvokeMethodNode -> node.owner == "java/lang/System" && node.name == "gc"
            else -> false
        }
    }

    override fun analysis(node: AnalysisNode): FlowyReport? {
        return GCPassReport(node)
    }
}

private class GCPassReport(
    private val node: AnalysisNode? = null,
    override val reportName: Identifier = Identifier("jvm", "gc"),
) : FlowyReport {
    override fun toJson(): JsonObject? {
        val jsonObject = JsonObject()
        jsonObject.addProperty("invoke_gc", "Invoke `System.gc` at node `${node?.uuid}`.")
        return jsonObject
    }
}
