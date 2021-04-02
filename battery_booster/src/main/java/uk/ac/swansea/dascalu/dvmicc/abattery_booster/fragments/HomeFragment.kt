package uk.ac.swansea.dascalu.dvmicc.abattery_booster.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import uk.ac.swansea.dascalu.dvmicc.abattery_booster.AdvancedActivity
import uk.ac.swansea.dascalu.dvmicc.abattery_booster.R

import java.util.Random
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {
    private val random = Random()
    //duration is measured in seconds
    private var optimiseDuration : Int = 0
    private var startOptimiseTime : Long = 0
    private var isOptimised : Boolean = false
    private var timer : Timer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<MaterialButton>(R.id.optimiseButton).setOnClickListener { it ->
            startOptimise(view)
        }

        view.findViewById<MaterialButton>(R.id.advancedButton).setOnClickListener { it ->
            startAdvancedActivity(view)
        }

        setBatteryIssues(view)
        stopProgressBar(view)
    }

    private fun setBatteryIssues(view: View) {
        val issues : Int = random.nextInt(25)
        view.findViewById<TextView>(R.id.optimiseTextView).text = resources.getString(
                R.string.battery_issues_text, issues)
    }

    private fun startOptimise(view: View) {
        if(!isOptimised) {
            optimiseDuration = random.nextInt(20)
            startOptimiseTime = System.currentTimeMillis()

            val progressBar = view.findViewById<LinearProgressIndicator>(R.id.progressBar)
            progressBar.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
            progressBar.visibility = View.VISIBLE


            timer = Timer()

            val task = object : TimerTask() {
                override fun run() {
                    if(System.currentTimeMillis() - startOptimiseTime >= optimiseDuration * 1000) {
                        activity!!.runOnUiThread {
                            stopProgressBar(view)
                            view.findViewById<TextView>(R.id.optimiseTextView).text = resources.getString(
                                    R.string.optimsed_text)
                        }

                        isOptimised = true
                        timer!!.cancel()
                        timer!!.purge()
                        timer = null
                    }
                }
            }

            timer!!.schedule(task, 10, 300)
        }
    }

    fun startAdvancedActivity(view: View) {
        val intent = Intent(view.context, AdvancedActivity::class.java)
        startActivity(intent)
    }

    private fun stopProgressBar(view: View) {
        val progressBar = view.findViewById<LinearProgressIndicator>(R.id.progressBar)
        progressBar.showAnimationBehavior = LinearProgressIndicator.SHOW_NONE
        progressBar.visibility = View.INVISIBLE
    }
}