package dev.krysztal.flowy.passes.io

import dev.krysztal.flowy.analyzer.node.AnalysisNode
import dev.krysztal.flowy.passes.FlowyPass

class FileWritePass : FlowyPass {
    override fun trigger(node: AnalysisNode): Boolean {
        return super.trigger(node)
    }
}
