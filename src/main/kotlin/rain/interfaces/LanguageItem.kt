package rain.interfaces

// TODO: this extra inheritance is odd here...
interface Labelable<T: LanguageItem> {
    val label: LabelInterface<out T>
}

interface LanguageItem: GraphableItem, Labelable<LanguageItem>  {

    override val labels: List<String> get() = label.allNames

    override val labelName: String get() = label.labelName

    val context: ContextInterface get() = label.context

    val graph: GraphInterface get() = this.context.graph

}

// ===========================================================================================================

interface LanguageNode: LanguageItem, GraphableNode {
    override val label: NodeLabelInterface<out LanguageNode>

    fun save() = graph.save(this)

    fun read() = graph.read(this)

    fun delete() {
        graph.deleteNode(this.key)
        label.registry.remove(this.key)
    }

}

// ===========================================================================================================

interface LanguageRelationship: LanguageItem, GraphableRelationship {
    fun save() = graph.save(this)

    fun read() = graph.read(this)

    fun delete() = graph.deleteRelationship(this.key)

//    fun mergeMe() = graph.merge(this) // TODO maybe: rename to merge?
//
//    fun createMe() = graph.create(this) // TODO maybe: rename to create?
}