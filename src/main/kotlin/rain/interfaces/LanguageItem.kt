package rain.interfaces

interface Labelable<T: LanguageItem> {
    val label: LabelInterface<out T>
}

interface LanguageItem: GraphableItem, Labelable<LanguageItem>  {

    override val labels: List<String> get() = label.allNames

    override val primaryLabel: String get() = label.name

    val context: ContextInterface get() = label.context

    val graph: GraphInterface get() = this.context.graph

    fun save() {
        graph.save(this)
//        return this
    }

//    fun read() {
//        graph.read(this)
////        return this
//    }

    fun delete() {
        graph.delete(this.key)
    }

    fun mergeMe() {
        graph.merge(this)
//        return this
    }

    fun createMe() {
        graph.create(this)
//        return this
    }

//    fun <T>getAs(n:String) = this[n] as T

}

// TODO: why these interfaces below...?

// ===========================================================================================================

interface LanguageNode: LanguageItem, GraphableNode {

}

// ===========================================================================================================

interface LanguageRelationship: LanguageItem, GraphableRelationship {}