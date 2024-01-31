package rain.language
import rain.interfaces.ContextInterface
import rain.utils.*

class FancyProperty<T>(
    val name: String,
    val value: T,
    val context: ContextInterface,
    val universalName: String = autoKey(),

) {
    val graphValue: String get() = ":FANCY:$universalName"

}