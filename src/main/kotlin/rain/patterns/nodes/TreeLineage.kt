package rain.patterns.nodes

import rain.language.NodeLabel
import rain.language.SelectNodes
import rain.patterns.relationships.CUES
import rain.patterns.relationships.CUES_FIRST
import rain.patterns.relationships.CUES_NEXT
import rain.patterns.relationships.TRIGGERS

class TreeLineage<T:Tree>(
    val tree: T,
    val label: NodeLabel<out T>,
    val cue: Cue? = null,
    val parent: TreeLineage<T>? = null,
) {
    //TODO: below assumes that all ancestor properties should carry down... are we sure that's what we want?
    val properties: Map<String, Any?> by lazy { parent?.properties.orEmpty() + tree.properties }

    override fun toString() = "TREE LINEAGE: ${ancestors.toList().reversed().map { it.tree.key }} - ${tree.labelName}(${tree.key}) $properties"

    fun <T>getAs(name:String):T = properties[name] as T

    operator fun get(name:String) = properties[name]

    private fun getChildren(qCue:SelectNodes):Sequence<TreeLineage<T>> = sequence {
        qCue.first(Cue)?.let {cue ->
            TreeLineage(cue[CUES()](label).first(), label, cue, this@TreeLineage).let {
                yield(it)
                yieldAll(getChildren(cue[CUES_NEXT()]))
            }
        }
    }

    val children get() = getChildren(tree[CUES_FIRST()])

    val nodes:Sequence<TreeLineage<T>> get() = sequence {
        yield(this@TreeLineage)
        children.forEach { yieldAll(it.nodes) }
    }

    val leaves:Sequence<TreeLineage<T>> get() = sequence {
        if (tree.isLeaf) yield(this@TreeLineage)
        else children.forEach { yieldAll(it.leaves) }
    }

    val ancestors:Sequence<TreeLineage<T>> get() = sequence {
        parent?.let{ yield(it); yieldAll(it.ancestors); }
    }

    val previous get() = cue?.let{ it[CUES_NEXT.left(), CUES()].first(label) }
    val next get() = cue?.let{ it[CUES_NEXT(), CUES()].first(label) }

}