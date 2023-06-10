package dev.krysztal.flowy

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dev.krysztal.flowy.analyzer.AnalysisPipeline
import dev.krysztal.flowy.passes.FlowyPass
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.multiple
import org.objectweb.asm.ClassReader
import kotlin.io.path.*

@OptIn(ExperimentalPathApi::class)
fun main(args: Array<String>) {
    val parser = ArgParser("ASMFlowy")

    val input by parser.option(ArgType.String, "input", "i", "Input file or directory.").default(".").multiple()
    val exclude by parser.option(ArgType.String, "exclude", "e", "Exclude file or directory.").multiple()
    val outputDir by parser.option(
        ArgType.String,
        "output-dir",
        "Output directory."
    ).default("./result")
    val enablePass by parser.option(ArgType.String, "pass", "p", "Enable some ASMFlowy pass.").multiple()
    val disablePass by parser.option(ArgType.String, "disable-pass", "dp", "Disable some ASMFlowy pass.").multiple()

    parser.parse(args)

    Thread.sleep(1)
    System.gc()

    // Build enabled pass
    val enabledPasses = (
            if (enablePass.isEmpty()) FlowyPass.passes
            else FlowyPass.passes.filter { enablePass.any { pass -> pass.equals(it) } }
            ).toMutableMap()

    disablePass.forEach { enabledPasses.remove(Identifier(it)) }

    // Build excludes
    val processedRegex = exclude.map { Regex(it) }

    // Build pipelines
    val analysisPipelines = input
        .map { Path(it).walk(PathWalkOption.BREADTH_FIRST) }
        .flatMap { it }
        .asSequence()
        .filter { it.extension == "class" }
        .filter { processedRegex.none { regex: Regex -> regex.containsMatchIn(it.pathString) } }
        .filter { !it.isDirectory() }
        .map { AnalysisPipeline(ClassReader(it.toFile().inputStream()), enabledPasses, it.pathString) }
        .toList()
        .associateBy { it.filePath }


    val bytecodeAnalysis = analysisPipelines.map { Pair(it.key, it.value.parse()) }

    val path = Path(outputDir)
    if (path.notExists()) path.createDirectory()

    Path(path.toString(), "bytecode.json").toFile().writeText(
        GsonBuilder().setPrettyPrinting().create().toJson(bytecodeAnalysis.toMap())
    )


    val r = analysisPipelines.map {
        val resultMap = mutableMapOf<Identifier, MutableList<JsonObject?>>()
        it.value.passing(resultMap)
        Pair(it.key, resultMap)
    }.toMap()

    Path(path.toString(), "result.json").writeText(GsonBuilder().setPrettyPrinting().create().toJson(r))
}

