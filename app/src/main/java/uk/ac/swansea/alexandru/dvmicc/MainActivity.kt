package uk.ac.swansea.alexandru.dvmicc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Format
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.highlight.ColorTheme
import uk.ac.swansea.alexandru.dvmicc.fragments.IntroductionFragment

class MainActivity : AppCompatActivity() {

    private val navigationBarListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.introductionButton -> {
                replaceFragment(IntroductionFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBar : MaterialToolbar = findViewById(R.id.mainActivityToolbar)
        setSupportActionBar(appBar)

        val navigationBar : BottomNavigationView = findViewById(R.id.mainNavigationBar)
        navigationBar.setOnNavigationItemSelectedListener(navigationBarListener)
        navigationBar.selectedItemId = R.id.introductionButton
    }



    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.mainContentFrame, newFragment)
        fragmentTransaction.commit()
    }
}
