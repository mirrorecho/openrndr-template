package rain.language._bak

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
//
//}