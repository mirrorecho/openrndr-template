package rain.rndr._bak.rndr_bak

//open class Text(
//    key:String = rain.utils.autoKey(),
//    properties: Map<String, Any?> = mapOf(),
//    context: ContextInterface = LocalContext,
//): RndrMachine(key, properties, context) {
//
//    companion object : ItemCompanion() {
//        override val label: Label<Text> = Label(
//            factory = { k, p, c -> Text(k, p, c) },
//            labels = listOf("Text", "RndrMachine", "Machine"),
//        )
//    }
//
//    override val label: LabelInterface get() = Text.label
//
//    class MachineInstance(
//        machine: RndrMachine,
//        program: Program, // TODO: needed?
//        properties: MutableMap<String, Any?>,
//    ): RndrMachine.MachineInstance(machine, program, properties) {
//
//        class AnimatedCircle : Animatable() {
//            var x: Double = 0.0
//        }
//
//        val animatedCircle = AnimatedCircle()
//
//        val font get() = this.properties["font"] as String
//        val fontSize get() = this.properties["font"] as String
//
//        val fontImageMap = loadFont("data/fonts/$font.otf", 48.0)
//
//        val hHSV = Random.nextDouble(0.2, 0.4)
//        val sHSV = Random.nextDouble(0.2, 0.8)
//        val vHSV get() = this.properties["vHSV"] as Double
//        val radius get() = this.properties["radius"] as Double
//
//
//        override fun render() {
//            this.program.apply {
//                animatedCircle.updateAnimation()
//                if (!animatedCircle.hasAnimations()) {
//                    animatedCircle.apply {
//                        ::x.animate(width.toDouble(), 1000, Easing.CubicInOut)
//                        ::x.complete()
//                        ::x.animate(0.0, 1000, Easing.CubicInOut)
//                        ::x.complete()
//                    }
//                }
//                drawer.fill = ColorHSVa(hHSV, sHSV, vHSV).toRGBa()
//                drawer.stroke = null
//                drawer.circle(animatedCircle.x, height / 2.0, radius)
//            }
//        }
//    }
//
//    override fun instanceFactory(machine: RndrMachine, program: Program, properties: MutableMap<String, Any?>): MachineInstance {
//        return MachineInstance(machine, program, properties)
//    }
//
//
//
//}