package rain.interfaces

// TODO: is this interface even necessary / worth it?
interface LabelInterface {
    val labels: List<String>
    val primary: String
//    val factory: (context:ContextInterface, key:String, properties: Map<String, Any?>)-> LanguageItem
    // TODO: something to say if it's a node or relationship?
    val factory: (key:String, properties: Map<String, Any?>, context:ContextInterface)-> LanguageItem
//    val context:ContextInterface

    fun make(key:String, properties: Map<String, Any?> = mapOf(), context: ContextInterface): LanguageItem {
        return this.factory(key,properties, context)
    }

    // TODO: needed?
//    fun createItem(context:ContextInterface, key:String, properties: Map<String, Any?> =mapOf()): LanguageItem {
//        return this.factory(context, key, properties)
//    }
}