package rain.language

// TODO: move somewhere else
class CachedTarget<T:Node>(
    val sourceNode:Node,
    val rLabel: RelationshipLabel,
    val nLabel: NodeLabel<T>,
) {
    val rQuery = sourceNode.r(rLabel)
    val query = rQuery.n(nLabel)
    private var _cachedValue:T? = query.first

    var target: T? get() {
        _cachedValue?.let { return it }
        _cachedValue = query.first
        return _cachedValue
    }
        set(value) {
            _cachedValue = value
            rQuery.first?.delete()
            value?.let { sourceNode.relate(rLabel, it) }
        }

    fun createIfMissing() {
        if (_cachedValue==null) {
            _cachedValue = nLabel.create().also { sourceNode.relate(rLabel, it) }
        }
    }

}