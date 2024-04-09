package rain.language._bak

// TODO: remove when ready (for sandbox/testing purposes only)


//import rain.interfaces.*
//
//open class FancyNode(
//    key:String = rain.utils.autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//):Node(key, properties, context) {
//
//    companion object : ItemCompanion() {
//        override val label: Label<FancyNode> = Label(
//            factory = { k, p, c -> FancyNode(k, p, c) },
//            labels = listOf("FancyNode"),
//        )
//    }
//    override val label: LabelInterface get() = FancyNode.label
//
//    var yo: String
//        get() = this.properties["yo"].toString()
//        set(v) {
//            this.properties["yo"] = v
//        }
//}
