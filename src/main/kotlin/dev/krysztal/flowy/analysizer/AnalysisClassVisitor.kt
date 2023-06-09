package dev.krysztal.flowy.analysizer

import dev.krysztal.flowy.analysizer.node.AnalysisClassNode
import dev.krysztal.flowy.analysizer.node.AnalysisDefineMethodNode
import dev.krysztal.flowy.analysizer.node.AnalysisNode
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class AnalysisClassVisitor(
    private val api: Int = Opcodes.ASM9,
    private val analysisTree: MutableList<AnalysisNode>
) : ClassVisitor(api) {
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        analysisTree.add(AnalysisClassNode(version, access, name, signature, superName, interfaces, mutableListOf()))
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val mutableList = mutableListOf<AnalysisNode>()
        analysisTree.last().tree()?.add(AnalysisDefineMethodNode(access, name, descriptor, signature, mutableList))
        return AnalysisMethodVisitor(analysisTree = mutableList)
    }
}