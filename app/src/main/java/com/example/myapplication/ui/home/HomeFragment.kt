package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.ViewModelMain
import com.example.myapplication.data.VendeurDatabase
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.modele.VendeurAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: VendeurAdapter
    private lateinit var viewModelMain: ViewModelMain

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModelMain = ViewModelProvider(requireActivity()).get(ViewModelMain::class.java)

        //creation de l'instance de base de données
        var vendeurDao= VendeurDatabase.getInstance(requireContext()).vendeurDao()

        // fixe les dimensions du RecyclerView pour gain de performance
        binding.rvPerson.setHasFixedSize(true)
        // Création de l'adapter avec une liste live data
        viewModelMain.isVariableChanged.observe(requireActivity()){administrateur->
            vendeurDao.getAllVendeurs().observe(requireActivity()) {vendeur->
                adapter = VendeurAdapter(vendeur,administrateur)
                binding.rvPerson.adapter = adapter
            }
        }


        //Réglage d'affichage du recyclerView
        binding.rvPerson.layoutManager = LinearLayoutManager(requireContext())
        val root: View = binding.root


        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}