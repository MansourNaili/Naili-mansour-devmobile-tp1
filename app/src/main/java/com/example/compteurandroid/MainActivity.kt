package com.example.compteurandroid   // <-- adaptez à VOTRE nom de package

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Variable demandée par le TP : entier initialisé à 0
    private var compteur: Int = 0

    // Références vers les composants graphiques (initialisées dans onCreate)
    private lateinit var textViewCompteur: TextView
    private lateinit var buttonIncrementer: Button
    private lateinit var buttonDecrementer: Button
    private lateinit var buttonReinitialiser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Évite que le contenu passe sous la barre d'état / de navigation
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1) Récupération des composants à partir de leurs identifiants
        textViewCompteur = findViewById(R.id.textViewCompteur)
        buttonIncrementer = findViewById(R.id.buttonIncrementer)
        buttonDecrementer = findViewById(R.id.buttonDecrementer)
        buttonReinitialiser = findViewById(R.id.buttonReinitialiser)

        // [OPTIONNEL] Restauration de la valeur après une rotation de l'écran
        compteur = savedInstanceState?.getInt(CLE_COMPTEUR) ?: 0

        // 2) Programmation du clic sur chaque bouton
        buttonIncrementer.setOnClickListener {
            compteur++
            afficherCompteur()
        }

        buttonDecrementer.setOnClickListener {
            compteur--
            afficherCompteur()
        }

        buttonReinitialiser.setOnClickListener {
            compteur = 0
            afficherCompteur()
            // [OPTIONNEL] Message Toast
            Toast.makeText(this, R.string.message_reinitialisation, Toast.LENGTH_SHORT).show()
        }

        // 3) Affichage initial : la valeur affichée correspond à la variable
        afficherCompteur()
    }

    // Met à jour le TextView pour qu'il reflète toujours la variable compteur
    private fun afficherCompteur() {
        textViewCompteur.text = compteur.toString()

        // [OPTIONNEL] Vert si positif, rouge si négatif, noir si nul
        val couleur = when {
            compteur > 0 -> R.color.compteur_positif
            compteur < 0 -> R.color.compteur_negatif
            else -> R.color.compteur_nul
        }
        textViewCompteur.setTextColor(ContextCompat.getColor(this, couleur))
    }

    // [OPTIONNEL] Sauvegarde de la valeur avant destruction de l'activité (rotation)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CLE_COMPTEUR, compteur)
    }

    companion object {
        private const val CLE_COMPTEUR = "cle_compteur"
    }
}
