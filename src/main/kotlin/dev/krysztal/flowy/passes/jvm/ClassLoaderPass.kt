package dev.krysztal.flowy.passes.jvm

import com.google.gson.Gson
import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analyzer.node.*
import dev.krysztal.flowy.passes.FlowyPass
import dev.krysztal.flowy.passes.FlowyReport

class ClassLoaderPass : FlowyPass {
    override fun trigger(node: AnalysisNode): Boolean {
        this.javaClass.classLoader
        return when (node) {
            is AnalysisInvokeMethodNode -> Gson().toJson(node).contains("getClassLoader")
            else -> false
        }
    }


    override fun analysis(node: AnalysisNode): FlowyReport? {
        return ClassLoaderPassReport(node)
    }

}

private class ClassLoaderPassReport(
    private val analysisNode: AnalysisNode,
    override val reportName: Identifier = Identifier("jvm", "classloader"),
) : FlowyReport {
    override fun toJson(): JsonObject? {
        val jsonObject = JsonObject()

        jsonObject.addProperty("invoke_get_classloader", "Invoke `Class.getClassLoader` at node `${analysisNode.uuid}`")

        return jsonObject
    }
}
