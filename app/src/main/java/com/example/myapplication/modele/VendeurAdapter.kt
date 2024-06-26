package com.example.myapplication.modele

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.categorie

class VendeurAdapter(private val lstVendeur: List<Vendeur>,private val administrateur:Boolean):
    RecyclerView.Adapter<VendeurAdapter.ViewHolder>() {


    // Interface pour gérer les clics sur les éléments de la liste
    // mis à disposition de toute activité instanciant l'adapter : à charge pour cette activité d'implémenter les méthodes de l'interface
    interface OnItemClickListenerInterface {
        fun onItemClick(itemView: View?, position: Int)
        fun onClickEdit(itemView: View, position: Int)
        fun onClickDelete(position: Int)
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

        // bloc de construction exécuté immédiatement après le constructeur primaire
        // nécessaire car on ne peut pas exécuter de code dans le constructeur primaire
        init {

            // Ajoute un écouteur d'événement du clic simple sur itemView (la ligne entière)
            itemView.setOnClickListener {
                // Récupère la position de l'élément cliqué
                val position = adapterPosition
                // Vérifie que la position est valide
                // (parfois, le clic est détecté alors que la position n'est pas encore déterminée)
                if (position != RecyclerView.NO_POSITION) {
                    // Appelle la méthode onItemClick de l'objet qui implémente l'interface OnItemClickListener
                    listener.onItemClick(itemView, position);

                }
            }

            if(administrateur){
                // Ajoute un écouteur d'événement du clic long sur itemView
                // (pour afficher le menu contextuel)
                itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    val position = adapterPosition
                    // Crée les items du menu contextuel
                    val edit: android.view.MenuItem = menu.add(0, v.id, 0, R.string.action_edit)
                    val delete: android.view.MenuItem = menu.add(0, v.id, 0, R.string.action_delete)
                    // Ajoute un écouteur d'événement sur les items du menu contextuel
                    edit.setOnMenuItemClickListener {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickEdit(itemView, position)
                        }
                        false
                    }
                    delete.setOnMenuItemClickListener {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClickDelete(position)
                        }
                        false
                    }
                }
            }



        }
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