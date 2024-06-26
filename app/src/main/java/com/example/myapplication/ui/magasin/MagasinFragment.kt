package com.example.myapplication.ui.magasin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.ViewModelMain
import com.example.myapplication.data.VendeurDatabase
import com.example.myapplication.databinding.FragmentMagasinBinding
import com.example.myapplication.modele.Vendeur
import com.example.myapplication.modele.VendeurAdapter
import com.example.myapplication.ui.panier.PanierViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MagasinFragment : Fragment() {

    private var _binding: FragmentMagasinBinding? = null
    private lateinit var adapter: VendeurAdapter
    private lateinit var viewModelMain: ViewModelMain
    private lateinit var panierViewModel: PanierViewModel
    private lateinit var liveDataVendeur: LiveData<List<Vendeur>>
    private lateinit var floattingAdd: FloatingActionButton

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMagasinBinding.inflate(inflater, container, false)
        viewModelMain = ViewModelProvider(requireActivity()).get(ViewModelMain::class.java)
        panierViewModel = ViewModelProvider(requireActivity()).get(PanierViewModel::class.java)
        floattingAdd=binding.fab


        //creation de l'instance de base de données
        var vendeurDao= VendeurDatabase.getInstance(requireContext()).vendeurDao()
        thread {
            liveDataVendeur=vendeurDao.getAllVendeurs()
        }.join()

        // fixe les dimensions du RecyclerView pour gain de performance
        binding.rvPerson.setHasFixedSize(true)


        // Création de l'écouteur d'événement pour le RecyclerView
        // (voir la classe PersonAdapter) interface OnItemClickListenerInterface
        val onItemClickListener: VendeurAdapter.OnItemClickListenerInterface =
            object : VendeurAdapter.OnItemClickListenerInterface {
                override fun onItemClick(itemView: View?, position: Int) {
                    var articleChoisie=liveDataVendeur.value!![position]

                    panierViewModel.addVendeur(articleChoisie)
                }

                // Méthode appelée lors du clic sur le bouton Éditer
                override fun onClickEdit(itemView: View, position: Int) {
                    // Configuration du fragment de dialogue pour modifier le nom
                    val dialog = CreerVendeur()
                    val args = Bundle()
                    var vendeur=liveDataVendeur.value!![position]
                    args.putString("nom", vendeur.nom)
                    args.putInt("id", vendeur.id)
                    args.putString("description", vendeur.description)
                    args.putString("categorie", vendeur.categorie.etat)
                    args.putDouble("prix", vendeur.prix)
                    dialog.arguments = args
                    // FragmentManager pour afficher le fragment de dialogue
                    val fm: FragmentManager =  MainActivity.fm
                    dialog.show(fm, "fragment_edit_name")
                }

                // Méthode appelée lors du clic sur le bouton Supprimer
                override fun onClickDelete(position: Int) {
                    var tt=liveDataVendeur.value!![position]
                    Toast.makeText(requireContext(), "Le vendeur ${tt.nom} a été supprimée avec succès", Toast.LENGTH_SHORT).show()
                    thread {
                        vendeurDao.deleteVendeur(tt)
                    }.join()

                // Mettre à jour l'affichage du RecyclerView (adapter)
                   // adapter.notifyItemRemoved(position)
                }
            }

        // Création de l'adapter avec une liste live data
        viewModelMain!!.administrateur.observe(viewLifecycleOwner){ administrateur->
            liveDataVendeur.observe(viewLifecycleOwner) {vendeur->
                adapter = VendeurAdapter(vendeur,administrateur)
                // Lier l'écouteur d'événement au RecyclerView
                adapter.setOnItemClickListener(onItemClickListener)
                binding.rvPerson.adapter = adapter

            }
        }


        //Réglage d'affichage du recyclerView
        binding.rvPerson.layoutManager = LinearLayoutManager(requireContext())

        val root: View = binding.root

        floattingAdd.setOnClickListener {
            Snackbar.make(it, "Création d'un nouveau vendeur", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            // Configuration du fragment de dialogue pour modifier le nom
            val dialog = CreerVendeur()
            val args = Bundle()
            dialog.arguments = args
            // FragmentManager pour afficher le fragment de dialogue
            //val fm: FragmentManager = supportFragmentManager
            dialog.show(MainActivity.fm, "fragment_magasin")

        }

        viewModelMain.administrateur.observe(viewLifecycleOwner){ administrateur->
            if(administrateur){
                floattingAdd.visibility= View.VISIBLE
            }
            else{
                floattingAdd.visibility=View.GONE
            }
        }
        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}