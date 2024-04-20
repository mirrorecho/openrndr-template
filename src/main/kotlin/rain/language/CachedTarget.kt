package rain.language

// TODO: move somewhere else
class CachedTarget<T:Node>(
    val sourceNode:Node,
    val rLabel: RelationshipLabel,
    val nLabel: NodeLabel<T>,
) {
    val rQuery = sourceNode.relates(label=rLabel)
    val query = rQuery.nodes()
    private var _cachedValue:T? = query(nLabel).firstOrNull()

    var target: T? get() {
        _cachedValue?.let { return it }
        _cachedValue = query(nLabel).firstOrNull()
        return _cachedValue
    }
        set(value) {
            _cachedValue = value
            rQuery(rLabel).firstOrNull()?.delete()
            value?.let { sourceNode.relate(rLabel, it) }
        }

    fun createIfMissing() {
        if (_cachedValue==null) {
            _cachedValue = nLabel.create().also { sourceNode.relate(rLabel, it) }
        }
    }

}