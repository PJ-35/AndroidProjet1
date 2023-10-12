package com.example.myapplication.ui.magasin

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.categorie

/**
 * Exemple de boîte de dialogue personnalisée pour modifier le nom d'une personne
 */
class CreerVendeur() : DialogFragment() {
    var position: Int = 0
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

        spinner.adapter = adapter
        spinner.setSelection(0)
        var categorieDeBase=categorie.values()[0]
        // Titre
        arguments?.let {
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
                val name = (dialog as AlertDialog).findViewById<EditText>(R.id.et_name)?.text.toString()
                val description = (dialog as AlertDialog).findViewById<EditText>(R.id.et_description)?.text.toString()
                val prix = (dialog as AlertDialog).findViewById<EditText>(R.id.et_prix)?.text.toString()
                if(name.isEmpty()||description.isEmpty()||prix.isEmpty()||prix.toDouble()<1) {
                    val txt_erreur = (dialog as AlertDialog).findViewById<TextView>(R.id.text_error)
                    txt_erreur?.setText("Veuillez remplir tous les champs")
                }
                else{
                    val prix2=prix.toDouble()
                    (activity as MainActivity).onCreateVendeur(name, description,prix2,categorieDeBase)

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