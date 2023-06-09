package dev.krysztal.flowy.passes

import dev.krysztal.flowy.Identifier
import dev.krysztal.flowy.analysizer.node.AnalysisNode
import dev.krysztal.flowy.passes.io.FileCreatePass
import dev.krysztal.flowy.passes.io.FileReadPass
import dev.krysztal.flowy.passes.io.FileWritePass
import dev.krysztal.flowy.passes.jvm.ClassLoaderPass
import dev.krysztal.flowy.passes.jvm.GCPass

interface FlowyPass {
    companion object {
        val passes = mapOf(
            Identifier("io", "file_create") to FileCreatePass(),
            Identifier("io", "file_read") to FileReadPass(),
            Identifier("io", "file_write") to FileWritePass(),

            Identifier("jvm", "gc") to GCPass(),
            Identifier("jvm", "classloader") to ClassLoaderPass(),
        )
    }

    fun trigger(node: AnalysisNode): Boolean = false

    fun analysis(node: AnalysisNode ): FlowyReport? = null
}