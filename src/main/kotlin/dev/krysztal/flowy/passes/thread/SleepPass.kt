package dev.krysztal.flowy.passes.thread

import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analyzer.node.*
import dev.krysztal.flowy.passes.FlowyPass
import dev.krysztal.flowy.passes.FlowyReport

class SleepPass : FlowyPass {
    override fun trigger(node: AnalysisNode): Boolean {
        return when (node) {
            is AnalysisInvokeMethodNode -> node.owner == "java/lang/Thread" && node.name == "sleep"
            else -> false
        }
    }


    override fun analysis(node: AnalysisNode): FlowyReport? {
        return Report(node)
    }
}

class Report(
    private val node: AnalysisNode,
    override val reportName: Identifier = Identifier("thread", "sleep"),
) : FlowyReport {
    override fun toJson(): JsonObject {
        val jsonObject = JsonObject()
        jsonObject.addProperty("invoke_sleep", "Invoke `Thread.sleep` at node `${node.uuid}`.")
        return jsonObject
    }
}