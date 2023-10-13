package com.example.myapplication.ui.magasin

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.categorie
import com.example.myapplication.modele.Vendeur

/**
 * Exemple de boîte de dialogue personnalisée pour modifier le nom d'une personne
 */
class CreerVendeur() : DialogFragment() {
    lateinit var nom:EditText
    lateinit var description:EditText
    lateinit var prix:EditText
    var descriptionV:String? = null
    var idV:Int = 0
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = activity?.let { AlertDialog.Builder(it) }
        // Layout Inflater : Responsable de l'affichage du layout
        // requireActivity() : Servi par l'activité appelante (ici, MainActivity)
        val inflater = requireActivity().layoutInflater
        val view =inflater.inflate(R.layout.fragment_creation, null)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        val listcategorie= mutableListOf<String>()
        for(categorie in categorie.values())
            listcategorie.add(categorie.etat)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listcategorie)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nom=view.findViewById(R.id.et_name)
        description=view.findViewById(R.id.et_description)
        prix=view.findViewById(R.id.et_prix)

        spinner.adapter = adapter
        spinner.setSelection(0)
        var categorieDeBase=categorie.values()[0]
        // Titre
        arguments?.let {
            val name = it.getString("nom")
            idV=it.getInt("id")
            descriptionV=it.getString("description")
            val categorieV=it.getString("categorie")
            val prixV=it.getDouble("prix")
            if(!name.isNullOrEmpty()){
                nom.setText(name)
                description.setText(descriptionV)
                val v=categorie.fromEtat(categorieV!!)
                spinner.setSelection(v!!.ordinal)
                categorieDeBase=v
                prix.setText(prixV.toString())
            }
            builder?.setTitle("Ajout d'un vendeur")
        }
        // Ajoutez un écouteur d'événements pour traiter les changements de sélection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItemName = parent.getItemAtPosition(position).toString()

                val categorie = categorie.fromEtat(selectedItemName)

                if (categorie != null) {
                    categorieDeBase=categorie
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code à exécuter lorsque rien n'est sélectionné
            }
        }

        // Importe le layout de la boîte de dialogue
        // Le paramètre null est nécessaire car le layout est directement lié à la boîte de dialogue et non ancré dans un parent
        builder?.setView(view)

            // Gestion des boutons Ok et Annuler
            ?.setPositiveButton("OK") { dialog, id ->
                val name = nom.text.toString()
                val descriptionO = description.text.toString()
                val prixO = prix.text.toString()
                if(name.isNullOrEmpty()||descriptionO.isNullOrEmpty()||prixO.isNullOrEmpty()||prixO.toDouble()==0.0) {
                    Toast.makeText(requireContext(), "Le vendeur n'a pas été créé. Informations insuffisantes ou mal entrées", Toast.LENGTH_SHORT).show()
                }
                else if(descriptionV.isNullOrEmpty()){
                    val prix2= prixO.toDouble()
                    (activity as MainActivity).onCreateVendeur(name, descriptionO,prix2,categorieDeBase)
                }else{
                    val prix2=prixO.toDouble()
                    (activity as MainActivity).onUpdateVendeur(Vendeur(idV,name,descriptionO,prix2,categorieDeBase,1))
                }
            }
            ?.setNegativeButton("Annuler") { dialog, id ->
                getDialog()?.cancel()
            }
        if (builder != null) {
            return builder.create()
        }
        return super.onCreateDialog(savedInstanceState)


    }
}