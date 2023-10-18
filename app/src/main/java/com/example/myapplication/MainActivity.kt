package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.data.VendeurDao
import com.example.myapplication.data.VendeurDatabase
import com.example.myapplication.data.categorie
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.modele.Vendeur
import com.example.myapplication.ui.magasin.CreerVendeur
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var fm:FragmentManager
    }
    private lateinit var viewModelMain: ViewModelMain
    private lateinit var binding: ActivityMainBinding
    private lateinit var floattingAdd:FloatingActionButton
    private lateinit var vendeurDao: VendeurDao

    /*private lateinit var floating:FloatingActionButton
*/
    /*private lateinit var listVendeur: List<Vendeur>*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelMain = ViewModelProvider(this)[ViewModelMain::class.java]
        floattingAdd=findViewById(R.id.fab)
        vendeurDao= VendeurDatabase.getInstance(this).vendeurDao()
        fm= supportFragmentManager
        floattingAdd.setOnClickListener { view ->
            Snackbar.make(view, "Création d'un nouveau vendeur", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            // Configuration du fragment de dialogue pour modifier le nom
            val dialog = CreerVendeur()
            val args = Bundle()
            dialog.arguments = args
            // FragmentManager pour afficher le fragment de dialogue
            //val fm: FragmentManager = supportFragmentManager
            dialog.show(fm, "fragment_magasin")

        }

        viewModelMain.administrateur.observe(this){ administrateur->
                if(administrateur){
                    floattingAdd.visibility= View.VISIBLE
                }
            else{
                    floattingAdd.visibility=View.GONE
            }
        }




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
        // Configurer l'écouteur de changement d'état du Switch
        switch.setOnCheckedChangeListener { _, isChecked ->
            // Faire quelque chose avec l'état activé/désactivé
            if (isChecked) {
                // Switch activé
                viewModelMain.setVariableChanged(true)
            }else {
                // Switch désactivé
                viewModelMain.setVariableChanged(false)
            }
        }

        return true
    }

    fun onCreateVendeur(nom: String,description:String,prix:Double,categorieDeBase:categorie){
        Toast.makeText(this, "Vendeur créé avec succès", Toast.LENGTH_SHORT).show()
        thread {
            vendeurDao.insertVendeur(Vendeur(0,nom,description,prix, categorieDeBase,1))
        }.join()

    }

    fun onUpdateVendeur(vendeur: Vendeur){
        Toast.makeText(this, "Le vendeur ${vendeur.nom} a bien été modifié", Toast.LENGTH_SHORT).show()
        thread {
            vendeurDao.updateVendeur(vendeur)
        }.join()

    }

}