package rain.interfaces

interface Labelable<T: LanguageItem> {
    val label: LabelInterface<out T>
}

interface LanguageItem: GraphableItem, Labelable<LanguageItem>  {

    override val labels: List<String> get() = label.allNames

    override val primaryLabel: String get() = label.name

    val context: ContextInterface get() = label.context

    val graph: GraphInterface get() = this.context.graph

}

// ===========================================================================================================

interface LanguageNode: LanguageItem, GraphableNode {
    fun save() = graph.save(this)

    fun read() = graph.read(this)

    fun delete() = graph.deleteNode(this.key)

    fun mergeMe() = graph.merge(this) // TODO maybe: rename to merge?

    fun createMe() = graph.create(this) // TODO maybe: rename to create?

}

// ===========================================================================================================

interface LanguageRelationship: LanguageItem, GraphableRelationship {
    fun save() = graph.save(this)

    fun read() = graph.read(this)

    fun delete() = graph.deleteRelationship(this.key)

    fun mergeMe() = graph.merge(this) // TODO maybe: rename to merge?

    fun createMe() = graph.create(this) // TODO maybe: rename to create?
}