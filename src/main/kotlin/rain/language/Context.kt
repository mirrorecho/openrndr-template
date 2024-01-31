package rain.language

import org.openrndr.Program
import rain.interfaces.*
import rain.patterns.Cell

open class Context<G:GraphInterface>(
    override val graph: G
): ContextInterface {

    private val labelRegistry: MutableMap<String, LabelInterface> = mutableMapOf()

    override fun registerLabel(label:LabelInterface) {
        this.labelRegistry[label.primary] = label
    }

    override fun get(labelName:String): LabelInterface? = this.labelRegistry[labelName]

    override fun <T: LanguageItem>make(labelName:String, key:String, properties: Map<String, Any?>, context:ContextInterface): T {
        // TODO: throw more specific exception here if label not found?
        return this.labelRegistry[labelName]?.make(key, properties, context) as T
    }

    fun <T:LanguageItem>registerItemType(labels: List<String> = listOf(), factory: (key:String, properties: Map<String, Any?>, context:ContextInterface)-> T) {
        val typeLabel = Label(labels, factory)
        this.labelRegistry[labels[0]] = typeLabel
    }

    fun <T:LanguageItem>makeLabel(
        labels:List<String>,
        factory:(key:String, properties: Map<String, Any?>, context:ContextInterface)-> T
        ): Label<T> {
        return Label(labels, factory).apply { registerLabel(this) }
    }

    fun <T:LanguageItem>getLabel(
        vararg labels:String,
        factory:(key:String, properties: Map<String, Any?>, context:ContextInterface)-> T
    ): LabelInterface {
        // TODO: is this called over and over and over with every node creation???? (although maybe that's OK)
        return get(labels[0]) ?: makeLabel(labels.toList(), factory)
    }

    private val fancyProperties: MutableMap<String, FancyProperty<*>> = mutableMapOf()

    override fun setFancyProperty(fancyProperty: FancyProperty<*>) {
        fancyProperties[fancyProperty.universalName] = fancyProperty
    }

    override fun <T>getFancyProperty(universalName: String): FancyProperty<T> {
        return fancyProperties[universalName] as FancyProperty<T>
    }

    // TODO: assume below not needed (registry is now with the score object)
    // private val actRegistry: MutableMap<Pair<String, String>, Act> = mutableMapOf()


    // TODO: remove Ops Implementation ... !!!!
//    // Ops implementation:
//    // TODO maybe: Ops implementation could live with a MachinePalette, instead of with this Context?
//

//
//    override fun getOp(machineKey: String, opKey:String): MachineFuncOp? = opsRegistry[Pair(machineKey, opKey)]
//
//    override fun stopOp(machineKey: String, opKey:String) {
//        val keyPair = Pair(machineKey, opKey)
//        opsRegistry[keyPair]?.stop()
//        opsRegistry.remove(keyPair)
//    }
//
//    // TODO: below breaks separation of concerns - refactor? (e.g. create a public interface for MachineFunc)
//    override fun cycleOps() {
//        // updates and renders all running ops
//        opsRegistry.values.filter { it.isRunning }.forEach {
//            it.machineFunc.apply {
//                updateOp(it)
//                renderOp(it, )
//            }
//        }
//    }

}