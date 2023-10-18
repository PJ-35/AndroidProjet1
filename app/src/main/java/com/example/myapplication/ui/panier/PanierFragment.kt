package com.example.myapplication.ui.panier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ViewModelMain
import com.example.myapplication.data.VendeurDatabase
import com.example.myapplication.databinding.FragmentPanierBinding
import com.example.myapplication.modele.PanierAdapter
import com.example.myapplication.modele.Vendeur
import com.example.myapplication.modele.VendeurAdapter
import kotlin.concurrent.thread

class PanierFragment : Fragment() {

    private var _binding: FragmentPanierBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var adapter: PanierAdapter
    //private lateinit var viewModelMain: ViewModelMain
    private lateinit var liveDataPanier: LiveData<List<Vendeur>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val  viewModelMain = ViewModelProvider(requireActivity()).get(ViewModelMain::class.java)
        _binding = FragmentPanierBinding.inflate(inflater, container, false)

        //creation de l'instance de base de données
        var vendeurDao= VendeurDatabase.getInstance(requireContext()).vendeurDao()
        thread {
            //var articlesId=viewModelMain.getArticlesDuPanier().
            liveDataPanier=vendeurDao.getListVendeurInId(viewModelMain.getIdArticlesDuPanier())

            //viewModelMain._articles=liveDataPanier
        }.join()


        // fixe les dimensions du RecyclerView pour gain de performance
        binding.rvPanier.setHasFixedSize(true)

        // Création de l'adapter
//        viewModelMain.articles.observe(requireActivity()){produit->
//            adapter=VendeurAdapter(produit,false)
//
//            binding.rvPanier.adapter=adapter
//        }

        liveDataPanier.observe(viewLifecycleOwner) {  produit ->
            // Mettez à jour votre RecyclerView avec la nouvelle liste d'articles (panier)

            for (prod in produit){
                var i =0
                for (id1 in viewModelMain.getIdArticlesDuPanier()){
                    if (prod.id==id1){
                        i++
                    }
                }
                prod.quantite=i
            }

            var total = 0.0
            for (article in produit){
                total+=article.prix*article.quantite
            }

            val formattedTotal = String.format("%.2f", total)
            adapter= PanierAdapter(produit)


            binding.txtTotal.text= "$$formattedTotal"
            binding.rvPanier.adapter=adapter
        }


        binding.rvPanier.layoutManager= LinearLayoutManager(requireContext())  //Réglage d'affichage du recyclerView


        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}