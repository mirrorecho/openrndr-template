package rain.sandbox

import org.openrndr.animatable.Animatable
import org.openrndr.animatable.easing.Easing
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import rain.interfaces.*
import rain.language.*
import rain.patterns.*
import rain.rndr.*
import kotlin.random.Random

fun main()  {
    solve1()
}

// TODO: SOLVE THE FOLLOWING:
//  - I.
//  - (1) create a circle in the center of the screen
//  - (2) animate the circle's radius so that it increases from 0. to 99. over 2 seconds
//  - (3) add easing to the above radius animation
//  - (4) animate the circle's color's alpha so that it increases from 0. to 1. over .6 seconds
//  - (5) continue to animate the circle's color's alpha so that it decreases from 1. to 0. over the next 1.4 seconds
//  - (6) change the circle's position to a random point, over 2 seconds, with easing for both X/Y coordinates
//  - ... cleanup and refactor!
//  - II.
//  - (7) repeat I.(1)-(6) above, every 1/10 second (multiple circle animations overlap)
//  - (8) move the center-point (from 1) around the screen, to a rhythm
//  - ... cleanup and refactor!
//  - III.
//  - (9) create multiple instances of II., each moving around the screen to different rhythms
//  - ... cleanup and refactor!

// -------------------------------------------------------------------------------------

fun solve1() {
    createValues(true,"X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS")

    val position1 = createRndrMachine("POSITION_1", true) { tr ->
        // TODO: how to implement the factory if trigger is an update to an existing act? (actName passed, or a "single" machine?)
        Position(
            x = tr.triggerRelated("X", properties = mapOf("value" to 0.5)),
            y = tr.triggerRelated("Y", properties = mapOf("value" to 0.5)),
        )
    }.apply { relate("X", "X"); relate("Y", "Y"); } // TODO: implement relate method
    // TODO: relating here within the apply method is wonky

    val color1 = createRndrMachine("COLOR_1", true) { tr ->
        Color(
            h = tr.triggerRelated("H", properties = mapOf("value" to 200.0)),
            s = tr.triggerRelated("S", properties = mapOf("value" to 1.0)),
            v = tr.triggerRelated("V", properties = mapOf("value" to 0.5)),
            a = tr.triggerRelated("A", properties = mapOf("value" to 1.0)),
        )
    }.apply {
        relate("H", "H")
        relate("S", "S")
        relate("V", "V")
        relate("A", "A")
    }

    val circle = createRndrMachine("CIRCLE_1", true) {tr ->
        Circle(
            radius = tr.triggerRelated("RADIUS", properties = mapOf("value" to 99.0)),
            position = tr.triggerRelated("POSITION", properties = mapOf()),
            strokeColor = tr.triggerRelated("STROKE_COLOR", properties = mapOf()),
            strokeWeight = tr.triggerRelated("STROKE_WEIGHT", properties = mapOf("value" to 1.0)),
            fillColor = tr.triggerRelated("FILL_COLOR", properties = mapOf()),
        )
    }.apply {
        relate("RADIUS", "RADIUS")
        relate("POSITION", "POSITION_1")
        relate("STROKE_COLOR", "COLOR_1")
        relate("STROKE_WEIGHT", "STROKE_WEIGHT")
        relate("FILL_COLOR", "COLOR_1")
    }
    // TODO: DANG!!! So much boilerplate needed... figure out how to simplify!

    println("----------------------------------------------------------------------------")

//    println(RndrMachine<ValueAct>("RADIUS").apply { read() }.actFactory)

    val rndrMachines = Palette.fromKeys<RndrMachine<Act>>(
        "X", "Y", "H", "S", "V", "A", "STROKE_WEIGHT", "RADIUS",
        "POSITION_1", "COLOR_1", "CIRCLE_1"
    )

    val c1 = Cell("C1").apply { createMe() }
    c1.setVeinCycle("machine", "CIRCLE_1")
    c1.setVeins("dur", 2.0,)

    val score = Score(rndrMachines)
    score.createTriggers(c1)
    score.play()

    println("YO MAMA")

}

// -------------------------------------------------------------------------------------