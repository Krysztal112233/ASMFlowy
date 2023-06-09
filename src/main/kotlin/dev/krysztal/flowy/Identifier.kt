package dev.krysztal.flowy

data class Identifier(
    val group: String,
    val name: String,
) {
    constructor(string: String) : this(string.split(":")[0], string.split(":")[1])

    override fun toString(): String {
        return "${group}:${name}"
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is String -> this.toString() == other
            is Identifier -> other.toString() == this.toString()
            else -> false
        }
    }

    override fun hashCode(): Int {
        var result = group.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}
