package com.example.myapplication.modele

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.categorie

class VendeurAdapter(private val lstVendeur: List<Vendeur>):
    RecyclerView.Adapter<VendeurAdapter.ViewHolder>() {


    // Interface pour gérer les clics sur les éléments de la liste
    // mis à disposition de toute activité instanciant l'adapter : à charge pour cette activité d'implémenter les méthodes de l'interface
    interface OnItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)

    }

    // Objet qui instancie l'interface OnItemClickListener
    lateinit var listener: OnItemClickListenerInterface

    // Cette méthode permet de passer l'implémentation de l'interface OnItemClickListener à cet adapter
    // Elle est appelée dans la classe MainActivity
    fun setOnItemClickListener(listener: OnItemClickListenerInterface) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvPrix: TextView = itemView.findViewById(R.id.tv_prix)
        var imgPerson: ImageView = itemView.findViewById(R.id.img_person)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_description)
    }

    // Cette méthode est appelée à chaque fois qu'il faut créer une ligne
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Utilise le layout person_one_line pour créer une vue
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vendeur_rangee, parent, false)

        // Crée un viewHolder en passant la vue en paramètre
        return ViewHolder(view)
    }

    // Cette méthode permet de lier les données à la vue
    // Elle remplit une ligne créée par onCreateViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupère l'élément de la liste à la position "position"
        val vendeur: Vendeur = lstVendeur[position]
        // Met à jour les données de la vue
        holder.tvName.text = vendeur.nom
        holder.tvPrix.text = vendeur.prix.toString()
        holder.tvDescription.text=vendeur.description
        holder.imgPerson.contentDescription=vendeur.categorie.etat
        if (vendeur.categorie==categorie.EN_COMMANDE)
            holder.imgPerson.setImageResource(R.drawable.en_commande)
        else if(vendeur.categorie==categorie.EN_STOCK)
            holder.imgPerson.setImageResource(R.drawable.en_stock)
        else
            holder.imgPerson.setImageResource(R.drawable.en_rupture_de_stock)

    }

    // Cette méthode permet de retourner le nombre d'éléments de la liste
    override fun getItemCount(): Int {
        return lstVendeur.size
    }
}