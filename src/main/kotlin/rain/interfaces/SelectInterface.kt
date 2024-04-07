package rain.interfaces

interface SelectInterface<T:LanguageItem> {

    val label: LabelInterface<out T>

    val graph: GraphInterface get() = label.context.graph

    val keys: List<String>?

    val properties: Map<String, Any?>?

    val selectFrom: SelectInterface<*>?

    val direction: SelectDirection?

    val cachedItems: List<T>

    fun asSequence(): Sequence<T>

    fun forEach(action:(T)->Unit) {
        asSequence().forEach {action(it)} }

    // TODO maybe: could use for more general implementation
//    fun indexOfFirst(key:String, predicate: (LanguageItem)-> Boolean ): Int {
//        return this.asSequence().indexOfFirst(predicate)
//    }

    fun indexOfFirst(key:String ): Int = this.asSequence().indexOfFirst {it.key==key}

    operator fun get(key: String) {throw NotImplementedError()}

    operator fun get(index: Int) {throw NotImplementedError()}

    fun contains(key: String): Boolean = this.indexOfFirst(key) > -1

    val first: T? get() = this.asSequence().firstOrNull()

}

interface SelectNodeInterface<T:LanguageNode>:SelectInterface<T>  {
    override val label: NodeLabelInterface<out T>
}

interface SelectRelationshipInterface<T:LanguageRelationship>:SelectInterface<T> {
    override val label: RelationshipLabelInterface<out T>
}

