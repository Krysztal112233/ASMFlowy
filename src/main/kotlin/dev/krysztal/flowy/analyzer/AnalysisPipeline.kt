package dev.krysztal.flowy.analyzer

import com.google.gson.JsonObject
import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analyzer.node.AnalysisNode
import dev.krysztal.flowy.passes.FlowyPass
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes

class AnalysisPipeline(
    private val reader: ClassReader,
    private val passes: MutableMap<Identifier, FlowyPass>,
    val filePath: String
) {

    private val analysisTree = mutableListOf<AnalysisNode>()

    fun parse(): List<AnalysisNode> {
        reader.accept(AnalysisClassVisitor(Opcodes.ASM9, analysisTree), ClassReader.EXPAND_FRAMES)
        return this.analysisTree
    }

    fun passing(result: MutableMap<Identifier, MutableList<JsonObject?>>) {
        analysisTree.forEach {
            walk(it) { node ->
                passes.forEach { passEntry ->
                    if (passEntry.value.trigger(node)) {
                        val report = passEntry.value.analysis(node)
                        val reports = result[report!!.reportName] ?: mutableListOf()
                        reports.add(report.toJson())
                        result[report.reportName] = reports
                    }
                }
            }
        }
    }

    fun result() {}

    private fun walk(node: AnalysisNode, action: ((AnalysisNode) -> Unit)? = null) {
        node.tree()?.forEach {
            action?.invoke(it)
            walk(it, action)
        }
    }

}
