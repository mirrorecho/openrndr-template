package rain.sandbox
//
//import org.openrndr.Program
//import rain.language.Palette
//import rain.machines.nodes.Machine
//import rain.rndr.*
//import rain.utils.autoKey
//
//// TODO / NOTES: base machine types:
////  - AnimValue - a simple value that can be animated, with optional easing/envelope
////  - AnimMulti - multiple values, which can either be fixed values, animated values (with optional easing/envelope),
////      or references to other AnimValue or AnimMulti machines
//
//fun triggerArg(argOpName: String) {}
//
//fun triggerArg(argValue: Double) {}
//
//val testProgram = Program(true)
//
//val triggersToPlay2 = mutableMapOf(
//    0.0 to listOf(
//        Trigger("OPERATE", "SIZE_OP", 4.0, program=testProgram)
//    ),
//    1.0 to listOf(
//
//    ),
//)
//
//val actsToPlay1 = mutableMapOf(
//    0.0 to listOf(
// ======================================================
// THE WINNING ENTRY!!!
//        Act( autoKey(),
//            Circle(), // this would come from the score's machinePalette
//            mapOf(
//            "machine" to "CIRCLE1", // assume only 1 machine necessary for any given work?
//            "op" to null, // op name will be auto-generated key
//            "dur" to 4.0, // assume optional? (time could also just be when all associated machine durs are over?)
//            // TODO maybe: implement these:
//            "time" to 0.0,
//            "startAction" to 1, // 0=nothing, 1=start, 2=free, 3="pause" (op is not running, but still exists)
//            // TODO consider whether endAction is best specified by the machine implementation (as opposed to the trigger)
//            "endAction" to 0, // ditto values as startAction
//            ),
//            Score(), // score is what creates the all this in the first place!
//            mapOf(
//                // omitting RADIUS because assuming in this example that Circle1 does not have a network-connected radius
//                "POSITION" to null, // create a new Act and auto-name it
//            )
//        ),
// ======================================================
//        mapOf(
//            "machine" to "OPERATE", // assume only 1 machine necessary for any given work?
//            "key" to "SIZE_OP",
//            "dur" to 4.0, // assume optional? (time could also just be when all associated machine durs are over?)
//            "startAction" to 1, // 0=nothing, 1=start, 2=free, 3="pause" (op is not running, but still exists)
//            // TODO consider whether endAction is best specified by the machine implementation (as opposed to the trigger)
//            "endAction" to 0, // ditto values as startAction
//        ),
//        mapOf(
//            "machine" to "OPERATE", // assume only 1 machine necessary for any given work?
//            "key" to "SMALL_CIRCLE_OP",
//            "dur" to 4.0, // assume optional? (time could also just be when all associated machine durs are over?)
//            "startAction" to 1, // 0=nothing, 1=start, 2=free, 3="pause" (op is not running, but still exists)
//            // TODO consider whether endAction is best specified by the machine implementation (as opposed to the trigger)
//            "endAction" to 0, // ditto values as startAction
//        ),
//        mapOf(
//            // MACHINE: kick off SIZE ... getting bigger for 2"
//            "machine" to "SIZE", // (an AnimValue)
//            "startValue" to 2.0,
//            "endValue" to 40.0,
//            "dur" to 2.0,
//            "op" to "SIZE_OP",
//        ),
//        mapOf(
//            "machine" to "SMALL_CIRCLE", // (a Circle, which inherits from AnimMulti)
//
//            // "op" to null, // NOTE: null (the default) means auto-create a new op
//
//            // TODO: how to kick off (or connect to) an op for the circle?
//
//            // NOTE that the Circle machine must define these as being able to be directly triggered through the Circle
//            // (as opposed to separately triggering a Color machine)
//            "stroke_h" to triggerArg(290.0),
//            "stroke_s" to triggerArg(0.9),
//            "stroke_v" to triggerArg(0.8),
//            "stroke_a" to triggerArg(1.0),
//
//            "radius" to triggerArg("SIZE_OP"), // TODO: how to connect this to the SIZE machine/op above (shared among many circles)?
//
//            // need to deal with:
//            // - radius
//            // - POSITION - x
//            // - POSITION - y
//            // - STROKE_COLOR - h
//            // - STROKE_COLOR - s
//            // - STROKE_COLOR - v
//            // - STROKE_COLOR - a
//            // - stroke_weight
//            // - FILL_COLOR - h
//            // - FILL_COLOR - s
//            // - FILL_COLOR - v
//            // - FILL_COLOR - a
//
//            // "ops" to listOf("SIZE_OP", "SMALL_CIRCLE_OP"),
//            // TODO: OK, but how to handle the case where multiple ops could would have values
//            //  with the same names? (e.g. values for HSVa for fill vs stroke)
//
//        ),
//        mapOf(
//            "machine" to "SMALL_CIRCLE",
//            "op" to 1003,
//            // "HEIGHT" to 1003, // NOTE... this is redundant / assumed
//            "gate" to true,
//            "dur" to 4.0,
//            "doneAction" to 2,
//        ),
//        mapOf(
//            "machine" to "SIZE",
//            "op" to 1002,
//            "gate" to true, // type: Boolean? (true means gate on, false means gate off, null means no change)
//            "dur" to 4.0,
//            // 0=nothing, 1="pause" (op is not running, but still exists), 2=free
//            "doneAction" to 2,
//        ),
//    ),
//    1.0 to listOf(
//        mapOf(
//            "machine" to "SMALL_CIRCLE",
//            "gate" to true, // type: Boolean? (true means gate on, false means gate off, null means no change)
//            "dur" to 4.0,
//        ),
//    ),
//    1.4 to listOf(
//        mapOf(
//            "machine" to "SMALL_CIRCLE",
//            "gate" to true, // type: Boolean? (true means gate on, false means gate off, null means no change)
//            "dur" to 4.0,
//        ),
//    ),
//    2.0 to listOf(
//        mapOf(
//            // now SIZE gets slightly smaller for the 2nd 2"
//            "machine" to "SIZE",
//            "startValue" to 40.0, // redundant?
//            "endValue" to 20.0,
//
//            // this is the operation of the machine
//            "op" to 1001,
//            "dur" to 2.0,
//            //"startAction" to 0 // not needed, since 0 is the default
//            "endAction" to 2 // free
//        ),
//    ),
//    4.0 to listOf(
//        mapOf(
//            "machine" to "SMALL_CIRCLE",
//            "gate" to true, // type: Boolean? (true means gate on, false means gate off, null means no change)
//            "dur" to 4.0,
//        ),
//    ),
//)