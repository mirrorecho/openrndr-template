package rain.interfaces

import rain.language.Item
import rain.language.TargetedRelationshipSelect
import rain.utils.autoKey

interface LanguageItem: GraphableItem {

    val label: LabelInterface

    // TODO include parent classes in label list
    override val labels: List<String> get() = this.label.labels

    override val primaryLabel: String get() = this.label.primary

    val context: ContextInterface

    val graph: GraphInterface get() = this.context.graph

    val selectSelf: SelectInterface

    fun save(): LanguageItem {
        context.graph.save(this)
        return this
    }

    fun read(): LanguageItem {
        graph.read(this)
        return this
    }

    fun delete() {
        graph.delete(this.key)
    }

    fun mergeMe(): LanguageItem {
        graph.merge(this)
        return this
    }

    // TODO: this is inconvenient because we lose the specific type information when returning this as a LanguageItem
    // remove/rethink?
    fun createMe(): LanguageItem {
        graph.create(this)
        return this
    }

    fun <T>getAs(n:String) = this[n] as T

}

// ===========================================================================================================

interface LanguageNode: LanguageItem, GraphableNode {
    fun r(direction: SelectDirection, label:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null): SelectInterface

    // TODO: is this even necessary in this interface? Or just include in the Node class?
    fun <T:LanguageNode?>targetsAs(label:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null): T?

    fun <T:LanguageNode>targetsOrMakeAs(relationshipLabel:String, makeTargetLabel:String, makeTargetKey:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null): T
}

// ===========================================================================================================

interface LanguageRelationship: LanguageItem, GraphableRelationship {

}