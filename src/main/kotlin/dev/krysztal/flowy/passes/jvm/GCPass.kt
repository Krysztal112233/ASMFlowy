package dev.krysztal.flowy.passes.jvm

import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analysizer.node.AnalysisInvokeMethodNode
import dev.krysztal.flowy.analysizer.node.AnalysisNode
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
        return Report(node)
    }
}

private class Report(
    private val node: AnalysisNode? = null,
) : FlowyReport {
    override val reportName: Identifier get() = Identifier("jvm", "gc")

    override fun fromJson(json: JsonObject) {
        super.fromJson(json)
    }

    override fun toJson(): JsonObject? {
        val jsonObject = JsonObject()
        jsonObject.addProperty("invoke_gc", "In this class, invoked GC at analysis node ${node?.uuid}.")
        return jsonObject
    }
}
