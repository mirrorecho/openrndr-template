package rain.interfaces

import rain.language.Relationship
import rain.language.Select
import rain.language.TargetedRelationshipSelect

interface SelectInterface {

    val context: ContextInterface

    val graph: GraphInterface get() = this.context.graph

    val label: String?

    val keys: List<String>?

    val properties: Map<String, Any?>?

    val selectFrom: SelectInterface?

    val direction: SelectDirection?

    val cachedItems: List<LanguageItem>

    fun asSequence(): Sequence<LanguageItem> = sequence {
        yieldAll(this@SelectInterface.graph.selectItems(this@SelectInterface ))
    }

    fun <T:LanguageItem>asTypedSequence(): Sequence<T> = sequence {
        yieldAll(this@SelectInterface.graph.selectItems(this@SelectInterface ))
    }

    fun forEach(action:(LanguageItem)->Unit) {asSequence().forEach {action(it)} }

    // TODO maybe: could use for more general implementation
//    fun indexOfFirst(key:String, predicate: (LanguageItem)-> Boolean ): Int {
//        return this.asSequence().indexOfFirst(predicate)
//    }

    fun indexOfFirst(key:String ): Int = this.asSequence().indexOfFirst {it.key==key}

    // TODO: ever used?
    fun contains(key: String): Boolean = this.indexOfFirst(key) > -1

    val first: LanguageItem? get() = this.asSequence().firstOrNull()

    fun <T:LanguageItem>firstAs(): T? = this.asTypedSequence<T>().firstOrNull()

    // TODO: implement a firstAs?

    fun get(key: String) {throw NotImplementedError()}

    // same as implementing __call__ method in python... cool
    operator fun invoke(label:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null): SelectInterface {throw NotImplementedError()}

    fun r(direction:SelectDirection, label:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null):SelectInterface{
        throw NotImplementedError()
    }

    fun n(label:String?=null, keys:List<String>?=null, properties:Map<String,Any>?=null):SelectInterface {
        throw NotImplementedError()
    }

}

