package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    companion object{
        var administrateur=false

    }
    private lateinit var viewModelMain: ViewModelMain
    private lateinit var binding: ActivityMainBinding
    /*private lateinit var floating:FloatingActionButton
*/
    /*private lateinit var listVendeur: List<Vendeur>*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelMain = ViewModelProvider(this)[ViewModelMain::class.java]







        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val menuItem = menu.findItem(R.id.app_bar_switch)
        val switch = menuItem.actionView as Switch
        switch.text="Administrateur"
        var floattingAdd=findViewById<FloatingActionButton>(R.id.fab)
        // Configurer l'écouteur de changement d'état du Switch
        switch.setOnCheckedChangeListener { _, isChecked ->
            // Faire quelque chose avec l'état activé/désactivé
            if (isChecked) {
                // Switch activé
                viewModelMain.setVariableChanged(true)
                floattingAdd.visibility= View.VISIBLE
            }else {
                // Switch désactivé
                viewModelMain.setVariableChanged(false)
                floattingAdd.visibility=View.GONE
            }
        }

        return true
    }
}