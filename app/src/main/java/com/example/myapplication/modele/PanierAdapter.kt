package com.example.myapplication.modele

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.categorie

class PanierAdapter(private val lstVendeur: List<Vendeur>):
    RecyclerView.Adapter<PanierAdapter.ViewHolder>()  {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvPrix: TextView = itemView.findViewById(R.id.tv_prix)
        var tvQte: TextView = itemView.findViewById(R.id.txt_qte)
        var imgPerson: ImageView = itemView.findViewById(R.id.img_person)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Utilise le layout person_one_line pour créer une vue
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.panier_rangee, parent, false)
        // Crée un viewHolder en passant la vue en paramètre
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstVendeur.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupère l'élément de la liste à la position "position"
        val vendeur: Vendeur = lstVendeur[position]
        // Met à jour les données de la vue
        holder.tvName.text = vendeur.nom
        holder.tvPrix.text = vendeur.prix.toString()
        holder.tvDescription.text=vendeur.description
        holder.imgPerson.contentDescription=vendeur.categorie.etat
        holder.tvQte.text="X"+ vendeur.quantite.toString()
        if (vendeur.categorie== categorie.EN_COMMANDE)
            holder.imgPerson.setImageResource(R.drawable.en_commande)
        else if(vendeur.categorie== categorie.EN_STOCK)
            holder.imgPerson.setImageResource(R.drawable.en_stock)
        else
            holder.imgPerson.setImageResource(R.drawable.en_rupture_de_stock)
    }

}