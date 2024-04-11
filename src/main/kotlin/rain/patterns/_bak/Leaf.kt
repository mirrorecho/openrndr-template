package rain.patterns._bak
//
//import rain.language.*
//import rain.patterns.selects.*
//
//// a node that represents an iterable over a group nodes ... each of which is connected
//// to this node, in a "pattern"
//open class Leaf(
//    key:String = rain.utils.autoKey(),
//): Tree(key) {
//    companion object : NodeCompanion<Leaf>(Tree.childLabel { k -> Leaf(k) })
//    override val label: NodeLabel<out Leaf> = Leaf.label
//
//    // TODO: implement the below
////    # TODO: assume this doesn't need to be serialized?
////    leaf_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
////    vein_hooks: Iterable[Callable[["rain.Pattern", Any, int], Any]] = ()
////    _parentage = ()
////    # TODO: MAYBE consider this
////    # node_hooks: Iterable[Callable[["rain.Pattern", "rain.Pattern"], "rain.Pattern"]] = ()
////
//
//    override val isAlter = false
//
//    override val isLeaf = true
//
//    override val branches: TreeSelect<out Tree> get() = TreeSelectEmpty(Tree.label, this)
//
//    override val nodes: TreeSelect<out Tree> get() = TreeSelectSelf(Tree.label, this)
//
//    // TODO: implement hook logic here
//    // ... and may need to change to a TreeSelfSelect here (in order to get TreeSelectContext)
//    override val leaves: TreeSelect<out Leaf> get() = TreeSelectSelf(Leaf.label, this)
//
//    override var cuePath: CuePath? = null
//
//}
