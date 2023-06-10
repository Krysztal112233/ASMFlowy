package dev.krysztal.flowy.analyzer

import dev.krysztal.flowy.analyzer.node.AnalysisInvokeDynamicNode
import dev.krysztal.flowy.analyzer.node.AnalysisInvokeMethodNode
import dev.krysztal.flowy.analyzer.node.AnalysisNode
import org.objectweb.asm.Handle
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class AnalysisMethodVisitor(
    private val api: Int = Opcodes.ASM9,
    val analysisTree: MutableList<AnalysisNode>
) : MethodVisitor(api) {

    override fun visitMethodInsn(
        opcode: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean,
    ) {
        analysisTree.add(
            AnalysisInvokeMethodNode(
                opcode,
                owner,
                name,
                descriptor,
                isInterface,
                mutableListOf()
            )
        )
    }

    override fun visitInvokeDynamicInsn(
        name: String?,
        descriptor: String?,
        bootstrapMethodHandle: Handle?,
        vararg bootstrapMethodArguments: Any?
    ) {
        analysisTree.add(
            AnalysisInvokeDynamicNode(
                name,
                descriptor,
                bootstrapMethodHandle,
                bootstrapMethodArguments.toList(),
                mutableListOf()
            )
        )
    }
}

